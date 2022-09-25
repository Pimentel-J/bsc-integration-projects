tstring([X],X).
tstring([X|L],Res):-
	tstring(L,Res1),
	string_concat(Res1,',',S3),
	string_concat(S3,X,Res).

fixProblemService(IEscolha,Fixed):-
	blocosInf(L),
	dinner(Dinner),
	restos1(Second),
	states1(First),
	servicoV(Servico),
	servicoViatura(Servico,_,_,List),
	inverte(List,Lista),
	freeDrivers(Usados),
	qtde(L,Q),
	(((Q==1),!,(
		primeiroElemento(L,E),
		bloco(E,Start,End,_,_),
		encontraind(E,Lista,Ind),
		encontraeElem(Ind,IEscolha,MotWarning),
		(((Usados==[]),!,(passa(Mot,0)));(
		findDriver(Usados,Start,End,Mot,_)
		)),
		(((Mot==0),!,(passa(IEscolha,Fixed), 
		tstring(L,Res),
		string_concat('O motorista ', MotWarning, String3),
		string_concat(' esta a trabalhar os blocos ',Res,Str1),string_concat(Str1,' fora do seu horario, no servico ',Str2),string_concat(Str2,Servico,String4),
		string_concat(String3,String4,FinalString),emboxa((MotWarning,FinalString), Warn),
		warnings(Warnings),append(Warn,Warnings,NewWarn),(retract(warnings(_));true),asserta(warnings(NewWarn))
		 ));(		
		encontraind(E,Lista,Indice),
		cortaLista(IEscolha,Parte1,Indice),
		qtde(IEscolha,QE),
		Ind2 is(QE-Indice-1),
		cortaListaFim(IEscolha,Ind2,Parte2,_),
		emboxa(Mot,BX),
		append(Parte1,BX,PP),append(PP,Parte2,Fixed),
		removeOne(Mot,Usados,Usados2),(retract(freeDrivers(_));true),asserta(freeDrivers(Usados2)),
		removeOne(Mot,Dinner,Dinner2),(retract(dinner(_));true),asserta(dinner(Dinner2)),
		removeSecond(Mot,Second,Second2),(retract(restos1(_));true),asserta(restos1(Second2)),		
		removeFirst(Mot,First,First2),(retract(states1(_));true),asserta(states1(First2))
	))));(
		primeiroElemento(L,Primeiro),
		ultimoElemento(L,Ultimo),
		bloco(Primeiro,Start,_,_,_),
		bloco(Ultimo,_,End,_,_),
		primeiroElemento(_Sol,Pri),
		encontraeElem(Pri,IEscolha,MotWarning),
		(((Usados==[]),!,(passa(Mot,0)));(
		findDriver(Usados,Start,End,Mot,_)
		)),
		(((Mot==0),!,(passa(IEscolha,Fixed), 
		tstring(L,Res),
		string_concat('O motorista ', MotWarning, String3),
		string_concat(' esta a trabalhar os blocos ',Res,Str1),string_concat(Str1,' fora do seu horario, no servico ',Str2),string_concat(Str2,Servico,String4),
		string_concat(String3,String4,FinalString),emboxa((MotWarning,FinalString),Warn),
		warnings(Warnings),append(Warn,Warnings,NewWarn),(retract(warnings(_));true),asserta(warnings(NewWarn))
		 ));(
		encontraind(Primeiro,Lista,IndiceI),
		encontraind(Ultimo,Lista,IndiceF),
		cortaLista(IEscolha,Parte1,IndiceI),
		qtde(IEscolha,QE),
		Ind2 is(QE-IndiceF-1),
		cortaListaFim(IEscolha,Ind2,Parte2,_),
		newList(Mot,Q,BX),
		append(Parte1,BX,PP),append(PP,Parte2,Fixed),
		removeOne(Mot,Dinner,Dinner2),(retract(dinner(_));true),asserta(dinner(Dinner2)),
		removeOne(Mot,Usados,Usados2),(retract(freeDrivers(_));true),asserta(freeDrivers(Usados2)),
		removeSecond(Mot,Second,Second2),(retract(restos1(_));true),asserta(restos1(Second2)),		
		removeFirst(Mot,First,First2),(retract(states1(_));true),asserta(states1(First2))
	))
	)).

