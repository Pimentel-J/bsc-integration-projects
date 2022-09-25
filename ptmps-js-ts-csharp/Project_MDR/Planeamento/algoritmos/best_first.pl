:- dynamic linha/5.
:- dynamic segmentos/4.


%6 Adaptação do Best First com heurística de minimização da distância ao destino e
%  considerando os horários sobre a solução encontrada 
menorBfs(Tinicial,Orig,Dest,Cam,Dist,Time):- 
    % verifica se o ponto inicial e final são pontos de rendição ou de recolha
    verificaPontosRendicao(Orig,Dest),
    menorfs2(Dest,(0,Tinicial,[Orig]),Cam,Dist,Time).

menorfs2(Dest,(Dist,Time,[Dest|T]),Cam,Dist,Time):-
    !,
    reverse([Dest|T],Cam).
  

menorfs2(Dest,(Ca,Ta,LA),Cam,Dist,Time):-
    %Identifica o No Atual
    LA=[NoActual|_],

    %Obtem uma lista com os seguintes argumentos Estimativa Distancia (EstX)
    %Distancia Acumulada (CaX), Tempo Acumulado (TaX), Lista Resultante com a lista  anterior e px nó
    findall((EstX,CaX,TaX,[NoNext|LA]),
    (segmentos(NoActual,NoNext,TX,CX),
        not(member(NoNext,LA)),

        %Verifica se o No Atual é pontoRendicao/Recolha ou os nós do segmento pertencem à mesma linha
        (verificaPonto(NoActual);mesmoPercursoBF(NoActual,NoNext,LA)),

        %Obtem o tempo atual (Ta) e o Tempo Espera Paragem (Dif)
        waitingTimeBF(NoActual,NoNext,Ta,Dif),

        %Obtem a melhor distância EstX pelo espaço metrico euclediano
        estimadist(NoNext,Dest,EstX),

        %obtem os valores acumulados
        TaX is Ta+Dif+TX,
        CaX is Ca+CX),
        %write(NoActual),write(' '),write(NoNext),write(' '),write(Ta),write(' '), write(CX), write(' '),write(EstX),
        %write(' '),write(TX), write(' '), write(Dif), nl),
        Novos),

    %ordena pela estimativa de distancia     
    sort(Novos,NovosOrd),
    NovosOrd = [(_,CM,TM,Melhor)|_],
    %write(Melhor),nl,
    menorfs2(Dest,(CM,TM,Melhor),Cam,Dist,Time).

%predicado para validar se o próximo nó pertencem à mesma linha 
%nós identificados No1 (NoActual), No2 (NoNext) e Na (NoAnterior ao NoActual)
mesmoPercursoBF(No1,No2,[_,Na|_]):-
        %write('2'+No1+' '+No2+' '+Na),nl,
        mesmoPercurso2BF(No1,No2,Na).
mesmoPercurso2BF(No1,No2,No3):-
        linha(_,_,Percurso,_,_), 
        sequence(No1,No2,Percurso), sequence(No3,No1,Percurso).

%obtem a distancia pelo metodo euclidiano
estimadist(No1, No2, Distancia):- distance(No1,No2,Distancia).

%Entre o no atual (NoInicial) e o próximo no (ProximoNo), a partir de uma determinada hora
%de chegada à paragem calcula o tempo de espera(Dif)
%A ordenaçao serve para ordenar a listaTempos fazendo a diferença entre o tempo de chegada do nó
% e o tempo de passagem do próximo   
waitingTimeBF(NoInicial,ProximoNo,StartTime,Dif):- 
            waitingTimeBF2(NoInicial,ProximoNo,StartTime,[ProximoTempo|_]),
            %write(ProximoTempo),write(' '),write(NoInicial),write(' '),write(ProximoNo),nl,
            Dif is ProximoTempo-StartTime.
waitingTimeBF2(NoInicial,ProximoNo,StartTime,NewList):- 
            timeListBF(NoInicial, ProximoNo ,StartTime,ListaTempos), sort(ListaTempos,NewList).

%Obtem uma lista de tempos provenientes de passagens
%cuja o tempo de passagem (time) é superior ou igual ao tempo de chegada ao nó de troca (StartTime)
%Obtem o percurso nas passagens, obtem o trajeto nas linhas daquele percurso e valida
%se os nó inicial e final se enquandram no trajeto atraves do predicado sequence
timeListBF(NoInicial,ProximoNo,StartTime,Lista):- 
            findall(Time,
                    (passagens(Percurso,Time,NoInicial),
                    (StartTime=<Time),
                    linha(_,Percurso,Trajeto,_,_),
                    sequence(NoInicial,ProximoNo,Trajeto)),
                    Lista).

%verifica se 2 pontos são de rendição ou recolha
verificaPontosRendicao(No1, No2):- 
        (verificaPonto(No1),verificaPonto(No2)).

verificaPonto(No):-
         (no(_,No,true,_,_,_);no(_,No,_,true,_,_)).
