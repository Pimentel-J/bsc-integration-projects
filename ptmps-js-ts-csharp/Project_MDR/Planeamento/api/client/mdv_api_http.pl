% imports necessários

:- use_module(library(http/http_open)).
:- use_module(library(http/json)).
:- use_module(library(http/http_ssl_plugin)).
:- use_module(library(ssl)).
:- use_module(library(http/http_session)).

% carregar dados relevantes consumindo a API do MDV

load_data_mdv_http :-
  clear_bc_mdv_http,
  get_viagens_http,
  get_passagens_http,
  get_motoristas_http,
  get_blocos_http,
  get_servicos_viatura_http.


% limpa a base de conhecimento no que toca aos factos que serão carregados das fontes MDR e MDV (evita duplicações)

clear_bc_mdv_http :-
    http_session_retractall(viagem(_,_)),
    http_session_retractall(passagens(_,_,_)),
    http_session_retractall(motorista(_)),
    http_session_retractall(bloco(_,_,_,_,_)),
    http_session_retractall(servicoViatura(_,_,_,_)).


% carregar dados das viagens

get_viagens_http :-
  http_open('https://20s5-3na-mdv.azurewebsites.net/api/viagens', Reply, [request_header('Accept'='application/json')]),
  json_read_dict(Reply, Dict),
  close(Reply),
  assert_viagens(Dict, _NViagens).

  % percorre cada viagem da lista e faz assert da mesma
assert_viagens_http([],0).
assert_viagens_http([H|L],N) :-
    assert_viagem_http(H),
    assert_viagens_http(L,N2),
    N is N2+1.
  
  % assert de uma viagem
assert_viagem_http(Viagem) :-
    http_session_assert(viagem(Viagem.id,Viagem.percursoId,Viagem.servicoViaturaId,Viagem.horaInicio,Viagem.horaFim)).


% carregar dados das passagens

get_passagens_http :-
  http_open('https://20s5-3na-mdv.azurewebsites.net/api/passagens', Reply, [request_header('Accept'='application/json')]),
  json_read_dict(Reply, Dict),
  close(Reply),
  assert_passagens_http(Dict, _NPassagens).

  % percorre cada passagem da lista e faz assert da mesma
assert_passagens_http([],0).
assert_passagens_http([H|L],N) :-
    assert_passagem_http(H),
    assert_passagens_http(L,N2),
    N is N2+1.
  
  % assert de uma passagem
assert_passagem_http(Passagem) :-
    viagem(Passagem.viagemId,PercursoIDString,_,_,_),
    %number_string(PercursoID,PercursoIDString),
    http_session_assert(passagens(PercursoIDString,Passagem.horaPassagem,Passagem.abreviaturaNo)).



% carregar dados dos motoristas

get_motoristas_http :-
  http_open('https://20s5-3na-mdv.azurewebsites.net/api/tripulantes', Reply, [request_header('Accept'='application/json')]),
  json_read_dict(Reply, Dict),
  close(Reply),
  assert_motoristas_http(Dict, _NMotoristas).

  % percorre cada motorista da lista e faz assert do mesmo
assert_motoristas_http([],0).
assert_motoristas_http([H|L],N) :-
    assert_motorista_http(H),
    assert_motoristas_http(L,N2),
    N is N2+1.
  
  % assert de um motorista
assert_motorista_http(Motorista) :-
    http_session_assert(motorista(Motorista.numeroMecanografico)).



% carregar dados dos blocos

get_blocos_http :-
  http_open('https://20s5-3na-mdv.azurewebsites.net/api/blocos', Reply, [request_header('Accept'='application/json')]),
  json_read_dict(Reply, Dict),
  close(Reply),
  assert_blocos_http(Dict, _NBlocos).

  % percorre cada bloco da lista e faz assert do mesmo
assert_blocos_http([],0).
assert_blocos_http([H|L],N) :-
    assert_bloco_http(H),
    assert_blocos_http(L,N2),
    N is N2+1.
  
  % assert de um bloco
assert_bloco_http(Bloco) :-
    http_session_assert(bloco(Bloco.codigo,Bloco.startTime,Bloco.endTime,Bloco.viagens,Bloco.servicoViaturaId)).




% carregar dados dos serviços viatura

get_servicos_viatura_http :-
  http_open('https://20s5-3na-mdv.azurewebsites.net/api/servicosViatura', Reply, [request_header('Accept'='application/json')]),
  json_read_dict(Reply, Dict),
  close(Reply),
  assert_servicos_viatura(Dict, _NServicosViatura).

  % percorre cada servico viatura da lista e faz assert do mesmo
assert_servicos_viatura_http([],0).
assert_servicos_viatura_http([H|L],N) :-
    get_blocos_de_servico_viatura(H.id,ListaBlocos),
    ordenar_blocos(ListaBlocos,ListaBlocosOrdenada),
    reduzir_lista_blocos(ListaBlocosOrdenada,ListaBlocosID),
    get_hora_inicio(H.id,HoraInicio),
    get_hora_fim(H.id,HoraFim),
    assert_servico_viatura_http(H,HoraInicio,HoraFim,ListaBlocosID),
    assert_servicos_viatura_http(L,N2),
    N is N2+1.

  % assert de um servico viatura
assert_servico_viatura_http(SV,HoraInicio,HoraFim,ListaBlocos) :-
    http_session_assert(servicoViatura(SV.id,HoraInicio,HoraFim,ListaBlocos)).