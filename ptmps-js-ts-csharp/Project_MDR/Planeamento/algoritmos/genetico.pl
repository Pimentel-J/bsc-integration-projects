
:-dynamic geracoes/1.
:-dynamic populacao/1.
:-dynamic prob_cruzamento/1.
:-dynamic prob_mutacao/1.
:-dynamic perc_preservacao/1.
:-dynamic geracaoEstaganar/1.
:-dynamic valorObjetivo/1.
:-dynamic tempoInicial/1.
:-dynamic tempoProcessamento/1.
:-dynamic servicoV/1.
:-dynamic motoristas/1.
:-dynamic best/1. 
:-dynamic escreve/1.
:-dynamic flag_Almoco/1.
:-dynamic flag_Jantar/1.
:-dynamic flag_Servico/1.
:-dynamic save/1.
:-dynamic blocosInf/1.
:-dynamic warnings/1.
:-dynamic freeDrivers/1.
:-dynamic restos1/1.
:-dynamic states1/1.
:-dynamic globalWarnings/1.
%linha("Paredes_Estação (Paredes)","39",["Paredes","Estação (Paredes)"],2,1500).


% constantes
hiAlmoco(39600).
hfAlmoco(54000).
hiJantar(64800).
hfJantar(79200).
clean([]).

%servicoViatura("15",69600,76920,["15","233","234","235"]).
%horariomotorista("276",25200,61200,28800, [21600,7200]).


gera_heuristica_pop(1).


lista_motoristas_nworkblocks2(SV,Esc,Lista):-
    (((servicoViatura(SV,_,_,_),!,
    (
	(retract(servicoV(_));true),asserta(servicoV(SV)),
    createList(SV,Esc,Lista),
	(retract(escala(_));true),asserta(escala(Esc)),
	saveMoto(Esc,ListaMoto),
	((retract(motoristas(_));true),asserta(motoristas(ListaMoto)))
	));(escreve(true),write('Numero de servico de viatura invalido');true))).

inicializa2(NG,DP,P1,P2,P3,MG,Valor,Tempo):- /* vai ler o numero de geracoes que vai trabalhar*/
    (retract(geracoes(_));true), ((NG>0,!,asserta(geracoes(NG)));
	((escreve(true),write('Limite de Geracoes nao estabelecido'),nl;true),asserta(geracoes(99999999)))),
	(retract(populacao(_));true), servicoV(SV),workblocks(SV,Num),permutacoes(Num,Resultado),
	((DP<Resultado,!,asserta(populacao(DP)));
	((escreve(true),write('Dimensão da população superior as combinações possiveis, alterado para ');true),
	(escreve(true),write(Num),nl;true),asserta(populacao(Num)))),
	PC is P1/100, 
	(retract(prob_cruzamento(_));true), 	asserta(prob_cruzamento(PC)),
	PM is P2/100, 
	(retract(prob_mutacao(_));true), asserta(prob_mutacao(PM)),
	((P3<50,!,PP is P3/100);((escreve(true),write('Valor de preservação demasiado elevado para');true),
	(escreve(true),write(' garantir evolução nas gerações. Valor retificado para 50%');true),nl,PP is 50/100)), 
	(retract(perc_preservacao(_));true), 	asserta(perc_preservacao(PP)),
	(retract(geracaoEstaganar(_));true), ((MG>0,!,asserta(geracaoEstaganar(MG)));
	((escreve(true),write('Geracoes ate estagnacao nao estabelecido'),nl;true),asserta(geracaoEstaganar(9999999)))),
	(retract(valorObjetivo(_));true), 	asserta(valorObjetivo(Valor)),
	(retract(tempoProcessamento(_));true), ((Tempo>0,!,asserta(tempoProcessamento(Tempo)));
	((escreve(true),write('Limite de tempo de processamento nao estabelecido'),nl;true),asserta(tempoProcessamento(600)))),
	(retract(tempoInicial(_));true), get_time(Ti),asserta(tempoInicial(Ti)).

/* SV= Servico Viatura , Esc =Escalonamento, NG =Numero de Gerações, DP= tamanho da população, P1 = Probabilidade de Cruzamento,
 P2= Probabilidade de Mutação, P3= Taxa de Preservação, MG- Numero de Gerações ate estagnar, Valor= Valor a atingir, 
 Tempo= tempo permitido para trabalhar, Escrita [true/false] = se escreve todas as infos para o ecrã, Solucao = devolve a lista com a melhor solução de escalonamento */

get1([X|_],X).
get2(L,X):-cortaLista(L,NL,2),ultimoElemento(NL,X).
get3(L,X):-cortaLista(L,NL,3),ultimoElemento(NL,X).
get4(L,X):-ultimoElemento(L,X).

heuristica(Serv,Lista,Res):-
	servicoViatura(Serv,StartTime,_,ListaBlocos),
	qtde(Lista,Quant),
	get1(Lista,First),
	getK(First,Mot1),
	getL(First,Q1),
	(((Quant==1),!,(	
		emboxa((Mot1,Q1),Res)
	));(
		get2(Lista,Second),
		getK(Second,Mot2),
		getL(Second,Q2),
		(((Quant==2),!,(passa(State,2)));(passa(State,0))),
		heuristicPart(Serv,StartTime,ListaBlocos,Mot1,Mot2,Q1,Q2,Last,State),
		(((Quant == 2),!,(
			passa(Last,Res)
		));(
			get3(Lista,Third),
			getK(Third,Mot3),
			getL(Third,Q3),
			(((Quant>3),!,(
				get4(Lista,Fourth),
				getK(Fourth,Mot4),
				getL(Fourth,Q4),
				DoneSoFar is (Q1+Q2),
				cortaLista(ListaBlocos,NewList,DoneSoFar),
				ultimoElemento(NewList,UltimoM1),
				bloco(UltimoM1,_,EndM,_,_),
				heuristicPart(Serv,EndM,ListaBlocos,Mot3,Mot4,Q3,Q4,Last2,1),
				append(Last,Last2,Res)
			));(
				emboxa((Mot3,Q3),L3),
				append(Last,L3,Res)
			)))))).

heuristicPart(Serv,StartTime,ListaBlocos,Mot1,Mot2,Q1,Q2,Last,State):-
	horariomotorista(Mot1,_,_,_,[Tempo1|_]),
	(((Tempo1>14400),!,(passa(NTempo1,14400)));(passa(NTempo1,Tempo1))),
	getBlocos(Serv,Mot1,StartTime,NTempo1,Numero,_),
	cortaLista(ListaBlocos,NewList,Numero),
	ultimoElemento(NewList,UltimoM1),
	bloco(UltimoM1,_,EndM1,_,_),
	horariomotorista(Mot2,_,_,_,[Tempo2|_]),
	(((Tempo2>14400),!,(passa(NTempo2,14400)));(passa(NTempo2,Tempo2))),	
	((((State ==1);(State==2)),!,(passa(Q2,Numero2)));(
	getBlocos(Serv,Mot2,EndM1,NTempo2,Numero2,_)
	)),

	Total is (Q1+Q2),
	TR is (Numero2+Numero),
	((((TR>Total),(State==1)),!,(Numero1 is Numero-1));(
	passa(Numero1,Numero)
	)),
	Resto1 is (Q1-Numero1),
	Resto2 is (Q2-Numero2),
	emboxa((Mot1,Numero1),F1),
	emboxa((Mot2,Numero2),F2),
	(((Resto1>0),!,(
		(((Resto2>0),!,(
			emboxa((Mot1,Resto1),F3),
			emboxa((Mot2,Resto2),F4),
			append(F1,F2,LF1),append(F3,F4,LF2),append(LF1,LF2,Last)
		));(
			emboxa((Mot1,Resto1),F3),
			append(F1,F2,LF1),append(LF1,F3,Last)
		))
	));(
		append(F1,F2,Last)
	)).

