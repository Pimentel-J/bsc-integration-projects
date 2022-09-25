% imports necessários

:- use_module(library(http/http_open)).
:- use_module(library(http/json)).
:- use_module(library(http/http_ssl_plugin)).
:- use_module(library(ssl)).

% carregar dados relevantes consumindo a API do MDV

load_data_mdv :-
  clear_bc_mdv,
  get_viagens,
  get_passagens,
  get_motoristas,
  get_blocos,
  get_servicos_viatura.


% limpa a base de conhecimento no que toca aos factos que serão carregados das fontes MDR e MDV (evita duplicações)

clear_bc_mdv :-
    abolish(viagem,2),
    abolish(passagens,3),
    abolish(motorista,1),
    abolish(bloco,5),
    abolish(servicoViatura,4).


% carregar dados das viagens

get_viagens :-
  http_open('https://20s5-3na-mdv.azurewebsites.net/api/viagens', Reply, [request_header('Accept'='application/json')]),
  json_read_dict(Reply, Dict),
  close(Reply),
  format('---- MDV API - Load Viagens ---- '),
  assert_viagens(Dict, NViagens),
  format('~d viagens importadas~n', NViagens).

  % percorre cada viagem da lista e faz assert da mesma
assert_viagens([],0).
assert_viagens([H|L],N) :-
    assert_viagem(H),
    assert_viagens(L,N2),
    N is N2+1.
  
  % assert de uma viagem
assert_viagem(Viagem) :-
    assertz(viagem(Viagem.id,Viagem.percursoId,Viagem.servicoViaturaId,Viagem.horaInicio,Viagem.horaFim)).


% carregar dados das passagens

get_passagens :-
  http_open('https://20s5-3na-mdv.azurewebsites.net/api/passagens', Reply, [request_header('Accept'='application/json')]),
  json_read_dict(Reply, Dict),
  close(Reply),
  format('---- MDV API - Load Passagens ---- '),
  assert_passagens(Dict, NPassagens),
  format('~d passagens importadas~n', NPassagens).

  % percorre cada passagem da lista e faz assert da mesma
assert_passagens([],0).
assert_passagens([H|L],N) :-
    assert_passagem(H),
    assert_passagens(L,N2),
    N is N2+1.
  
  % assert de uma passagem
assert_passagem(Passagem) :-
    viagem(Passagem.viagemId,PercursoIDString,_,_,_),
    %number_string(PercursoID,PercursoIDString),
    assertz(passagens(PercursoIDString,Passagem.horaPassagem,Passagem.abreviaturaNo)).



% carregar dados dos motoristas

get_motoristas :-
  http_open('https://20s5-3na-mdv.azurewebsites.net/api/tripulantes', Reply, [request_header('Accept'='application/json')]),
  json_read_dict(Reply, Dict),
  close(Reply),
  format('---- MDV API - Load Motoristas ---- '),
  assert_motoristas(Dict, NMotoristas),
  format('~d motoristas importados~n', NMotoristas).

  % percorre cada motorista da lista e faz assert do mesmo
assert_motoristas([],0).
assert_motoristas([H|L],N) :-
    assert_motorista(H),
    assert_motoristas(L,N2),
    N is N2+1.
  
  % assert de um motorista
assert_motorista(Motorista) :-
    assertz(motorista(Motorista.numeroMecanografico)).



% carregar dados dos blocos

get_blocos :-
  http_open('https://20s5-3na-mdv.azurewebsites.net/api/blocos', Reply, [request_header('Accept'='application/json')]),
  json_read_dict(Reply, Dict),
  close(Reply),
  format('---- MDV API - Load Blocos ---- '),
  assert_blocos(Dict, NBlocos),
  format('~d blocos importados~n', NBlocos).

  % percorre cada bloco da lista e faz assert do mesmo
assert_blocos([],0).
assert_blocos([H|L],N) :-
    assert_bloco(H),
    assert_blocos(L,N2),
    N is N2+1.
  
  % assert de um bloco
assert_bloco(Bloco) :-
    assertz(bloco(Bloco.codigo,Bloco.startTime,Bloco.endTime,Bloco.viagens,Bloco.servicoViaturaId)).




% carregar dados dos serviços viatura

get_servicos_viatura :-
  http_open('https://20s5-3na-mdv.azurewebsites.net/api/servicosViatura', Reply, [request_header('Accept'='application/json')]),
  json_read_dict(Reply, Dict),
  close(Reply),
  format('---- MDV API - Load Servicos Viatura ---- '),
  assert_servicos_viatura(Dict, NServicosViatura),
  format('~d servicos viatura importados~n', NServicosViatura).

  % percorre cada servico viatura da lista e faz assert do mesmo
assert_servicos_viatura([],0).
assert_servicos_viatura([H|L],N) :-
    get_blocos_de_servico_viatura(H.id,ListaBlocos),
    ordenar_blocos(ListaBlocos,ListaBlocosOrdenada),
    reduzir_lista_blocos(ListaBlocosOrdenada,ListaBlocosID),
    get_hora_inicio(H.id,HoraInicio),
    get_hora_fim(H.id,HoraFim),
    assert_servico_viatura(H,HoraInicio,HoraFim,ListaBlocosID),
    assert_servicos_viatura(L,N2),
    N is N2+1.

  % reduz a lista [BlocoID,HoraInicio] para [BlocoID]
  reduzir_lista_blocos([],[]).
  reduzir_lista_blocos([[BlocoID,_HoraInicio]|L1],[BlocoID|L2]) :-
    reduzir_lista_blocos(L1,L2).

  % obtem uma lista de ID dos blocos do servico viatura
get_blocos_de_servico_viatura(SV,Lista) :-
    findall([BlocoID,HoraInicio], (    bloco(BlocoID,HoraInicio,_,_,SV)
                    ), Lista).

  % ordena uma lista de blocos [BlocoID,HoraInicio] com base no atributo 'hora de inicio' (2º parametro)
ordenar_blocos(Lista, Ordenada):-
    predsort(comparar_blocos, Lista, Ordenada).

comparar_blocos(<, [_,A], [_,B]) :-
    A @< B.
comparar_blocos(=, [_,A], [_,B]) :-
    A == B.
comparar_blocos(>, [_,A], [_,B]) :-
    A @> B.

    % obtem a hora de inicio de um servico viatura
get_hora_inicio(SV,HoraInicio) :-
    get_lista_inicio_viagens_de_sv(SV,ListaHorasInicio),
    min_list(ListaHorasInicio,HoraInicio).


  % obtem uma lista de horas inicio de viagens do servico viatura
get_lista_inicio_viagens_de_sv(SV,Lista) :-
    findall(HoraInicio, (    viagem(_,_,SV,HoraInicio,_)
                    ), Lista).

  % obtem a hora de fim de um servico viatura
get_hora_fim(SV,HoraFim) :-
    get_lista_fim_viagens_de_sv(SV,ListaHorasFim),
    max_list(ListaHorasFim,HoraFim).


  % obtem uma lista de horas fim de viagens do servico viatura
get_lista_fim_viagens_de_sv(SV,Lista) :-
    findall(HoraFim, (    viagem(_,_,SV,_,HoraFim)
                    ), Lista).

  % assert de um servico viatura
assert_servico_viatura(SV,HoraInicio,HoraFim,ListaBlocos) :-
    assertz(servicoViatura(SV.id,HoraInicio,HoraFim,ListaBlocos)).