removeFirst(_,[],[]).
removeFirst(Mot,[X|L],L2):-
	removeFirst(Mot,L,L3),
	getP(X,XMot),
	(((XMot==Mot),!,(
		passa(L2,L3)
	));(
		emboxa(X,BX),append(BX, L3, L2)
	)).

removeSecond(_,[],[]).
removeSecond(Mot,[X|L],L2):-
	removeSecond(Mot,L,L3),
	getK(X,XMot),
	(((XMot==Mot),!,(
		passa(L2,L3)
	));(
		emboxa(X,BX),append(BX, L3, L2)
	)).

fixProblemLunch(IEscolha,Fixed):-
	motoristas(M),
	dinner(Dinner),
	restos1(Second),
	states1(First),
	freeDrivers(Usados),
	problemLunch(IEscolha,M,Sol),
	(((Sol==0),!,(
		passa(IEscolha,Fixed)
	));(
	primeiroElemento(Sol,Fim),
	ultimoElemento(Sol,Inicio),
	servicoV(Serv),
	servicoViatura(Serv,_,_,List),
	inverte(List,Lista),
	cortaLista(Lista,NewLista,Fim),
	cortaListaFim(NewLista,Inicio,Final,_),
	getBlocks(Final,ListaAnomalia,_,_),
	qtde(ListaAnomalia,Q),
	(((Q==0),!,(passa(IEscolha,Fixed)));(
		(((Q==1),!,(
		primeiroElemento(ListaAnomalia,E),
		bloco(E,Start,End,_,_),
		lunch(Drivers),
		primeiroElemento(Sol,Pri),
		encontraeElem(Pri,IEscolha,MotWarning),
		ultimoElemento(ListaAnomalia,Ultimo),
		bloco(Ultimo,_,End1,_,_),
		hfAlmoco(HfAlmoco),
		Serve is (End1+3600),
		((((Second==[]);(First==[]);(Drivers==[])),!,(passa(Mot,0)));(
		findDriver(Drivers,Start,End,Mot,_)
		)),
		(((Mot==0),!,(
		(((Serve<HfAlmoco),!,(
			passa(IEscolha,Fixed)
		));(
		passa(IEscolha,Fixed), 
		string_concat('O motorista ', MotWarning, String3),
		string_concat(' nao teve tempo de almoco no servico ',Serv,String4),
		string_concat(String3,String4,FinalString),emboxa((MotWarning,FinalString), Warn),
		warnings(Warnings),append(Warn,Warnings,NewWarn),(retract(warnings(_));true),asserta(warnings(NewWarn))
		 ))));(		
		encontraind(E,Lista,Indice),
		cortaLista(IEscolha,Parte1,Indice),
		qtde(IEscolha,QE),
		Ind2 is(QE-Indice-1),
		cortaListaFim(IEscolha,Ind2,Parte2,_),
		emboxa(Mot,BX),
		append(Parte1,BX,PP),append(PP,Parte2,Fixed),
		removeOne(Mot,Drivers,NewDrivers),
		removeOne(Mot,Dinner,Dinner2),(retract(dinner(_));true),asserta(dinner(Dinner2)),
		removeOne(Mot,Usados,Usados2),(retract(freeDrivers(_));true),asserta(freeDrivers(Usados2)),
		removeSecond(Mot,Second,Second2),(retract(restos1(_));true),asserta(restos1(Second2)),		
		removeFirst(Mot,First,First2),(retract(states1(_));true),asserta(states1(First2)),
		(retract(lunch(_));true),asserta(lunch(NewDrivers))
		))
	));(
		primeiroElemento(ListaAnomalia,Primeiro),
		ultimoElemento(ListaAnomalia,Ultimo),
		bloco(Primeiro,Start,_,_,_),
		bloco(Ultimo,_,End,_,_),
		lunch(Drivers),
		primeiroElemento(Sol,Pri),
		encontraeElem(Pri,IEscolha,MotWarning),
		((((Second==[]);(First==[]);(Drivers==[])),!,(passa(Mot,0)));(
		findDriver(Drivers,Start,End,Mot,_)
		)),
		hfAlmoco(HfAlmoco),
		Serve is (End+3600),
		(((Mot==0),!,(
		(((Serve<HfAlmoco),!,(
			passa(IEscolha,Fixed)
		));(			
			passa(IEscolha,Fixed), 
		string_concat('O motorista ', MotWarning, String3),
		string_concat(' nao teve tempo de almoco no servico ',Serv,String4),
		string_concat(String3,String4,FinalString),emboxa((MotWarning,FinalString), Warn),
		warnings(Warnings),append(Warn,Warnings,NewWarn),(retract(warnings(_));true),asserta(warnings(NewWarn))
		 ))));(
		encontraind(Primeiro,Lista,IndiceI),
		encontraind(Ultimo,Lista,IndiceF),
		cortaLista(IEscolha,Parte1,IndiceI),
		qtde(IEscolha,QE),
		Ind2 is(QE-IndiceF-1),
		cortaListaFim(IEscolha,Ind2,Parte2,_),
		newList(Mot,Q,BX),
		append(Parte1,BX,PP),append(PP,Parte2,Fixed),
		removeOne(Mot,Drivers,NewDrivers),
		removeOne(Mot,Dinner,Dinner2),(retract(dinner(_));true),asserta(dinner(Dinner2)),
		removeOne(Mot,Usados,Usados2),(retract(freeDrivers(_));true),asserta(freeDrivers(Usados2)),
		removeSecond(Mot,Second,Second2),(retract(restos1(_));true),asserta(restos1(Second2)),		
		removeFirst(Mot,First,First2),(retract(states1(_));true),asserta(states1(First2)),
		(retract(lunch(_));true),asserta(lunch(NewDrivers))
		))
	)))
	))).	

