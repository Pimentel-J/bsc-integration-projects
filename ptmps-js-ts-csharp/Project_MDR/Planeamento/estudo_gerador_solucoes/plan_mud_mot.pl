% 1ª experiência - 17 Nós/16 Linhas
:-consult('test1_data.pl').
% 2ª experiência - 21 Nós/20 Linhas
% :-consult('test2_data.pl').
% 3ª experiência - 24 Nós/22 Linhas
% :-consult('test3_data.pl').

% Para analisar:
% 1) a dimensão da lista LLCaminho(nº de soluções NSol do problema)
% 2) o tempo de geração da melhor solução (TSol)
% ### COM FINDALL ###
plan_mud_mot(Noi,Nof,LCaminho_menostrocas):-
    % gerar ligações (liga/3)
    ((retractall(liga(_,_,_)),!,gera_ligacoes());true),
    get_time(Ti),
    % gerar lista de listas (paragem1,paragem2,nº_linha)
    findall(LCaminho,caminho(Noi,Nof,LCaminho),LLCaminho),
    % verifica caminho com menor tamanho
    menor(LLCaminho,LCaminho_menostrocas),
    get_time(Tf),
    length(LLCaminho,NSol),
    TSol is Tf-Ti,
    write("Numero de Solucoes: "),writeln(NSol),
    write("Tempo de geracao da solucao: "),writeln(TSol).
% ### COM FINDALL ###


% ### SEM FINDALL - com contador de soluções ###
:- dynamic melhor_sol_ntrocas/2.
:- dynamic nSol/1.
plan_mud_mot_sem_findall(Noi,Nof,LCaminho_menostrocas):-
    % gerar ligações (liga/3)
    ((retractall(liga(_,_,_)),!,gera_ligacoes());true),
    % inicializar contador de soluções
    asserta(nSol(0)),
    get_time(Ti),
    % true -> tem sempre sucesso
    (melhor_caminho(Noi,Nof);true),
    % mostrar solução lista caminhos
    retract(melhor_sol_ntrocas(LCaminho_menostrocas,_)),
    get_time(Tf),
    TSol is Tf-Ti,
    write("Numero de Solucoes: "),forall(nSol(P), writeln(P)),
    write("Tempo de geracao da solucao: "),writeln(TSol).

% Gerar solução(s) e atualizar BC com a melhor
melhor_caminho(Noi,Nof):-
    % atualizar a BC (adicionar antes)
    asserta(melhor_sol_ntrocas(_,10000)),
    % caminho(s) entre 2 pontos rendição/recolha
    caminho(Noi,Nof,LCaminho),
    atualiza_melhor(LCaminho),
    % raciocínio pela negativa (falha, obrigando a voltar atrás e gerar novo caminho)
    % garante assim que todas as soluções são geradas (sem findall)
    fail.

% Verifica e depois atualiza a BC com o percurso menor
atualiza_melhor(LCaminho):-
    % por cada atualização, acrescenta 1 ao contador
    retract(nSol(NS)), NS1 is NS+1, asserta(nSol(NS1)),
    % recolhe o tamanho da lista de listas que está atualmente na BC
    melhor_sol_ntrocas(_,N),
    length(LCaminho,C),
    % verifica se o nº de trocas (sub-listas) é menor
    C<N,retract(melhor_sol_ntrocas(_,_)),
    % se sim, remove o anterior (retract) e adiciona o novo à BC
    asserta(melhor_sol_ntrocas(LCaminho,C)).
% ### SEM FINDALL ###


% Gerar todas as ligações diretas entre pontos de rendição/estações de recolha
:-dynamic liga/3.
gera_ligacoes():-findall(_,
    % seleção de pontos de rendição ou estações de recolha
    ((no(_,No1,t,f,_,_);no(_,No1,f,t,_,_)),
    (no(_,No2,t,f,_,_);no(_,No2,f,t,_,_)),
    % verificação se são pontos diferentes
    No1\=No2,
    % vai buscar linha(s) e retira a lista de nós pertencente a essa linha (caminho)
    linha(_,N,LNos,_,_),
    % verifica a ordem dos nós na linha
    ordem_membros(No1,No2,LNos),
    % introduz novo facto na BC
    assertz(liga(No1,No2,N))),_).

% condição: encontrar N1 e N2 constam nos restantes nós contidos no caminho
ordem_membros(No1,No2,[No1|L]):-member(No2,L),!.
ordem_membros(No1,No2,[_|L]):-ordem_membros(No1,No2,L).

% Caminho(s) entre 2 pontos rendição/recolha
caminho(Noi,Nof,LCaminho):-caminho(Noi,Nof,[],LCaminho).
% condição: Noi==Nof e então reverte os caminhos construídos
caminho(No,No,Lusadas,Lfinal):-reverse(Lusadas, Lfinal).
caminho(No1,Nof,Lusadas,Lfinal):-
    % obtém ligações entre Noi e outro nó
    liga(No1,No2,N),
    % verifica se linha não está a ser usada
    % \+member((_,_,N), Lusadas),
    % verifica se nó não está a ser usado como origem
    \+member((No2,_,_), Lusadas),
    % verifica se nó não está a ser usado como destino
    \+member((_,No2,_), Lusadas),
    % insere nova ligação
    caminho(No2,Nof,[(No1,No2,N)|Lusadas],Lfinal).

% Menor nº de troca de linhas entre 2 pontos rendição/recolha
menor_ntrocas(Noi,Nof,LCaminho_menostrocas):-
    % encontra todos os caminhos possíveis
    findall(LCaminho,caminho(Noi,Nof,LCaminho),LLCaminho),
    % verifica caminho com menor tamanho
    menor(LLCaminho,LCaminho_menostrocas).
% condição: quando a lista de caminhos apenas possui 1 elemento
menor([H],H):-!.
% retira elemento à cabeça da lista
menor([H|T],Hmenor):-
    % chamada recursiva
    menor(T,L1),
    % obtém o tamanho do caminho a observar
    length(H,C),
    % obtém o tamanho do caminho enviado recursivamente
    length(L1,C1),
    % verifica se caminho atual tem tamanho inferior ao tamanho do elemento recursivo
    ((C<C1,!,Hmenor=H);Hmenor=L1).
