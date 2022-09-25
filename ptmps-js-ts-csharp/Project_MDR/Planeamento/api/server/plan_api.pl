% imports necessários

:- use_module(library(http/thread_httpd)).
:- use_module(library(http/http_dispatch)).
:- use_module(library(http/http_cors)).
:- use_module(library(http/http_server)).
:- use_module(library(http/http_parameters)).

:- set_setting_default(http:cors, [*]).


% iniciar server

server(Port) :-
    http_server(http_dispatch, [port(Port)]).

% routes

:- http_handler('/solMudancaMotorista', solMM, []).
:- http_handler('/solGenetico', solGenetico, []).
:- http_handler('/solEscalonamento', solEscalonamento, []).
:- http_handler('/teste', teste_http, []).

teste_http(Request) :-
    cors_enable(Request, [ methods([get,post], headers(['Access-Control-Allow-Origin'('*')]))]),
    format('Content-type: application/json~n~n'),
    http_parameters(Request,
                    [ noOrigem(NoO, []),
                      noDestino(NoD, []),
                      horaInicial(TempoI, [])
                    ]),
    load_data_http,
    load_data_mdv_http,
    format('sucesso!').


% handlers

solMM(Request) :-
    % enable do CORS para este handler
    cors_enable(Request, [ methods([get,post], headers(['Access-Control-Allow-Origin'('*')]))]),
    % aloca os parâmetros enviados no formato x-www-form-urlencoded aos átomos Prolog correspondentes
    http_parameters(Request,
                    [ noOrigem(NoO, []),
                      noDestino(NoD, []),
                      horaInicial(TempoI, [])
                    ]),
    % escreve o cabeçalho a informar que a resposta será dada no formato JSON
    format('Content-type: application/json~n~n'),
    % converte os átomos para String e Number, de forma a serem representados da forma mais correta na response
    atom_string(NoO,NoOstr),
    atom_string(NoD,NoDstr),
    atom_number(TempoI,TempoNum),
    % carrega os factos provenientes do MDR para a BC desta sessao http (módulo mdr_api_http.pl)
    load_data_http,
    % carrega os factos provenientes do MDV para a BC desta sessao http (módulo mdv_api_http.pl)
    load_data_mdv_http,
    % obtem a solução para os inputs recebidos (módulo plan_mud_mot.pl)
    plan_mud_mot_sem_findall(TempoNum,NoOstr,NoDstr,Caminho,Duracao),
    % escreve a resposta em JSON até ao array caminho
    format('{ "horaInicial": ~d, "noOrigem": "~w", "noDestino": "~w", "duracao": ~d, "caminho": [', [TempoNum, NoOstr, NoDstr, Duracao]),
    % obtem o número de ligações dentro do caminho
    length(Caminho,NLigacoes),
    % escreve o caminho no formato JSON
    print_ligacoes(Caminho,NLigacoes),
    % fecha o array e objeto JSON da response
    format('] }').

% escreve um caminho, composto por uma ou mais ligações, no formato JSON
print_ligacoes([],0).
print_ligacoes([H|L],NLigacoes) :-
    print_ligacao(H),
    N is NLigacoes-1,
    (N=\=0 -> format(','); format('')),
    print_ligacoes(L,N).

% escreve cada ligação no formato JSON
print_ligacao((NoOrigem,NoDestino,Percurso)) :-
    format('{ "noOrigem": "~w", "noDestino": "~w", "percurso": ~w }', [NoOrigem, NoDestino, Percurso] ).




% --- handler algoritmo genetico ---

solGenetico(Request) :-
    % enable do CORS para este handler
    cors_enable(Request, [ methods([get,post], headers(['Access-Control-Allow-Origin'('*')]))]),
    % aloca os parâmetros enviados no formato x-www-form-urlencoded aos átomos Prolog correspondentes
    http_parameters(Request,
                    [ servicoViaturaID(ServicoViaturaID, []),
                      escalonamentoMotoristas(EscalonamentoMotoristas, []),
                      numeroGeracoes(NumeroGeracoes, []),
                      tamanhoPopulacao(TamanhoPopulacao, []),
                      probCruzamento(ProbCruzamento, []),
                      probMutacao(ProbMutacao, []),
                      taxaPreservacao(TaxaPreservacao, []),
                      numeroGeracoesEstagnacao(NumeroGeracoesEstagnacao, []),
                      valorAlvo(ValorAlvo, []),
                      tempoLimite(TempoLimite, []),
                      escrita(Escrita, [])
                    ]),
    % escreve o cabeçalho a informar que a resposta será dada no formato JSON
    format('Content-type: application/json~n~n'),
    % converte os átomos para String e Number, de forma a serem representados da forma mais correta na response
    atom_string(ServicoViaturaID,ServicoViaturaID_str),
    read_term_from_atom(EscalonamentoMotoristas,EscalonamentoLista,[]),
    atom_number(NumeroGeracoes,NumeroGeracoes_num),
    atom_number(TamanhoPopulacao,TamanhoPopulacao_num),
    atom_number(ProbCruzamento,ProbCruzamento_num),
    atom_number(ProbMutacao,ProbMutacao_num),
    atom_number(TaxaPreservacao,TaxaPreservacao_num),
    atom_number(NumeroGeracoesEstagnacao,NumeroGeracoesEstagnacao_num),
    atom_number(ValorAlvo,ValorAlvo_num),
    atom_number(TempoLimite,TempoLimite_num),
    % carrega os factos provenientes do MDR para a BC desta sessao http (módulo mdr_api_http.pl)
    load_data_http,
    % carrega os factos provenientes do MDV para a BC desta sessao http (módulo mdv_api_http.pl)
    load_data_mdv_http,
    % obtem a solução para os inputs recebidos (módulo genetico.pl)
    gera_com_parametros(
        ServicoViaturaID_str,
        EscalonamentoLista,
        NumeroGeracoes_num,
        TamanhoPopulacao_num,
        ProbCruzamento_num,
        ProbMutacao_num,
        TaxaPreservacao_num,
        NumeroGeracoesEstagnacao_num,
        ValorAlvo_num,
        TempoLimite_num,
        Solucao,
        Escrita
        ), 
    % obtem os termos Escalonamento e Custo separados
    solucao_escalonamento_custo(Solucao,Escalonamento,Custo),
    % escreve a resposta em JSON
    format('{ "escalonamento": ~w, "custo": ~w }', [Escalonamento,Custo]).
    
