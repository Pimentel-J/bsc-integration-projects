% imports necess�rios

:- use_module(library(http/http_open)).
:- use_module(library(http/json)).
:- use_module(library(http/http_ssl_plugin)).
:- use_module(library(ssl)).
:- use_module(library(http/http_session)).

% carregar dados relevantes consumindo a API do MDR

load_data_http :-
  clear_bc_http,
  get_nos_http,
  get_linhas_http,
  gera_ligacoes_http.


% limpa a base de conhecimento no que toca aos factos que ser�o carregados das fontes MDR e MDV (evita duplica��es)

clear_bc_http :-
    http_session_retractall(no(_,_,_,_,_,_)),
    http_session_retractall(linha(_,_,_,_,_)),
    http_session_retractall(segmentos(_,_,_,_)),
    http_session_retractall(liga(_,_,_)).


% carregar dados dos n�s da rede

get_nos_http :-
  http_open('https://***private***.herokuapp.com/api/nos', Reply, [request_header('Accept'='application/json')]),
  json_read_dict(Reply, Dict),
  close(Reply),
  assert_nos_http(Dict, _NLinhas).

  % percorre cada n� da lista e faz assert do mesmo
assert_nos_http([],0).
assert_nos_http([H|L],N) :-
    assert_no_http(H),
    assert_nos_http(L,N2),
    N is N2+1.
  
  % assert de um n�
assert_no_http(No) :-
    %format('No: abrev="~w", nome="~w" ~n', [No.abreviatura, No.nome] ),
    http_session_assert(no(No.nome,No.abreviatura,No.pontoRendicao,No.estacaoRecolha,No.longitude,No.latitude)).



% carregar dados das linhas

get_linhas_http :-
  http_open('https://***private***.herokuapp.com/api/percursos', Reply, [request_header('Accept'='application/json')]),
  json_read_dict(Reply, Dict),
  close(Reply),
  assert_linhas_http(Dict, _NLinhas).

  % percorre cada linha da lista e faz assert da mesma
assert_linhas_http([],0).
assert_linhas_http([H|L],N) :-
    (atom(H.idLinha);assert_linha_http(H)), % s� faz assert se o idLinha n�o for nulo
    assert_linhas_http(L,N2),
    N is N2+1.

  % assert de uma linha
assert_linha_http(Linha) :-
    assert_segmentos_http(Linha.segmentos),
    get_dados_linha(Linha.segmentos, Duracao_seg, Distancia),
    ordenar_segmentos(Linha.segmentos, SegmentosSorted),
    get_lista_nos(SegmentosSorted, Nos),
    %format('~n linha("~w",~w,~p,~d,~d)', [Linha.idLinha.nome, Linha.idPercurso, Nos, Duracao_seg/60, Distancia] ),
    Duracao_min is div(Duracao_seg,60),
    %number_string(IDPercurso,Linha.idPercurso),
    http_session_assert(linha(Linha.idLinha.nome,Linha.idPercurso,Nos,Duracao_min,Distancia)).

% carregar dados dos segmentos, fornecidos aquando do assert de cada linha
assert_segmentos_http([]).
assert_segmentos_http([H|L]) :-
    assert_segmento_http(H),
    assert_segmentos_http(L).
  
  % assert de um segmento
assert_segmento_http(Segmento):-
    http_session_assert(segmentos(Segmento.noOrigem.abreviatura,Segmento.noDestino.abreviatura,Segmento.duracao, Segmento.distancia)).