fixProblemDinner(IEscolha,Fixed):-
	motoristas(M),
	restos1(Second),
	states1(First),
	freeDrivers(Usados),
	problemDinner(IEscolha,M,Sol),
	(((Sol==0),!,(
		passa(IEscolha,Fixed)
	));(
	primeiroElemento(Sol,Fim),
	ultimoElemento(Sol,Inicio),
	servicoV(Serv),
	servicoViatura(Serv,_,_,List),
	inverte(List,Lista),
	cortaLista(Lista,NewLista,Fim),
	cortaListaFim(NewLista,Inicio,Final,_),
	getBlocks(Final,ListaAnomaly,_,_),
	(((ListaAnomaly==[]),!,(passa(ListaAnomalia,Final)));(passa(ListaAnomalia,ListaAnomaly))),
	qtde(ListaAnomalia,Q),
	(((Q==1),!,(
		primeiroElemento(ListaAnomalia,E),
		bloco(E,Start,End,_,_),
		dinner(Drivers),
		primeiroElemento(Sol,Pri),
		encontraeElem(Pri,IEscolha,MotWarning),
		((((Second==[]);(First==[]);(Drivers==[])),!,(passa(Mot,0)));(
		findDriver(Drivers,Start,End,Mot,_)
		)),
		(((Mot==0),!,(passa(IEscolha,Fixed), 
		string_concat('O motorista ', MotWarning, String3),
		string_concat(' nao teve tempo de jantar no servico ',Serv,String4),
		string_concat(String3,String4,FinalString),emboxa((MotWarning,FinalString), Warn),
		warnings(Warnings),append(Warn,Warnings,NewWarn),(retract(warnings(_));true),asserta(warnings(NewWarn))
		 ));(		
		encontraind(E,Lista,Indice),
		cortaLista(IEscolha,Parte1,Indice),
		qtde(IEscolha,QE),
		Ind2 is(QE-Indice-1),
		cortaListaFim(IEscolha,Ind2,Parte2,_),
		emboxa(Mot,BX),
		append(Parte1,BX,PP),append(PP,Parte2,Fixed),
		removeOne(Mot,Drivers,NewDrivers),
		removeOne(Mot,Usados,Usados2),(retract(freeDrivers(_));true),asserta(freeDrivers(Usados2)),
		removeSecond(Mot,Second,Second2),(retract(restos1(_));true),asserta(restos1(Second2)),		
		removeFirst(Mot,First,First2),(retract(states1(_));true),asserta(states1(First2)),
		(retract(dinner(_));true),asserta(dinner(NewDrivers))
		))
	));(
		primeiroElemento(ListaAnomalia,Primeiro),
		ultimoElemento(ListaAnomalia,Ultimo),
		bloco(Primeiro,Start,_,_,_),
		bloco(Ultimo,_,End,_,_),
		dinner(Drivers),
		primeiroElemento(Sol,Pri),
		encontraeElem(Pri,IEscolha,MotWarning),
		((((Second==[]);(First==[]);(Drivers==[])),!,(passa(Mot,0)));(
		findDriver(Drivers,Start,End,Mot,_)
		)),
		(((Mot==0),!,(passa(IEscolha,Fixed), 
		string_concat('O motorista ', MotWarning, String3),
		string_concat(' nao teve tempo de jantar no servico ',Serv,String4),
		string_concat(String3,String4,FinalString),emboxa((MotWarning,FinalString), Warn),
		warnings(Warnings),append(Warn,Warnings,NewWarn),(retract(warnings(_));true),asserta(warnings(NewWarn))
		 ));(
		encontraind(Primeiro,Lista,IndiceI),
		encontraind(Ultimo,Lista,IndiceF),
		cortaLista(IEscolha,Parte1,IndiceI),
		qtde(IEscolha,QE),
		Ind2 is(QE-IndiceF-1),
		cortaListaFim(IEscolha,Ind2,Parte2,_),
		newList(Mot,Q,BX),
		append(Parte1,BX,PP),append(PP,Parte2,Fixed),
		removeOne(Mot,Drivers,NewDrivers),
		removeOne(Mot,Usados,Usados2),(retract(freeDrivers(_));true),asserta(freeDrivers(Usados2)),
		removeSecond(Mot,Second,Second2),(retract(restos1(_));true),asserta(restos1(Second2)),		
		removeFirst(Mot,First,First2),(retract(states1(_));true),asserta(states1(First2)),
		(retract(dinner(_));true),asserta(dinner(NewDrivers))
		))
	))
	)).	