getBl1(X,StartTime,Stripe, Numero):-
	servicoViatura(X,_,_,ListaBlocos),
	inverte(ListaBlocos,Invertida),
	getNBl1(Invertida,StartTime,Stripe,Numero,_,_).

getNBl1([],_,_,0,0,0).
getNBl1([X|Invertida],InicioTurno,Stripe,Numeros,Real,AT):-
	getNBl1(Invertida,InicioTurno,Stripe, Numeros1,Real1,AT1),
	bloco(X,StartTime,EndTime,_,_),
	Dif is EndTime-StartTime,
	(((((Real1+Dif)>Stripe);(AT == 1)),!,(Numeros is Numeros1,Real is Real1, AT is 1));(Real is Real1+Dif, Numeros is Numeros1+1, AT is AT1)).


start(NG,DP,P1,P2,P3,MG,Valor,Tempo, Solucao,Escrita,Warn):-
	findEscalonamentos(Lista,Use,States,Rest),
	(retract(globalWarnings(_));true),asserta(globalWarnings([])),
	(retract(restos1(_));true),asserta(restos1(Rest)),
	(retract(states1(_));true),asserta(states1(States)),
	(retract(freeDrivers(_));true),asserta(freeDrivers(Use)),
	checkEach(Lista,NG,DP,P1,P2,P3,MG,Valor,Tempo, Sol,Escrita),
	show_allservices_drivers(Sol,Solucao),
	globalWarnings(Warn).

checkEach([],_,_,_,_,_,_,_,_,[],_).
checkEach([X|ListaEscalonamentos],NG,DP,P1,P2,P3,MG,Valor,Tempo, Solucao,Escrita):- 
	checkEach(ListaEscalonamentos,NG,DP,P1,P2,P3,MG,Valor,Tempo, Solucao1,Escrita),
	getK(X,Servico),
	getL(X,Esc),
	gera_com_parametros(Servico,Esc,NG,DP,P1,P2,P3,MG,Valor,Tempo,_,Escrita),
	(retract(flag_Servico(_));true),asserta(flag_Servico(0)),
	(retract(flag_Almoco(_));true),asserta(flag_Almoco(0)),
	(retract(flag_Jantar(_));true),asserta(flag_Jantar(0)),
	(retract(blocosInf(_));true),asserta(blocosInf([])),
	%caso(Solucao,Escolha),
	save(Escolha),
	inverte(Escolha,IEscolha),
	avalia(IEscolha,_),
	flag_Servico(Trabalho),
	fixProblemLunch(IEscolha,Fixed),
	(((Trabalho>0),!,(
	fixProblemService(Fixed,AlmostThere)
	));(passa(Fixed,AlmostThere))),
	fixProblemDinner(AlmostThere,Finnaly),
	inverte(Finnaly,TheEnd),
	emboxa((Servico,TheEnd),Done),
	append(Done, Solucao1, Solucao),
	globalWarnings(Global),
	warnings(Warn),
	append(Warn, Global, AllWarnings),
	(retract(globalWarnings(_));true),asserta(globalWarnings(AllWarnings)).
	%dinner(Dinner),
	%write('Dinner  '),write(Dinner),nl,
	%freeDrivers(Usados),write('Usados  '),write(Usados),nl,
	%states1(Estado),write('Estado  '),write(Estado),nl,restos1(Resto),write('Resto  '),write(Resto),nl,lunch(Lunch),write('Lunch  '),write(Lunch),
	%write('  Servico '),write(Servico),write(IEscolha),nl,write('  Servico '),write(Servico),write(Fixed),nl,
	%write('  Servico '),write(Servico),write(AlmostThere),nl,write('  Servico '),write(Servico),write(Finnaly),nl.	
	%AKI ENTRA A PARTE DA AVALIAÇÃO, PARTE 2 DESTE SPRINT .



gera_com_parametros(SV,Esc,NG,DP,P1,P2,P3,MG,Valor,Tempo, Solucao,Escrita):-
	(retract(warnings(_));true),asserta(warnings([])),
	(retract(blocosInf(_));true),asserta(blocosInf([])),
	(retract(flag_Servico(_));true),asserta(flag_Servico(0)),
	(retract(flag_Almoco(_));true),asserta(flag_Almoco(0)),
	(retract(flag_Jantar(_));true),asserta(flag_Jantar(0)),
	asserta(escreve(Escrita)),
    lista_motoristas_nworkblocks2(SV,Esc,Lista),
	/*dados de preferncias para gerações e criterios de paragem*/
	inicializa2(NG,DP,P1,P2,P3,MG,Valor,Tempo),
	/* vai gerar uma população usando a lista criada a cima para gerar individuos*/
	/*gera_heuristica_pop(HPop),*/
	% se o parametro for = 1, gera população recorrendo a heuristica personalizada
	/*(HPop =:= 1, gera_populacao_heuristica(Lista,Pop);
	HPop =\= 1, gera_populacao(Lista,Pop)),*/
	heuristica(SV,Esc,Heuristica),
	createList(SV,Heuristica,Res),
	inverte(Res,IRes),
	(retract(save(_));true),asserta(save(IRes)),
	gera_populacao(Lista,APop),
	emboxa(IRes,BRes),
	append(BRes, APop, Pop),
	(escreve(true),write('Pop=');true),(escreve(true),write(Pop),nl;true),
	/*vai avaliar a populacao*/
    avalia_populacao(Pop,PopAv),
	(escreve(true),write('PopAv=');true),(escreve(true),write(PopAv),nl;true),
	/* e ordenala pelo custo*/
	ordena_populacao(PopAv,PopOrd),
    /* vai dizer quantas novas gerações vamos querer*/
	geracoes(NG),
	gera_geracao([],0,NG,PopOrd,0),
	best(Solucao),
	(retract(escreve(_));true).

workblocks(SV,Num):-servicoViatura(SV,_,_,Lista), qtde(Lista,Num).
/* vai contar o numeros de elementos de uma lista*/
qtde([],0).
qtde([_|T],S):-qtde(T,G),S is 1+G.
/*vai criar uma lista com os possiveis combinações consoante o escalonamento
Ex: Escalonamento for [(276,2),(5188,3),(16690,2),(18107,1)] , Lista de retonro é 
[276,276,5188,5188,5188,16690,16690,18107]*/
createList(SV,Lista,Res):-
    workblocks(SV,Num),createList2(Num,Lista,Res).
