
% ### SEM FINDALL ###
:- dynamic melhor_sol_duracao/2.
:- dynamic nSol/1.


plan_mud_mot_sem_findall(TInicial,Noi,Nof,LCaminho_menorDuracao,TempoViagem):-
    % true -> tem sempre sucesso
    (melhor_caminho(TInicial,Noi,Nof);true),
    % remove melhor solução encontrada
    retract(melhor_sol_duracao(LCaminho_menorDuracao,TempoFinal)),
    % calcula a duração da viagem
    TempoViagem is TempoFinal - TInicial.


% Gerar solução(s) e atualizar BC com a melhor
melhor_caminho(TInicial,Noi,Nof):-
    % atualizar a BC (adicionar antes)
    asserta(melhor_sol_duracao(_,999999)),
    % caminho(s) entre 2 pontos rendição/recolha
    caminho(Noi,Nof,LCaminho),
    atualiza_melhor(TInicial,LCaminho),
    % raciocínio pela negativa (falha, obrigando a voltar atrás e gerar novo caminho)
    % garante assim que todas as soluções são geradas (sem findall)
    fail.

% Verifica e depois atualiza a BC com o percurso menor
atualiza_melhor(TInicial,LCaminho):-
    % recolhe o tempo do percurso que está atualmente na BC
    melhor_sol_duracao(_,MelhorTempoAnterior),
    % calcula duração do caminho encontrado
    calcula_tf_caminho(TInicial,LCaminho,TempoFinal),
    % verifica se o caminho é mais rápido
    TempoFinal < MelhorTempoAnterior,
    retract(melhor_sol_duracao(_,_)),
    % se sim, remove o anterior (retract) e adiciona o novo à BC
    asserta(melhor_sol_duracao(LCaminho,TempoFinal)).


% calcula o tempo final de um caminho, dado um tempo inicial
calcula_tf_caminho(TFinal,[],TFinal):-!.
calcula_tf_caminho(TInicial,[Ligacao|Caminho],TFCaminho):-
    % calcula o tempo final da primeira ligação à cabeça da lista de ligações, que é um caminho
    calcula_tf_ligacao(TInicial,Ligacao,TFLigacao),
    % calcula o tempo final das próximas ligações existentes no caminho, tendo como tempo inicial o tempo final da ligação anterior
    calcula_tf_caminho(TFLigacao,Caminho, TFCaminho).

% calcula o tempo final de uma ligação, dado um tempo inicial
calcula_tf_ligacao(TInicial,(No1,No2,NLinha),TFLigacao):-
    % o tempo final será igual ao tempo de passagem no No2
    calcula_tf_segmentos_ligacao(TInicial,No1,No2,NLinha,TFSegmentos),
    TFLigacao is TFSegmentos.

% calcula o tempo final de uma ligação como somatório dos tempos dos segmentos
calcula_tf_segmentos_ligacao(TInicial,NoInicial,NoFinal,NLinha,TFSegmentos):-
    calcula_tf_segmentos(TInicial,NoInicial,NoInicial,NoFinal,NLinha,TFSegmentos).

% calcula o tempo final de uma ligação como somatório dos tempos dos segmentos
calcula_tf_segmentos(TFSegmentos,_,NoFinal,NoFinal,_,TFSegmentos):-!.
calcula_tf_segmentos(TInicial,No1,_,NoFinal,NLinha,TFSegmentos):-
    % Encontrar o nó seguinte ao No1, com base nas seguintes condições:
    segmentos(No1,NoSeg,DuracaoSeg,_),
    linha(_,NLinha,LNos,_,_),
    ordem_membros(No1,NoSeg,LNos),
    % obtem o tempo de passagem no primeiro nó
    passagem_no(TInicial,NLinha,No1,TPass1),
    % somar a duracao do segmento para garantir que o próximo nó encontrado é do mesmo autocarro (e não outro que por coincidência passa no nó mas em sentido contrário, por exemplo)
    TPass1Min is TPass1 + DuracaoSeg,
    % obtem o tempo de passagem do nó seguinte
    passagem_no(TPass1Min,NLinha,NoSeg,TPassSeg),
    % soma ao tempo final os segmentos seguintes
    calcula_tf_segmentos(TPassSeg,NoSeg,NoSeg,NoFinal,NLinha,TFSegmentos).


% obtem o tempo de passagem de um dado No, numa dada Linha, após um tempo inicial
passagem_no(TInicial,NLinha,No,TPass):-
    % obtem o primeiro tempo de passagem disponível, imediatamente a seguir ao tempo inicial 
    lista_tempos_no(TInicial,NLinha,No,[TPass|_]).
 
% obtem uma lista ordenada (asc) de tempos de passagem de um dado No, numa dada Linha, após um tempo inicial
lista_tempos_no(TInicial,NLinha,No,LTempos):-
    findall(TPass, (    passagens(NLinha,TPass,No),
                        TPass >= TInicial
                    ), LTemposRandom),
    % ordena a lista de tempos obtida
    sort(LTemposRandom,LTempos).

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

