% Imports


% APIs

:- consult('api/client/mdr_api.pl').
:- consult('api/client/mdr_api_http.pl').
:- consult('api/client/mdv_api.pl').
:- consult('api/client/mdv_api_http.pl').
:- consult('api/server/plan_api.pl').


% Algoritmos de Planeamento

:- consult('algoritmos/best_first.pl').
:- consult('algoritmos/a_star.pl').
:- consult('algoritmos/plan_mud_mot.pl').
:- consult('algoritmos/genetico.pl').
:- consult('algoritmos/avalia.pl').
:- consult('afetacao/escalonamento.pl').
:- consult('afetacao/servico_tripulante.pl').

% Mocks

:- consult('mocks/horarioMotorista.pl').


% Utils

:- consult('utils/distance.pl').
:- consult('utils/sequence.pl').
:- consult('utils/ordem_membros.pl').
:- consult('utils/gera_ligacoes.pl').



% Load Data from sources (MDR and MDV) and start server using port 1234

:- initialization(load_data).
:- initialization(load_data_mdv).
:- initialization(server(1234)).