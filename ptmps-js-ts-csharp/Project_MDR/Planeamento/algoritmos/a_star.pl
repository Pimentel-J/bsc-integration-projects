:- dynamic linha/5.
:- dynamic segmentos/4.

agrupa(Lista):-(findall((Temp,Dist),linha(_,_,_,Temp,Dist),Lista)).

maiorVelocidade(Res):-agrupa(Lista),listaVelocidades(Lista,Velocidades),max_member(Res,Velocidades).

listaVelocidades([(T,D)|L],[Vel|L1]):-
    listaVelocidades(L,L1),
    Vel is (D/(T*60)).

listaVelocidades([],[]):-!.

timeListAS(NoInicial,ProximoNo,StartTime,Lista):- findall(Time,
                                                        (passagens(Percurso,Time,NoInicial),
                                                        StartTime=<Time,
                                                        linha(_,Percurso,Trajeto,_,_),
                                                        sequence(NoInicial,ProximoNo,Trajeto)),
                                                        Lista).



 
waitingTime(NoInicial,ProximoNo,StartTime,Dif):- waitingTime2(NoInicial,ProximoNo,StartTime,[ProximoTempo|_]),Dif is ProximoTempo-StartTime.
waitingTime2(NoInicial,ProximoNo,StartTime,NewList):- timeListAS(NoInicial, ProximoNo ,StartTime,ListaTempos), sort(ListaTempos,NewList).
 
estimativa(No1, No2, Time):- distance(No1,No2,Distancia), maiorVelocidade(Velocidade),Time is (Distancia/Velocidade).
 
serve(No):- no(_,No,true,_,_,_);no(_,No,_,true,_,_).
 
verifica(No1, No2):- serve(No1),serve(No2).
 
mesmoPercurso(No1,No2,[T|_]):-mesmoPercurso2(No1,No2,T).
mesmoPercurso2(No1,No2,No3):-linha(_,_,Percurso,_,_), 
                              sequence(No1,No2,Percurso),
                              sequence(No3,No1,Percurso).
 
aStar(TempoInicial,Orig,Dest,Cam,Custo):-
    verifica(Orig,Dest),
    aStar2(Dest,[(_,0,TempoInicial,[Orig])],Cam,Custo).
 
aStar2(Dest,[(_,Custo,_,[Dest|T])|_],Cam,Custo):-
    reverse([Dest|T],Cam).
 
aStar2(Dest,[(_,Custo,TA,[Actual|L])|Outros],Cam,CustoReal):-
       findall((NovaSoma,NC,NT,[Next,Actual|L]),
       (segmentos(Actual,Next,CustoAN,_),
               (serve(Actual);mesmoPercurso(Actual,Next,L)),
               not(member(Next,L)),
               waitingTime(Actual,Next,TA,Dif),
               NC is Custo+CustoAN+Dif,
               NT is TA+CustoAN+Dif,
               estimativa(Next,Dest,EstX),
               NovaSoma is EstX+NC),Novos),
append(Outros,Novos,Todos),
%write(Todos),nl,
sort(Todos,TodosOrdem),
%write(TodosOrdem),nl,
aStar2(Dest,TodosOrdem,Cam,CustoReal).