/*vai verificar o numero de blocos existentes no escalonamento*/
conta([],0).
conta([H|L],Num):-
    conta(L,Num1),
    elemento(H,Valor),
    Num is Num1+Valor.
/* vai retornar o numero de blocos de um determinado motorista, e verifica se o motorista existe*/
elemento((_,Valor),Valor).
/* verifica se o numero de blocos de um servico de viatura é igual ao do escalonamento*/
verificaNBlocos(Lista,Num):-
    conta(Lista,Elem),
    Elem==Num.
/* cria a lista que se vai trabalhar a distribuiçao dos blocos, Exemplo:
[276,276,5188,5188,5188,16690,16690,18107]*/
createList2(Num,Lista,Res):-
	/* verifica se existe compatibilidade entre a quantidade de blocos no escalonamento e o 
	numero de blocos no servicoViatura*/
    verificaNBlocos(Lista,Num),
	/* cria a lista*/
    create(Lista,Res).
/* cria a lista*/
create([],[]).
create([X|L],Res):-
     create(L,Res1),
    addList(X,Res1,Res).
/* vai anexando outras listas*/
addList((SM,Num),Lista,Lista1):-
    newList(SM,Num,CreatedList),
    append(Lista, CreatedList, Lista1).
    
/* cria uma lista com o mesmo Num de caracteres igual a Valor*/
newList(_,0,[]).
newList(Valor,Num,[Valor|Lista]):-
    Num1 is Num-1,
    newList(Valor,Num1,Lista). 
/* retorna a lista dos motoristas usados no escalonamento*/
saveMoto([],[]).
saveMoto([(Mot,_)|L],[Mot|Lista]):-saveMoto(L,Lista).
/* vai trabalhar com o escalonamento*/



permutacoes(1,1):-!.
permutacoes(Valor,Resultado):-
	Valor1  is Valor-1,
	permutacoes(Valor1,Resultado1),
	Resultado is Valor * Resultado1.


	/* Gera uma população de individuos, vai criar tantos cromossomas consoante o tamanho da população
   O tamanho da população e mandado por parametro no inicializa
   O num tarefas ta descrito em cima
   A lista de tarefas esta na base de conhecimento*/
/* base de inicio do gerapopulacao*/

gera_populacao(Lista,Pop):-
	populacao(TamPop),
    servicoV(SV),
	workblocks(SV,NumT),
	TamPop1 is (TamPop-1),
	gera_populacao(TamPop1,Lista,NumT,Pop).

gera_populacao(0,_,_,[]):-!.
gera_populacao(TamPop,ListaBlocos,NumT,[Ind|Resto]):-
    /*realiza uma iteração*/
	TamPop1 is TamPop-1,
    /* vai começar com a lista da população a vazio e ir acrescentando*/
	gera_populacao(TamPop1,ListaBlocos,NumT,Resto),
    /* gera um cromossoma e adiciona a lista da populacao*/
	gera_individuo(ListaBlocos,NumT,Ind),
    /*se esta combinação ainda nao existir*/
	not(member(Ind,Resto)).

/* se o cromossoma ja existir na lista simplesmente volta a iterar e nao dimunui o tamPop, porque a lista da população nao cresceu*/
gera_populacao(TamPop,ListaBlocos,NumT,L):-
	gera_populacao(TamPop,ListaBlocos,NumT,L).


/* Gera população com heuristica inicial */

/* gera um individuo 
gera_individuo_melhor(ListaBlocos,_,Ind):-
	sort(0,@=<,ListaBlocos,Ind).

gera_populacao_heuristica(Lista,Pop):-
	populacao(TamPop),
    servicoV(SV),
	workblocks(SV,NumT),
	gera_populacao_heuristica(TamPop,Lista,NumT,Pop).

gera_populacao_heuristica(0,_,_,[]):-!.
gera_populacao_heuristica(TamPop,ListaBlocos,NumT,[Ind|Resto]):-
    /*realiza uma iteração*/
	TamPop1 is TamPop-1,
    /* vai começar com a lista da população a vazio e ir acrescentando*/
	gera_populacao_heuristica(TamPop1,ListaBlocos,NumT,Resto),
    /* gera um cromossoma e adiciona a lista da populacao*/
	(TamPop =:= 1, gera_individuo_melhor(ListaBlocos,NumT,Ind);
	TamPop =\= 1, gera_individuo(ListaBlocos,NumT,Ind)),
    /*se esta combinação ainda nao existir*/
	not(member(Ind,Resto)).

/* se o cromossoma ja existir na lista simplesmente volta a iterar e nao dimunui o tamPop, porque a lista da população nao cresceu*/
gera_populacao_heuristica(TamPop,ListaBlocos,NumT,L):-
	gera_populacao_heuristica(TamPop,ListaBlocos,NumT,L).


horas_segundos(Horas,Segundos):-
	Segundos is Horas*60*60. */

/* base do gera-individuo*/
gera_individuo([],0,[]):-!.
/* vai gerar um cromossoma aleatorio com as tarefas que existem*/
gera_individuo(ListaBlocos,NumT,[G|Resto]):-
	NumTemp is NumT + 1, % To use with random
	random(1,NumTemp,N),
	retira(N,ListaBlocos,G,NovaLista),
	NumT1 is NumT-1,
	gera_individuo(NovaLista,NumT1,Resto).

/* vai retirar um elemento a uma lista*/
retira(1,[G|Resto],G,Resto).
retira(N,[G1|Resto],G,[G1|Resto1]):-
	N1 is N-1,
	retira(N1,Resto,G,Resto1).
/* vai criar uma lista tipo [(StartTime,Bloco),...]*/
ordenaBlocos2([],[]).
ordenaBlocos2([X|L],[(StartTime,X)|Lista1]):-
	ordenaBlocos2(L,Lista1),
	bloco(X,StartTime,_,_,_).
/* faz o sort pelo startTime*/
ordenaBlocos(Lista,ListaFinal):-
	ordenaBlocos2(Lista,Lista1),
	sort(Lista1, Lista2),
	transform(Lista2, ListaFinal).
/* vai buscar o bloco apenas*/
getB((_,X),X).
/* transforma de (StartTime,Bloco) para Bloco apenas*/
transform([],[]).
transform([X|L],[Elem|L1]):-
	transform(L,L1),
	getB(X,Elem).

/*base do avalia populacao*/
avalia_populacao([],[]).
avalia_populacao([Ind|Resto],[Ind*V|Resto1]):-
	avalia(Ind,V),
    /*vai repetir para todos os cromossomas de uma população*/
	avalia_populacao(Resto,Resto1).

/* segundo predicado do avalia que vai lidar com 2 argumentos*/
avalia(Seq,Custo):-
	motoristas(LMoto),
	avaliaTempos(Seq,LMoto,Custo).