% obtem os termos Escalonamento e Custo separados
solucao_escalonamento_custo(Solucao,Escalonamento,Custo) :-
    % converte o termo Solucao para string
    term_string(Solucao,Solucao_str),
    % divide a string usando como separador o *
    split_string(Solucao_str,"*","",Solucao_lista),
    % devolve os dois únicos elementos duma lista separados
    devolve_termos(Solucao_lista,Escalonamento,Custo).

% devolve os dois únicos elementos duma lista separados
devolve_termos([Primeiro|[Segundo]],Primeiro,Segundo) :-!.



% -- handler Escalonamento -----------

solEscalonamento(Request) :-
    % enable do CORS para este handler
    cors_enable(Request, [ methods([get,post], headers(['Access-Control-Allow-Origin'('*')]))]),
    % aloca os parâmetros enviados no formato x-www-form-urlencoded aos átomos Prolog correspondentes
    http_parameters(Request,
                    [ 
                      numeroGeracoes(NumeroGeracoes, []),
                      tamanhoPopulacao(TamanhoPopulacao, []),
                      probCruzamento(ProbCruzamento, []),
                      probMutacao(ProbMutacao, []),
                      taxaPreservacao(TaxaPreservacao, []),
                      numeroGeracoesEstagnacao(NumeroGeracoesEstagnacao, []),
                      valorAlvo(ValorAlvo, []),
                      tempoLimite(TempoLimite, []),
                      escrita(Escrita, [])
                    ]),
    % escreve o cabeçalho a informar que a resposta será dada no formato JSON
    format('Content-type: application/json~n~n'),
    % converte os átomos para String e Number, de forma a serem representados da forma mais correta na response
    atom_number(NumeroGeracoes,NumeroGeracoes_num),
    atom_number(TamanhoPopulacao,TamanhoPopulacao_num),
    atom_number(ProbCruzamento,ProbCruzamento_num),
    atom_number(ProbMutacao,ProbMutacao_num),
    atom_number(TaxaPreservacao,TaxaPreservacao_num),
    atom_number(NumeroGeracoesEstagnacao,NumeroGeracoesEstagnacao_num),
    atom_number(ValorAlvo,ValorAlvo_num),
    atom_number(TempoLimite,TempoLimite_num),
    % carrega os factos provenientes do MDR para a BC desta sessao http (módulo mdr_api_http.pl)
    load_data_http,
    % carrega os factos provenientes do MDV para a BC desta sessao http (módulo mdv_api_http.pl)
    load_data_mdv_http,
    % obtem a solução para os inputs recebidos (módulo genetico.pl)
    start(
        NumeroGeracoes_num,
        TamanhoPopulacao_num,
        ProbCruzamento_num,
        ProbMutacao_num,
        TaxaPreservacao_num,
        NumeroGeracoesEstagnacao_num,
        ValorAlvo_num,
        TempoLimite_num,
        Solucao,
        Escrita,
        Warnings
        ), 
    % obtem os termos Escalonamento e Custo separados
    % escreve a resposta em JSON
    length(Solucao,NLigacoes),
    format('{ "escalonamento": ['),
    escreveEsc(Solucao,NLigacoes),
    format('], "avisos": ~w }', [Warnings]).

% escreve um caminho, composto por uma ou mais ligações, no formato JSON
escreveEsc([],0).
escreveEsc([H|L],NLigacoes) :-
    escreveSV(H),
    N is NLigacoes-1,
    (N=\=0 -> format(','); format('')),
    escreveEsc(L,N).

% escreve cada ligação no formato JSON

escreveSV([H,L]) :-
    format('{"servicoTripulante":"~w",',[H]),
    length(L,NLB),
    format('"blocos":['),
    escreveListaBlocos(L,NLB),
    format(']}').

escreveListaBlocos([],0).
escreveListaBlocos([H|L],NLigacoes) :-
    escreveBloco(H),
    N is NLigacoes-1,
    (N=\=0 -> format(','); format('')),
    escreveListaBlocos(L,N).

escreveBloco(Bloco) :-
    format('"~w"', [Bloco] ).