encontraind(E,[E|_],0):- !.
encontraind(E,[_|T],I):- encontraind(E,T,X), I is X + 1.

findDriver([],_,_,0,0).
findDriver([X|Drivers],Start,End,Mot,Flag):-
	findDriver(Drivers,Start,End,Mot1,Flag1),
	(((Flag1 == 0),!,(
	restos1(Second),
	states1(First),
	existeSecond(X,Second,Existe,Sol),
	(((Existe==1),!,(
		horariomotorista(X,StartTime,EndTime,_,_),
		getL(Sol,Temp),
		getK(Sol,M1),
		((((StartTime<Start),(End<Temp)),!,(
			passa(M1,Mot),passa(Flag,1)
		));(
			passa(Flag,Flag1),passa(Mot,Mot1)
		))		
	));(
		getDriverP1(X,First,Obj),
		(((Obj==0),!,(passa(Flag,Flag1),passa(Mot,Mot1)));(
		horariomotorista(X,_,EndTime,_,_),
		getO(Obj,T),
		((((Start>T),(End<EndTime)),!,(
			passa(X,Mot),passa(Flag,1)
		));(
			passa(Flag,Flag1),passa(Mot,Mot1)
		))))
	))));(
			passa(Flag,Flag1),passa(Mot,Mot1)
	)).

getO((_,_,T),T).
getP((D,_,_),D).

getDriverP1(_,[],0).
getDriverP1(Mot,[X|State],Obj):-
	getDriverP1(Mot,State,Obj1),
	getP(X,Mot2),
	(((Mot2==Mot),!,(
		passa(X,Obj)
	));(
		passa(Obj,Obj1)
	)).

existeSecond(Mot,[X|Second],Existe,Valor):-
	existeSecond(Mot,Second,Existe1,Valor1),
	getK(X,MotX),
	(((MotX==Mot),!,(
		passa(Existe,1),passa(Valor,X)
	));(
		passa(Existe,Existe1),passa(Valor1,Valor)
	)).
existeSecond(_,[],0,0):-!.