/* vai avaliar o cromossoma*/
avaliaTempos(_,[],0).
avaliaTempos(Seq,[X|Moto],Custo):-
	avaliaTempos(Seq,Moto,_),
	/* vai a sequencia buscar as posicoes onde trabalha o motorista*/
	getTempos(Seq,X,Lista,_),
	/*com essas posiçoes vao trabalhar as associaçoes com os blocos do servicoViatura, e retornar 
	os blocos correspondentes*/
	getAssocBlock(Lista, ListaBlocos),
	/*vai ordenar por startTime*/
	ordenaBlocos(ListaBlocos,NewListaBlocos),
	inverte(NewListaBlocos,ListaInvertida),
	/* vai analisar o tempo total de serviço que um motorisra vai ter, nao deve ser >28800
	caso tenha periodos maiores que 14400 sem fazer pausa de uma hora, aumenta-se o total time 
	com 28800 , dizendo que a solução não é viavel*/
	%analisaBlocos(ListaInvertida,_,FullTime,_),
	%(((FullTime>28800),!,(CustoB is (Custo1+40)));(CustoB is Custo1)),
	analisaSequencia(Lista,CustoS),
	analisaWorkTime(NewListaBlocos,X,CustoW),
	analisaAlmoco(ListaInvertida,CustoA),
	analisaJantar(ListaInvertida,CustoJ),
	Custo is (CustoS+CustoA+CustoJ+CustoW).
/* analisa o cromossoma a nivel de sequencia*/
analisaSequencia([],0).
analisaSequencia([X|L],Custo):-
  analisaSequencia(L,Custo1),
  getNext(L,X1),
  (((X1 is 0),!,(Custo is Custo1));(
	  Dif is X-X1,
	  (((Dif == 1 ),!,(Custo is Custo1));(Custo is (Custo1+30)))
  )).
/* para ir buscar o elemento a seguir*/
getNext([],0).
getNext([X|_],X).

analisaWorkTime([],_,0).
analisaWorkTime([X|ListaBlocos],Mot,Custo):-
	analisaWorkTime(ListaBlocos,Mot,Custo1),
	horariomotorista(Mot,StartTime,EndTime,_,_),
	bloco(X,StartB,EndB,_,_),
	((((StartB>StartTime),(EndB<EndTime)),!,(passa(Custo,Custo1)));(Custo is (Custo1+15),
	flag_Servico(Oco),(retract(flag_Servico(_));true),Oco1 is Oco+1, asserta(flag_Servico(Oco1)),
	blocosInf(BL),emboxa(X,BX),append(BL,BX,R),(retract(blocosInf(_));true),asserta(blocosInf(R))
	)).

getValores(S,Lista):- servicoViatura(S,_,_,Lista), write(Lista).

analisaAlmoco(L,Custo):-
	ultimoElemento(L,Ultimo),
	bloco(Ultimo,StartTime,_,_,_),
	hfAlmoco(HoraFinal),
	(((StartTime > HoraFinal),!, (Custo is 0));
	(analisaAlmoco2(L,Tempolivre,_), 
	((((Tempolivre>=3600);(StartTime>HoraFinal)),!,(Custo is 0));(Custo is 15),	(retract(flag_Almoco(_));true),asserta(flag_Almoco(1))
	))).

analisaAlmoco2([],0,_).
analisaAlmoco2([X],TempoLivre,Last):-
	analisaAlmoco2([],TempoLivre1,_),
	bloco(X,_,EndTime,_,_),
	TempoLivre is TempoLivre1,
	Last is EndTime.
analisaAlmoco2([X|L],TempoLivre,Last):-
	analisaAlmoco2(L,TempoLivre1, Last1),
	bloco(X,StartTime,EndTime,_,_),
	hiAlmoco(_),
	hfAlmoco(HFinal),
	Temp is StartTime-Last1,
	Last is EndTime,
	(((StartTime>HFinal),!,(
			(((Last1< HFinal),!,(TempoLivre is Temp));(TempoLivre is TempoLivre1))
		));(
		((((Temp>=3600),(EndTime<HFinal)),!,(TempoLivre is Temp));(
			TempoLivre is TempoLivre1
		))
	)).

analisaJantar(L,Custo):-
	primeiroElemento(L,Primeiro),
	bloco(Primeiro,_,EndTime,_,_),
	hiJantar(HoraInicial),
	hfJantar(HoraFinal),
	(((EndTime < HoraInicial),!, (Custo is 0));
	(analisaJantar2(L,Tempolivre1,_), 
	((((Tempolivre1>=3600);(EndTime<HoraFinal)),!,(Custo is 0));(Custo is 15),(retract(flag_Jantar(_));true),asserta(flag_Jantar(1))
	))).

analisaJantar2([],0,_).
analisaJantar2([X],TempoLivre,Last):-
	analisaJantar2([],TempoLivre1,_),
	bloco(X,_,EndTime,_,_),
	TempoLivre is TempoLivre1,
	Last is EndTime.
analisaJantar2([X|L],TempoLivre,Last):-
	analisaJantar2(L,TempoLivre1, Last1),
	bloco(X,StartTime,EndTime,_,_),
	hfAlmoco(_),
	hiJantar(Hinicial),
	hfJantar(HFinal),
	Temp is StartTime-Last1,
	Last is EndTime,
	(((StartTime>HFinal),!,(
			(((Last1< HFinal),!,(TempoLivre is Temp));(TempoLivre is TempoLivre1))
		));(
		((((Temp>=3600),(StartTime>Hinicial)),!,(TempoLivre is Temp));(
			TempoLivre is TempoLivre1
		))
	)).

primeiroElemento([X|_],X).

ultimoElemento([X],X).
ultimoElemento([_|L],U):-
	ultimoElemento(L,U).

/* verifica se respeita a regra do tempo maximo por turno de 8h, ou maximo de 4 horas sem descanso de 1 hora,
para obdecer o resultado tem de ser inferior a 28800*/
analisaBlocos([],0,0,_).
analisaBlocos([X],SomaTime,FullTime,Last):-
	analisaBlocos([],SomaTime1,FullTime1,Last),
	bloco(X,StartTime,Last,_,_),
	Time is Last-StartTime,
	SomaTime is SomaTime1+Time,
	(((SomaTime>14400),!,(FullTime is FullTime1+Time+28800));
	(FullTime is FullTime1 +Time)).
analisaBlocos([X|ListaBlocos],SomaTime,FullTime,End):-
	analisaBlocos(ListaBlocos,SomaTime1,FullTime1,Last),
	bloco(X,StartTime,EndTime,_,_),
	Dif is (StartTime-Last),
	(((Dif >= 3600),!,(S is 0));(S is SomaTime1)),
	Time is EndTime-StartTime,
	SomaTime is S+Time,
	(((SomaTime>14400),!,(FullTime is (FullTime1+Time+28800)));
	(FullTime is (FullTime1+Time))),
	End is Last.

/**/
getAssocBlock([],[]).
/*Vai receber uma lista de posicoes e retornar a lista de blocos referentes a essas posicoes , na lista de servicos viatura*/
getAssocBlock([X|Lista],[Elem|ListaTempos]):-
	getAssocBlock(Lista,ListaTempos),
	/* vai buscar o servico viatura a usar*/
	servicoV(SV),
	/* com esse servico vai buscar a lista de blocos*/
	servicoViatura(SV,_,_,ListaBlocos),
	X1 is X-1,
	/* e procurar nessa lista os elementos passados na Lista*/
	encontraeElem(X1,ListaBlocos,Elem).
