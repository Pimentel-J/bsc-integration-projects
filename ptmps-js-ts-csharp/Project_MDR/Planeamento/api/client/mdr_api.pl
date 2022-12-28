% imports necess�rios

:- use_module(library(http/http_open)).
:- use_module(library(http/json)).
:- use_module(library(http/http_ssl_plugin)).
:- use_module(library(ssl)).

% carregar dados relevantes consumindo a API do MDR

load_data :-
  clear_bc,
  get_nos,
  get_linhas,
  gera_ligacoes.


% limpa a base de conhecimento no que toca aos factos que ser�o carregados das fontes MDR e MDV (evita duplica��es)

clear_bc :-
    abolish(no,6),
    abolish(linha,5),
    abolish(segmentos,4),
    abolish(liga,3).


% carregar dados dos n�s da rede

get_nos :-
  http_open('https://***private***.herokuapp.com/api/nos', Reply, [request_header('Accept'='application/json')]),
  json_read_dict(Reply, Dict),
  close(Reply),
  format('---- MDR API - Load N�s ---- '),
  assert_nos(Dict, NLinhas),
  format('~d n�s importados~n', NLinhas).

  % percorre cada n� da lista e faz assert do mesmo
assert_nos([],0).
assert_nos([H|L],N) :-
    assert_no(H),
    assert_nos(L,N2),
    N is N2+1.
  
  % assert de um n�
assert_no(No) :-
    %format('No: abrev="~w", nome="~w" ~n', [No.abreviatura, No.nome] ),
    assertz(no(No.nome,No.abreviatura,No.pontoRendicao,No.estacaoRecolha,No.longitude,No.latitude)).



% carregar dados das linhas

get_linhas :-
  http_open('https://***private***.herokuapp.com/api/percursos', Reply, [request_header('Accept'='application/json')]),
  json_read_dict(Reply, Dict),
  close(Reply),
  format('---- MDR API - Load Linhas ---- '),
  assert_linhas(Dict, NLinhas),
  format('~d linhas importadas~n', NLinhas).

  % percorre cada linha da lista e faz assert da mesma
assert_linhas([],0).
assert_linhas([H|L],N) :-
    (atom(H.idLinha);assert_linha(H)), % s� faz assert se o idLinha n�o for nulo
    assert_linhas(L,N2),
    N is N2+1.

  % assert de uma linha
assert_linha(Linha) :-
    assert_segmentos(Linha.segmentos),
    get_dados_linha(Linha.segmentos, Duracao_seg, Distancia),
    ordenar_segmentos(Linha.segmentos, SegmentosSorted),
    get_lista_nos(SegmentosSorted, Nos),
    %format('~n linha("~w",~w,~p,~d,~d)', [Linha.idLinha.nome, Linha.idPercurso, Nos, Duracao_seg/60, Distancia] ),
    Duracao_min is div(Duracao_seg,60),
    %number_string(IDPercurso,Linha.idPercurso),
    assertz(linha(Linha.idLinha.nome,Linha.idPercurso,Nos,Duracao_min,Distancia)).

  % fornece os totais da dura��o e dist�ncia da linha completa (soma dos v�rios segmentos que a comp�em
get_dados_linha([], 0, 0).
get_dados_linha([H|L], Duracao, Distancia) :-
    get_dados_linha(L, Duracao2, Distancia2),
    Duracao is H.duracao + Duracao2,
    Distancia is H.distancia + Distancia2.

  % fornece uma lista de abreviaturas de n�s representados a partir da lista de segmentos
get_lista_nos(Segs, Nos):-
    get_lista_nos2(1, Segs, Nos).
get_lista_nos2(_,[],[]):-!.
get_lista_nos2(1,[H1|T1],L):-
    L=[H1.noOrigem.abreviatura | T],
    get_lista_nos2(2, [H1|T1], T).
get_lista_nos2(N,[H1|T1],L):-
    N1 is N+1,
    L=[H1.noDestino.abreviatura | T],
    get_lista_nos2(N1, T1, T).

  % ordena uma lista de segmentos com base no atributo 'ordem'
ordenar_segmentos(Lista, Ordenada):-
    predsort(comparar_segmentos, Lista, Ordenada).

comparar_segmentos(<, A, B) :-
    A.ordem @< B.ordem.
comparar_segmentos(=, A, B) :-
    A.ordem == B.ordem.
comparar_segmentos(>, A, B) :-
    A.ordem @> B.ordem.


% carregar dados dos segmentos, fornecidos aquando do assert de cada linha
assert_segmentos([]).
assert_segmentos([H|L]) :-
    assert_segmento(H),
    assert_segmentos(L).
  
  % assert de um segmento
assert_segmento(Segmento):-
    assertz(segmentos(Segmento.noOrigem.abreviatura,Segmento.noDestino.abreviatura,Segmento.duracao, Segmento.distancia)).