getBlocks([],[],0,0).
getBlocks([X|Final],Lista,Soma,Flag):-
	getBlocks(Final,Lista2,Soma2,Flag2),
	hiAlmoco(AlmocoInicial),
	hfAlmoco(AlmocoFinal),
	bloco(X,StartTime,EndTime,_,_),
	((((StartTime>AlmocoInicial),(EndTime<AlmocoFinal)),!,(
			Dif is (EndTime-StartTime),
	(((Soma2>=3600),!,(
		passa(Soma2,Soma),passa(Flag,1),passa(Lista,Lista2)
	));(
		Soma is (Soma2+Dif),passa(Flag,Flag2),emboxa(X,BX),append(BX,Lista2,Lista)
	))
	));(
		passa(Soma2,Soma),passa(Flag,Flag2),passa(Lista,Lista2)		
	)).

getBlocks2([],[],0,0).
getBlocks2([X|Final],Lista,Soma,Flag):-
	getBlocks2(Final,Lista2,Soma2,Flag2),
	hiJantar(AlmocoInicial),
	hfJantar(AlmocoFinal),
	bloco(X,StartTime,EndTime,_,_),
	((((StartTime>AlmocoInicial),(EndTime<AlmocoFinal)),!,(
			Dif is (EndTime-StartTime),
	(((Soma2>=3600),!,(
		passa(Soma2,Soma),passa(Flag,1),passa(Lista,Lista2)
	));(
		Soma is (Soma2+Dif),passa(Flag,Flag2),emboxa(X,BX),append(BX,Lista2,Lista)
	))
	));(
		passa(Soma2,Soma),passa(Flag,Flag2),passa(Lista,Lista2)		
	)).

problemLunch(_,[],0).
problemLunch(IEscolha,[X|M],Sol):-
	problemLunch(IEscolha,M,Sol1),
	problemLunch2(IEscolha,X,S1),
	(((S1==0),!,(
		passa(Sol,Sol1)
	));(
		passa(S1,Sol)
	)).

problemLunch2(Seq,Mot,S):-
	getTempos(Seq,Mot,Lista,_),
	getFirstPart(Lista,FParte,_,_),
	primeiroElemento(FParte,V),
	getAssocBlock(Lista, ListaBlocos),
	cortaListaFim(ListaBlocos,V,NList,_),
	primeiroElemento(NList,BF),
	bloco(BF,_,End,_,_),
	ultimoElemento(NList,BI),
	bloco(BI,Start,_,_,_),
	hfAlmoco(Hfinal),
	hiAlmoco(Hinicial),
	((((Start<Hfinal),(Start>Hinicial),(End>Hfinal)),!,(passa(S,FParte)));(passa(S,0))).

problemDinner(_,[],0).
problemDinner(IEscolha,[X|M],Sol):-
	problemDinner(IEscolha,M,Sol1),
	problemDinner2(IEscolha,X,S1),
	(((S1==0),!,(
		passa(Sol,Sol1)
	));(
		passa(S1,Sol)
	)).

problemDinner2(Seq,Mot,S):-
	getTempos(Seq,Mot,Lista,_),
	getFirstPart(Lista,FParte,_,_),
	primeiroElemento(FParte,V),
	getAssocBlock(Lista, ListaBlocos),
	cortaListaFim(ListaBlocos,V,NList,_),
	primeiroElemento(NList,BF),
	bloco(BF,_,End,_,_),
	ultimoElemento(NList,BI),
	bloco(BI,Start,_,_,_),
	hfJantar(Hfinal),
	hiJantar(Hinicial),
	((((Start<Hfinal),(Start>Hinicial),(End>Hfinal)),!,(passa(S,FParte)));(passa(S,0))).

getFirstPart([X],[X],X,0).
getFirstPart([X|ListaBlocos],Lista,Ant,Flag):-
	getFirstPart(ListaBlocos,Lista1,Ant1,Flag1),
	Dif is (X-Ant1),
	((((Dif==1),(Flag1==0)),!,(
		emboxa(X,BX),append(BX,Lista1,Lista),passa(Flag,Flag1),Ant is X
	));(
		passa(Lista,Lista1),passa(Flag,1),Ant is X
	)).