/* vai percorrer a sequencia e guardar as posições em que o motorista X , vai trabalhar, para depois ir buscar os 
blocos referentes na lista do servico de viatura*/
getTempos([],_,[],0).
getTempos([X|Seq],X,[Cont|Lista],Cont):-
		getTempos(Seq,X,Lista,Cont1),
		Cont is Cont1+1.
getTempos([_|Seq],X,Lista,Cont):-
		getTempos(Seq,X,Lista,Cont1),
		Cont is Cont1+1.


concatenar([], L, L).
concatenar([H|T], L, [H|D]) :- concatenar(T, L, D).
inverte([], []).
inverte([H|T], L) :- inverte(T, X), concatenar(X, [H], L).

encontraeElem(0, [H|_], H):- !.
encontraeElem(I, [_|T], E):- X is I - 1, encontraeElem(X, T, E).


/* base do avalia*/
avalia([],_,0).

ordena_populacao(PopAv,PopAvOrd):-
	bsort(PopAv,PopAvOrd).

/*predicado base do bsort*/
bsort([X],[X]):-!.
/*predicado usado no sort da lista dos custos dos cromossomas*/
bsort([X|Xs],Ys):-
	bsort(Xs,Zs),
	btroca([X|Zs],Ys).


btroca([X],[X]):-!.
btroca([X*VX,Y*VY|L1],[Y*VY|L2]):-
	VX>VY,!,
	btroca([X*VX|L1],L2).
btroca([X|L1],[X|L2]):-btroca(L1,L2).

/*mete o X dentro de uma lista*/
emboxa(X,[X]):-!.
/* vai buscar o peso de um item*/
peso(_*Peso,Peso).
caso(Lista*_,Lista).
/*retorna o topo de uma lista*/
topo([X|_],X).
/*condição de paragem de atingir a ultima geração*/
gera_geracao(_,G,G,Pop,_):-!,
	(escreve(true),write('Geracao '), write(G), write(':'), nl, write(Pop), nl;true),
		(retract(best(_));true), primeiroElemento(Pop,Elem), asserta(best(Elem)).
/*condicao de paragem tendo em conta a quantidade de turnos até á exaustao de soluções*/
gera_geracao(_,_,_,Pop,MG):-geracaoEstaganar(MG), (retract(best(_));true), primeiroElemento(Pop,Elem), asserta(best(Elem)),
						(escreve(true),write('Tendo em que nao existem modificacoes na melhor solucao a ')
						,write(MG), write('turnos, chegamos a conclusao que obtemos as melhores solucoes');true),!.
/*condição de paragem tendo em conta o valor atingido*/
/*gera_geracao(_,N,_,Pop,_):-valorObjetivo(Valor),primeiroElemento(Pop,X),peso(X,Peso),Peso=<Valor,
			(escreve(true),write('Geracao '), write(N), write(':'), nl, write(Pop), nl;true),
			(retract(best(_));true), primeiroElemento(Pop,Elem), asserta(best(Elem)),
			(escreve(true),write('Na '),write(N),write("º geracao foi atingido um valor igual ou inferior ao pretendido : "),write(X);true),!.*/
/*condição de paragem tendo em conta o tempo de processamento*/
%gera_geracao(_,_,_,Pop,_):-get_time(Tf),
						   /* vai buscar o tempo atual e final*/
						   %tempoInicial(Ti),
						   /* vai buscar o tempo de processamento permitido*/
						   %tempoProcessamento(Minutos), 
						   /*converte em segundos*/
						   %Segundos is Minutos*60,
						   /* calcula o tempo decorrido desde o inicio*/ 
						   %Dif is Tf-Ti,
						   /* verifica se o programa ja corre á mais tempo que aquele permitido*/
						   %Dif>Segundos,
						   /* se sim informa o utilizador*/
						   %(escreve(true),write('atingiu o tempo limite de processamento passado por parametro');true),
						   	%(retract(best(_));true), primeiroElemento(Pop,Elem), asserta(best(Elem)),!.
/* vai gerar e imprimir as proximas gerações*/
gera_geracao(Best,N,G,Pop,IterWithoutChanges):-
	(escreve(true),write('Geracao '), write(N), write(':'), nl, write(Pop), nl;true),
	/* para garantir que nao fazemos combinações certas entre apenas o 1 e o 2 elemento
	ou entre o 3º e o 4º, ou 5º e 6º,..., fazemos permutações á ordem como os cromossomas
	aparecem*/
	random_permutation(Pop,Permutation),
	/* faz os cruzamentos*/
	cruzamento(Permutation,NPop),
	/* faz as mutações*/
	mutacao(NPop,NPop1),
	/* vai verificar a quantidade de items a preservar desta geração atraves da taxa de preservação*/
	quantidade_preservar(Numeros),
	/* vai avaliar a população*/
	avalia_populacao(NPop1,NPopAv),
	/*vai buscar o melhor membro da lista atuale guarda-o , pk ao fazer o retifica corremos o risco de o perder*/
	max_member(BestActual, NPopAv),
	/*depois de guardado retira-o da lista*/
	remove(BestActual,NPopAv,NPopAv2),
	/* ao retificarmos antes da ordenação, garantimos que para alem dos elementos a preservar , os restantes elementos são aleatorios,
	tornando assim ,num metodo nao elitista. Para ser um metodo elitista, ordenavamos e so depois e que acrescentavamos os elementos a 
	preservar*/
	((N\==0,!,(addToBest(BestActual,Best,Best1),retifica(Best1,NPopAv2,NPopAvRet)));
			(header(Pop,MelhoresG0,Numeros),addToBest(BestActual,MelhoresG0,Melhores),retifica(Melhores,NPopAv2,NPopAvRet))),
	/*ordena a população pelo peso*/
	ordena_populacao(NPopAvRet,NPopOrd),
	/* vai procurar os valores a preservar para a proxima geração tendo em atenção a percentagem de preservação*/
	header(NPopOrd,Header,Numeros),
	/* passa para a geração seguinte*/
	N1 is N+1,
	/* se a melhor solucao entre a geração anterior e esta permanecer o mesmo , incrementa o iterador*/
	((bestSolutionTheSame(Pop,NPopOrd),!,IterWithoutChanges1 is IterWithoutChanges+1);IterWithoutChanges1 is 0),
	/* itera*/
	gera_geracao(Header,N1,G,NPopOrd,IterWithoutChanges1).
/*adiciona o melhor da geração aos melhores da geração anterior de maneira a preservarmos pelo menos o melhor da presente
e os melhores ja mandados anteriormente*/
addToBest(Best,Lista,Lista1):-
	emboxa(Best,NewBest),
	append(Lista,NewBest,Lista1).
/* vai verificar quantos numeros a preservar da geração anterior , tendo em conta o tamanho da população e a taxa de preservação*/
quantidade_preservar(Quantidade):-
	populacao(Num),
	perc_preservacao(Perc),
	Numeros is round(Num*Perc),
	((Numeros<1,!,Quantidade is 1);(Quantidade is Numeros)).
/* vai verificar se os topos são os mesmos, utilizado para verificar se ha exaustão de soluções*/
bestSolutionTheSame([X|_],[X|_]).


/*best_and_rest([Best|L],NPopAv,List):-
	tarefas(Num),
	((not(member(Best,NPopAv)),!,
	best_and_rest1(Best,NPopAv,List,Num));
	igual(NPopAv,List)).

best_and_rest1(Best,NPopAv,List,Num):-
	Num1 is Num,
	best_and_rest2(Best,NPopAv,List,Num1).

best_and_rest2(Best,[X|L],[X|List],Num):-
	Num1 is Num-1,
	best_and_rest2(Best,L,List,Num1).

best_and_rest2(Best,L,[Best],0):-!.
*/

/*vai copiar uma lista para outra*/
copy([X],[X]):-!.
copy([X|Lista],[X|Lista1]):-copy(Lista,Lista1).
/* vai construir o header tendo em atenção a lista e os valores a preservar*/
header(_,[],0):-!.
header([T|Lista],[T|Lista1],Num):-
	Num1 is Num-1,
	header(Lista,Lista1,Num1).
/*Header sao os bests da ultima geração
  Lista é a nova lista com a nova geração
  ListaFinal é a lista que devolvemos , junto com os bests da antiga geração e os restantes da nova, tendo esta lista um tamanho igual a "NG"*/
retifica(Header,Lista,ListaFinal):-
	/*vai verificar quais os valores que vai acerescentar, apenas acrescenta valores que ja nao estejam na lista */
	append(Header,Lista,LF),
	/* vai buscar o tamanho da população geral dada como parametro no inicio*/
	populacao(NP),
	/*vai reduzir a lista da nova geração para uma lista com apenas "RestantesMenbros", de maneira a que "RestantesMembros" + "Numeros" seja igual a "NP"*/
	cortaLista(LF,ListaFinal,NP).
/* vai verificar quais os items dos "Best"´s da geração anterior que nao se encontram nesta geraçõa*/
valoresAcrescentar([],_,[]):-!.
valoresAcrescentar([X|Header],Lista,[X|NewLista]):-
	not(member(X,Lista)),valoresAcrescentar(Header,Lista,NewLista).
valoresAcrescentar([_|Header],Lista,NewLista):-
	valoresAcrescentar(Header,Lista,NewLista).
/* vai verificar o tamanho de uma lista*/
tamanhoLista([],0).
tamanhoLista([_|T],S):-tamanhoLista(T,G),S is 1+G.
/*vai cortar uma Lista para o Num de algarismos dados*/
cortaLista([X|Lista],[X|Lista1],Num):-
	Num1 is Num-1,
	cortaLista(Lista,Lista1,Num1).
cortaLista(_,[],0):-!.
/* pk duas bases do cruzamento??????????????????????????*/
cruzamento([],[]).
cruzamento([Ind*_],[Ind]).
cruzamento([Ind1*_,Ind2*_|Resto],[NInd1,NInd2|Resto1]):-
	/* gera numeros aleatorios*/
    gerar_pontos_cruzamento(P1,P2),
    /* vai ler a probabilidade de cruzamento e gerar um random para o Pc*/
	prob_cruzamento(Pcruz),random(0.0,1.0,Pc),
    /* vai verificar qual a probablidade de cruzamento*/
	((Pc =< Pcruz,!,
      cruzar(Ind1,Ind2,P1,P2,NInd1),
	  cruzar(Ind2,Ind1,P1,P2,NInd2)
	);
	(NInd1=Ind1,NInd2=Ind2)),
	cruzamento(Resto,Resto1).

% Remove um elemento da lista a partir do índice
remove(X, [X|T], T).
remove(X, [H|T], [H|T1]):- remove(X,T,T1).

/*predicado para gerar pontos de cruzamento a ser usados no cruzar, para trabalharmos as mutações*/
gerar_pontos_cruzamento(P1,P2):-
	gerar_pontos_cruzamento1(P1,P2).
/*tendo em conta o numero de tarefas, vai produzir numeros aleatorios para retornar*/
gerar_pontos_cruzamento1(P1,P2):-
	/*tamanho da base de conhecimento de tarefas*/
	servicoV(SV),
	workblocks(SV,NumT),
    /*acresenta 1 a esse tamanho*/
	NTemp is NumT+1,
    /*gera dois randoms que vai guardar em P11 E P21*/
	random(1,NTemp,P11),
	random(1,NTemp,P21),
    /* vai verificar se são diferentes e se forem prossegue*/
	P11\==P21,!,
    /* guarda o menor valor em P1 e o maior em P2*/
	((P11<P21,!,P1=P11,P2=P21);(P1=P21,P2=P11)).
/*se os pontos forem iguais volta a tentar fazer atribuições random*/
gerar_pontos_cruzamento1(P1,P2):-
	gerar_pontos_cruzamento1(P1,P2).
/* p1 e p2 vão ser numeros de 1 a 5 neste caso, a sublista vai preservar os valores entre p1 e p2
Exemplo Ind1 = t1 , t2 , t3 , t4 , t5 
        Ind2 = t2 , t3 , t1, t5, t4
        p1=2 e p2=4
    sub1 = h , t2 , t3 , t4 , h 
    a seguir vai fazer n rotaçoes para a direita , em que n=5-4 neste caso , ou seja 1 deslocação
    ficando Ind21  = t4 , t2 , t3 , t1 , t5  , os elementos todos a h do lado esquerdo 
    depois elimina-se  e fica 
    Sub 2 = [t1, t5] 
    e faz.se a inserção ficando com 
    NInd1 = [t5, h, t2, t3, t4, t1, h]
    eliminamos os h e ficamos com 
    NInd11 = [t5, t2, t3, t4, t1].

Outro Exemplo Ind1 = t1, t2, t3 , t4, t5
              Ind2 = t2 , t3 , t1, t5, t4
              p1=2 e p2=3
              sub1 = h , t2 , t3 , h , h 
              Ind21 = [t5, t4, t2, t3, t1].
              Sub 2 = = [t5, t4 , t1]
              NInd1 = [t1, h, t2, t3, t5, t4, h, h].
              NInd11 = [t1, t2, t3, t5, t4]. 

Basicamente vai preservar os valores entre P1 E P2 e fazer combinações com os restantes
    */
cruzarOld(Ind1,_,P1,P2,NInd11):-
	/* cria a lista com os h´s*/
	sublista(Ind1,P1,P2,Sub1),
	/* vai buscar os motoristas usados*/
	motoristas(Mot),
	/* apresenta a lista sub1 sem h´s*/
	listSemH(Sub1,NewList),
	/* agrupa por motorista para saber quantos faltam usar*/
	agrupa(NewList,Mot,AgrList),
	/* retorna a lista das possibilidades tipo [(2,3),(3,1)]*/
	possibilidades(AgrList, Possibilidades),
	/* cria uma lista com essas possibilidades tipo [2,2,2,1]*/
	create(Possibilidades,ListaPossibilidades),
	/* calcula o tamanho dessa lista*/
	qtde(ListaPossibilidades,Tamanho),
	/* gera um individuo novo com base nessa lista*/
	gera_individuo(ListaPossibilidades,Tamanho,Solucao),
	/* substitui os h´s na lista inicial por valores da nova lista de possibilidades*/
	replaceH(Sub1,Solucao,NInd11).

cruzar2(Ind1,Ind2,P1,P2,NInd11):-
	/* cria a lista com os h´s*/
	sublista(Ind1,P1,P2,Sub1),
	/* vai buscar os motoristas usados*/
	motoristas(Mot),
	/* apresenta a lista sub1 sem h´s
	NewList = 276,16690,16690,18107,18107*/
	listSemH(Sub1,NewList),
	/*Calcular o tamanho do Ind2*/
	qtde(Ind2,Tam2),
	/* Calcular quantos elementos restam desde o P2 ate ao fim de Ind1*/
	Dif is Tam2-P2,
	/* Vai buscar os elementos da segunda parte k fica de fora*/
	cortaListaFim(Ind1,Dif,Canto,_),
	/* vai juntar com Ind2
	{18107,18107,18107,18107,276, 276,5188,5188,5188,18107, 16690,16690,18107,18107,18107,18107,18107*/
	append(Canto,Ind2, NovaLista),
	/* vai ajeitar o tamanho da nova lista
	TreatedList = 18107,18107,18107,18107,276, 276,5188,5188,5188,18107, 16690,16690,18107*/
	cortaLista(NovaLista, TreatedList,Tam2),
	/*Append = 18107,18107,18107,18107*/
	cortaLista(TreatedList,Append,Dif),
	/*PartialList = 276,16690,16690,18107,18107,18107,18107,18107,18107*/
	append(NewList, Append, PartialList),
	/**/
	Rest is Tam2-Dif,
	/*Lista2 = 276, 276,5188,5188,5188,18107, 16690,16690,18107*/
	cortaListaFim(TreatedList,Rest,Lista2,_),
	/* agrupa por motorista para saber quantos faltam usar*/
	agrupa(PartialList,Mot,AgrList),
	/* retorna a lista das possibilidades tipo [(2,3),(3,1)]*/
	possibilidades(AgrList, Possibilidades),
	/* cria uma lista com essas possibilidades tipo [2,2,2,1]*/
	create(Possibilidades,ListaPossibilidades),
	/* calcula o tamanho dessa lista*/
	preencheResto(Lista2,ListaResto,_,ListaPossibilidades),
	/**/
	append(ListaResto, PartialList, NInd11).

/*
	Lista2: menbros do 2º menbro para acrescentar caso existam em ListaPossibilidades
	PartialList: a lista á qual se vai acrescentar
	ListaPossibilidades: os menbros que ainda pode usar
*/
preencheResto([],[],Same,Same):-!.
preencheResto([X|Lista2],List,ListaPossibilidades,Same):-
	preencheResto(Lista2,List2,ListaPossibilidades2,Same),
	existe(X,ListaPossibilidades2,Existe),
	(((Existe>0),!,(
		emboxa(X,XinList),
		append(XinList,List2, List),
		removeOne(X,ListaPossibilidades2,ListaPossibilidades)		
	));(copia(List2,List),copia(ListaPossibilidades2,ListaPossibilidades))).

copia([],[]).
copia([X|L],[X|L1]):-
	copia(L,L1).


/* cruzar tp11*/
cruzar(Ind1,Ind2,P1,P2,NInd11):-

	/*[18107,5188,16690,276,5188,5188,16690,276]*/
	/*[18107,5188,16690,16690,5188,276,276,5188]*/
	/* P1=3 P2=5*/
	/*Sub1= {H,H, 16690,276,5188,H,H,H}*/
	sublista(Ind1,P1,P2,Sub1),
	/* vai buscar os motoristas usados*/
	motoristas(Mot),  						/*  [276, 5188, 16690, 18107]*/
	listSemH(Sub1,NewList),					/*newlist = {16690,276,5188}*/
	/* agrupa por motorista para saber quantos faltam usar*/
	/* AgrList = [[276, 1], [5188, 1], [16690, 1], [18107, 0]]*/
	agrupa(NewList,Mot,AgrList),
	possibilidades(AgrList, Possibilidades),  /* Possibilidades  [(276, 1),(5188, 2),(16690, 1),(18107, 1)]*/
    /*ListaPossibilidades	= [18107, 16690, 5188, 5188, 276]*/
	create(Possibilidades,ListaPossibilidades),
	/* calcula o tamanho dessa lista*/
	qtde(Ind1,TamLst),
    /* tamanho lista apos 2 corte*/
	NRot is TamLst-P2,
	/* lista 2 apos 2 corte e refaz a lista 2*/
	/*Sub1= {H,H, 16690,276,5188,H,H,H}*/
    /*Ind21 -[5188,16690,276,18107,5188,16690,276,5188  rodou 5188,16690,276]*/
	rotate_right(Ind2,NRot,Ind21),
	rotate_right(Sub1,NRot,Sub12),
	/* sublist Sub1 até p2*/
	%sublista(Sub1,1,P2,NSub1P2),
	/* elementos neste é p nrot vai criar uma lista desde esteja dentro ~
	 da lista de possibilidades*/
    %write(NSub1P2),
	/*Sub1= {H,H, 16690,276,5188,H,H,H}*/
	/*Ind1=[18107,5188,16690,276,5188,5188,16690,276]*/
	/*Ind2=[18107,5188,16690,16690,5188,276,276,5188]*/
	/* Ind21 = 276,276,5188,18107,5188,16690,16690,5188*/
    /*listaPossibilidades= (18107,5188,276,276,5188)*/
	/* NInd = 276, 276, 5188, 18107,5188, 16690, 276, 5188*/
	replaceHs(Sub12,Ind21,ListaPossibilidades,NInd1),
	%write(NInd1),
	rotate_right(NInd1,P2,NInd11).
	%write(NInd11),nl.

replaceHs([],_,_,_):-!.
/* vai substituir os h´s pelos elementos de outra lista*/
replaceHs([h|HList],[X|HInd2],LP,[X|Ideal]):-
		member(X,LP),
		removeOne(X,LP,NLP),
		replaceHs(HList,HInd2,NLP,Ideal).
replaceHs([h|HList],[X|HInd2],LP,Ideal):-
			not(member(X,LP)),
			replaceHs([h|HList],HInd2,LP,Ideal).
replaceHs([X|HList],HInd2,LP,[X|Ideal]):-
		replaceHs(HList,HInd2,LP,Ideal).

/* fim cruzar tp11*/

removeOne(_,[],[]).
removeOne(X,[X|L],L2):-
	removeOne('9999',L,L2).
removeOne(X,[Y|L],[Y|L2]):-
	removeOne(X,L,L2).

existe(_,[],0).
existe(Num,[Num|_],Existe):-
	Existe is 1.
existe(Num,[_|List],Existe):-
	existe(Num,List,Existe1),
	Existe is Existe1.

cortaListaFim([],_,[],0).
cortaListaFim([X|L],Valor,L2,Num):-
    cortaListaFim(L,Valor,L3,Num1),
    (((Num1<Valor),!,(
    Num is Num1+1,
	emboxa(X,BX),
	append(BX,L3,L2)));(
		passa(L3,L2),passa(Num,Num1)
	)).


replaceH([],_,[]).
/* vai substituir os h´s pelos elementos de outra lista*/
replaceH([h|HList],[X|Poss],[X|Ideal]):-
	replaceH(HList,Poss,Ideal).

replaceH([X|HList],Poss,[X|Ideal]):-
	replaceH(HList,Poss,Ideal).

/* vai agrupar os motoristas por ocorrencias*/
agrupa(_,[],[]).
agrupa(Lista,[X|Mot],[Elem|NewList]):-
 	agrupa(Lista,Mot,NewList),
	agrupa2(X,Lista,Elem).
agrupa2(X,Lista,Elem):-
	contaOcorrencias(X,Lista,Elem).
/*vai contar as ocorrencias de um determinado motorista*/
contaOcorrencias(X,Lista,Elem):-
	contaOcorrencias2(X,Lista,Resultado),
	boxa2(X,Resultado,Elem).
/* mete o conteudo numa lista*/
boxa2(X,L,[X,L]).

contaOcorrencias2(_,[],0).
contaOcorrencias2(X,[X|Lista],Conta):-
	contaOcorrencias2(X,Lista,Conta1),
	Conta is Conta1+1.

contaOcorrencias2(X,[_|Lista],Conta):-
	contaOcorrencias2(X,Lista,Conta1),
	Conta is Conta1.
/* retorna as possibilidades para adicionar*/
possibilidades([],[]).
possibilidades([X|Lista],[(Mot,Elem)|Possibilidades]):-
	possibilidades(Lista,Possibilidades),
	motor(X,Mot),
	newPoss(X,Elem).

motor([Mot,_],Mot).
/* de acordo com as que ja existem na lista sub1 diz quantas podemos ainda adicionar*/
newPoss([Mot,Ocor],El):-
	valorEsc(Mot,Elem),
	El is Elem-Ocor.

/* retorna o valor referente aquele motorista na escala*/
valorEsc(Mot,Elem):-
	escala(Esc),
	valorEsc2(Mot,Esc,Elem).

valorEsc2(_,[],_).
valorEsc2(Mot,[(Mot,Valor)|_],Valor).
valorEsc2(Mot,[(_,_)|L],Valor):-
	valorEsc2(Mot,L,Valor).
/* retira os h´s a lista*/
listSemH([],[]).
listSemH([h|Lista],NewLista):-listSemH(Lista,NewLista).
listSemH([X|Lista],[X|NewLista]):-listSemH(Lista,NewLista).

/*preenche com h´s*/
preencheh([],[]).
preencheh([_|R1],[h|R2]):-
	preencheh(R1,R2).
/*pk esta verificação se é menor se o predicado gerar pontos de cruzamento ja nos garante isso???????????????????????? e ainda por cima a seguir faz o na mesma, mesmo 
que não verifique a condição???*/
sublista(L1,I1,I2,L):-
	I1 < I2,!,
	sublista1(L1,I1,I2,L).
/*predicado da sublista*/
sublista(L1,I1,I2,L):-
	sublista1(L1,I2,I1,L).
/**devolve os ultimos P2-P1 elementos da lista*/
sublista1([X|R1],1,1,[X|H]):-!,
	preencheh(R1,H).
/*predicado da sublista*/
sublista1([X|R1],1,N2,[X|R2]):-!,
	N3 is N2 - 1,
	sublista1(R1,1,N3,R2).
/*predicado da sublista*/
sublista1([_|R1],N1,N2,[h|R2]):-
	N3 is N1 - 1,
	N4 is N2 - 1,
	sublista1(R1,N3,N4,R2).

/*faz k rotaçoes a uma lisra L e guarda o resultado em L1*/
rotate_right(L,K,L1):-
	servicoV(SV),
	workblocks(SV,N),
	T is N - K,
	rr(T,L,L1).
/*predicado base do rr*/
rr(0,L,L):-!.
/*predicado auxiliar do rotate right*/
rr(N,[X|R],R2):-
	N1 is N - 1,
	append(R,[X],R1),
	rr(N1,R1,R2).
/*predicado base do elimina*/
elimina([],_,[]):-!.
/* predicado do elimina*/
elimina([X|R1],L,[X|R2]):-
	not(member(X,L)),!,
	elimina(R1,L,R2).
/* predicado do elimina*/
elimina([_|R1],L,R2):-
	elimina(R1,L,R2).
/* base do insere*/
insere([],L,_,L):-!.
/* predicado do insere*/
insere([X|R],L,N,L2):-
	servicoV(SV),
	workblocks(SV,T),
	((N>T,!,N1 is N mod T);N1 = N),
	insere1(X,N1,L,L1),
	N2 is N + 1,
	insere(R,L1,N2,L2).
/* predicado auxiliar do insere*/
insere1(X,1,L,[X|L]):-!.
insere1(X,N,[Y|L],[Y|L1]):-
	N1 is N-1,
	insere1(X,N1,L,L1).
/*predicado base do elimina h*/
eliminah([],[]).
/* predicado do elimina h*/
eliminah([h|R1],R2):-!,
	eliminah(R1,R2).
/* predicado do elimina h*/
eliminah([X|R1],[X|R2]):-
	eliminah(R1,R2).
/*predicado da mutação*/
mutacao([],[]).
/*predicado da mutação*/
mutacao([Ind|Rest],[NInd|Rest1]):-
    /* vai buscar a probabilidade de mutação*/
	prob_mutacao(Pmut),
	random(0.0,1.0,Pm),
    /* vai verificar se vai usar a mutação ou não , consoante se metemos uma probabilidade ou simplesmente zero*/
	((Pm < Pmut,!,mutacao1(Ind,NInd));NInd = Ind),
	mutacao(Rest,Rest1).
/* a mutação vai fazer o contrario do cruzamento, o p1 e o p2 vão definir o intervalo a mudar , ou seja,
se eu tiver [ t1, t2, t3, t4, t5] e p1=2 p2=3
as posicões que vao mudar são a segunda e a terceira , ou seja o t2 e t3*/
mutacao1(Ind,NInd):-
    /* vai gerar pontos de cruzamento a ser usados*/
	gerar_pontos_cruzamento(P1,P2),
	mutacao22(Ind,P1,P2,NInd).
/* vai trocar a posicao p1 com a posicao p2 ou seja 
se eu tiver [ t1, t2, t3, t4, t5] e p1=1 p2=3
ficamos com [ t3, t2, t1, t4, t5]*/
mutacao22([G1|Ind],1,P2,[G2|NInd]):-
	!, P21 is P2-1,
	mutacao23(G1,P21,Ind,G2,NInd).
mutacao22([G|Ind],P1,P2,[G|NInd]):-
	P11 is P1-1, P21 is P2-1,
	mutacao22(Ind,P11,P21,NInd).

mutacao23(G1,1,[G2|Ind],G2,[G1|Ind]):-!.
mutacao23(G1,P,[G|Ind],G2,[G|NInd]):-
	P1 is P-1,
	mutacao23(G1,P1,Ind,G2,NInd).
/* apaga todos os elementos X da lista*/
apagaTodos(_,[],[]).
apagaTodos(X,[X|L],NL):-
	apagaTodos(X,L,NL).
apagaTodos(X,[Y|L],[Y|NL]):-
	apagaTodos(X,L,NL).
