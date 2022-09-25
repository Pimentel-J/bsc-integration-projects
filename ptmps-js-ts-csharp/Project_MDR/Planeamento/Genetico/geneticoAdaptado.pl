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
:-dynamic escala/1.

/* servicoViatura(IdServicoViatura,IdViatura,ListaBlocos)*/

servicoMotorista(276,5,[]).
servicoMotorista(5188,3,[]).
servicoMotorista(16690,2,[]).
servicoMotorista(18107,2,[]).

bloco(12,[459],34080,37620).
bloco(211,[31,63],37620,41220).
bloco(212,[33,65],41220,44820).
bloco(213,[35,67],44820,48420).
bloco(214,[37,69],48420,52020).
bloco(215,[39,71],52020,55620).
bloco(216,[41,73],55620,59220).
bloco(217,[43,75],59220,62820).
bloco(218,[45,77],62820,66420).
bloco(219,[48,82],66420,70020).
bloco(220,[52,86],70020,73620).
bloco(221,[56,432],73620,88000).
bloco(222,[460],77220,77340).


servicoViatura(1,2,[12,221,211,212,213,214,215,216]).
workblocks(SV,Num):-servicoViatura(SV,_,Lista), qtde(Lista,Num).
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
elemento((SM,Valor),Valor):-servicoMotorista(SM,_,_).
/* verifica se o numero de blocos de um servico de viatura é igual ao do escalonamento*/
verifica(Lista,Num):-
    conta(Lista,Elem),
    Elem==Num.
/* cria a lista que se vai trabalhar a distribuiçao dos blocos, Exemplo:
[276,276,5188,5188,5188,16690,16690,18107]*/
createList2(Num,Lista,Res):-
	/* verifica se existe compatibilidade entre a quantidade de blocos no escalonamento e o 
	numero de blocos no servicoViatura*/
    verifica(Lista,Num),
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
lista_motoristas_nworkblocks(Lista):-
	/*pede o servico de viatura*/ 
    write('insira o servico de viatura'),nl,read(SV),
	/* verifica se  a viatura existe*/
    (((servicoViatura(SV,_,_),!,
	/* pede o escalonamento*/
    (write('insira o escalonamento?'),nl,
	/* persiste o servico de viatura*/
	(retract(servicoV(_));true),asserta(servicoV(SV)),
	/* le o escalonamento e cria a lista a permutar*/
    read(Esc),createList(SV,Esc,Lista),
	(retract(escala(_));true),asserta(escala(Esc)),
	/* vai descobrir os motorisras que queremos escalonar*/
	saveMoto(Esc,ListaMoto),
	/* vai persistir essa lista de motoristas*/
	((retract(motoristas(_));true),asserta(motoristas(ListaMoto)))
	/* caso o servico de viatura pedido acima nao exista*/
	));(write('Numero de servico de viatura invalido')))).

inicializa:- /* vai ler o numero de geracoes que vai trabalhar*/
	/* vai ler o numero de geracoes que vai trabalhar*/
	write('Numero de novas Geracoes: '),read(NG),
    (retract(geracoes(_));true), ((NG>0,!,asserta(geracoes(NG)));
	/*não foi definido um limite para as gerações, nao sendo entao criterio de paragem*/
	(write('Limite de Geracoes nao estabelecido'),nl,asserta(geracoes(99999999)))),
	/*vai ler a dimensao da populacao*/
	write('Dimensao da Populacao: '),read(DP),
	(retract(populacao(_));true), servicoV(SV),workblocks(SV,Num),permutacoes(Num,Resultado),
	((DP<Resultado,!,asserta(populacao(DP)));
	/* valor dado foi superior as possiveis combinaçoes*/
	(write('Dimensão da população superior as combinações possiveis, alterado para '),
	write(Num),nl,asserta(populacao(Num)))),
	/*probabilidade para ocorrer cruzamento*/
	write('Probabilidade de Cruzamento (%):'), read(P1),
	PC is P1/100, 
	(retract(prob_cruzamento(_));true), 	asserta(prob_cruzamento(PC)),
	/*Probabilidade para ocorrer mutacao*/
	write('Probabilidade de Mutacao (%):'), read(P2),
	PM is P2/100, 
	(retract(prob_mutacao(_));true), asserta(prob_mutacao(PM)),
	/*percentagem de preservação das melhores solucoes para a geração seguinte*/
	write('Percentagem de preservação entre gerações (%):'), read(P3),
	((P3<50,!,PP is P3/100);(write('Valor de preservação demasiado elevado para'),
	/*temos de garantir que nao e muito elevado, para existir evolucao da populacao*/
	write(' garantir evolução nas gerações. Valor retificado para 50%'),nl,PP is 50/100)), 
	(retract(perc_preservacao(_));true), 	asserta(perc_preservacao(PP)),
	/*vai ler o numero permitido de geracoes ate estagnar*/
	write('Maximo de Gerações pra provar estagnação de solução:'), read(MG), 
	(retract(geracaoEstaganar(_));true), ((MG>0,!,asserta(geracaoEstaganar(MG)));
	/*nao foi definido um limite para geracoes ate estagnar, nao sendo criterio de paragem*/
	(write('Geracoes ate estagnacao nao estabelecido'),nl,asserta(geracaoEstaganar(9999999)))),
	/*vai ler o valor a tentar atingir*/
	write('Valor a tentar atingir:'), read(Valor), 
	(retract(valorObjetivo(_));true), 	asserta(valorObjetivo(Valor)),
	/*vai ler o tempo de processamento permitido ate parar*/
	write('Tempo de processamento em minutos:'), read(Tempo),
	(retract(tempoProcessamento(_));true), ((Tempo>0,!,asserta(tempoProcessamento(Tempo)));
	/*nao foi definido um limite para o tempo de processamento, nao sendo criterio de paragem*/
	(write('Limite de tempo de processamento nao estabelecido'),nl,asserta(tempoProcessamento(600)))),
	(retract(tempoInicial(_));true), get_time(Ti),asserta(tempoInicial(Ti)).

permutacoes(1,1):-!.
permutacoes(Valor,Resultado):-
	Valor1  is Valor-1,
	permutacoes(Valor1,Resultado1),
	Resultado is Valor * Resultado1.

gera:- 
	/* informaçoes relativas ao escalonamento, motoristas a escalonar, e servico de viatura,
	e de acordo com o escallonamento retorno logo uma lista para ser usada no gera_população*/
    lista_motoristas_nworkblocks(Lista),
	/*dados de preferncias para gerações e criterios de paragem*/
	inicializa,
	/* vai gerar uma população usando a lista criada a cima para gerar individuos*/
	gera_populacao(Lista,Pop),
	write('Pop='),write(Pop),nl,
	/*vai avaliar a populacao*/
    avalia_populacao(Pop,PopAv),
	write('PopAv='),write(PopAv),nl,
	/* e ordenala pelo custo*/
	ordena_populacao(PopAv,PopOrd),
    /* vai dizer quantas novas gerações vamos querer*/
	geracoes(NG),
	gera_geracao([],0,NG,PopOrd,0).
	/* Gera uma população de individuos, vai criar tantos cromossomas consoante o tamanho da população
   O tamanho da população e mandado por parametro no inicializa
   O num tarefas ta descrito em cima
   A lista de tarefas esta na base de conhecimento*/
/* base de inicio do gerapopulacao*/

gera_populacao(Lista,Pop):-
	populacao(TamPop),
    servicoV(SV),
	workblocks(SV,NumT),
	gera_populacao(TamPop,Lista,NumT,Pop).

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
/* base do gera-individuo*/
gera_individuo([G],1,[G]):-!.
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
	bloco(X,_,StartTime,_).
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
	avaliaTempos(Seq,Moto,Custo1),
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
	analisaBlocos(ListaInvertida,_,FullTime,_),
	(((FullTime>28800),!,(CustoB is (Custo1+100)));(CustoB is Custo1)),
	analisaSequencia(Lista,CustoS),
	Custo is CustoB+CustoS.
/* analisa o cromossoma a nivel de sequencia*/
analisaSequencia([],0).
analisaSequencia([X|L],Custo):-
  analisaSequencia(L,Custo1),
  getNext(L,X1),
  (((X1 is 0),!,(Custo is Custo1));(
	  Dif is X-X1,
	  (((Dif == 1 ),!,(Custo is Custo1));(Custo is (Custo1+5)))
  )).
/* para ir buscar o elemento a seguir*/
getNext([],0).
getNext([X|_],X).

/* verifica se respeita a regra do tempo maximo por turno de 8h, ou maximo de 4 horas sem descanso de 1 hora,
para obdecer o resultado tem de ser inferior a 28800*/
analisaBlocos([],0,0,_).
analisaBlocos([X],SomaTime,FullTime,Last):-
	analisaBlocos([],SomaTime1,FullTime1,Last),
	bloco(X,_,StartTime,Last),
	Time is Last-StartTime,
	SomaTime is SomaTime1+Time,
	(((SomaTime>14400),!,(FullTime is FullTime1+Time+28800));
	(FullTime is FullTime1 +Time)).
analisaBlocos([X|ListaBlocos],SomaTime,FullTime,End):-
	analisaBlocos(ListaBlocos,SomaTime1,FullTime1,Last),
	bloco(X,_,StartTime,EndTime),
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
	servicoViatura(SV,_,ListaBlocos),
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
peso([_*Peso],Peso):-!.
/*retorna o topo de uma lista*/
topo([X|_],X).
/*condição de paragem de atingir a ultima geração*/
gera_geracao(_,G,G,Pop,_):-!,
	write('Geracao '), write(G), write(':'), nl, write(Pop), nl.
/*condicao de paragem tendo em conta a quantidade de turnos até á exaustao de soluções*/
gera_geracao(_,_,_,_,MG):-geracaoEstaganar(MG),write('Tendo em que nao existem modificacoes na melhor solucao a ')
						,write(MG), write('turnos, chegamos a conclusao que obtemos as melhores solucoes'),!.
/*condição de paragem tendo em conta o valor atingido*/
gera_geracao(_,N,_,Pop,_):-valorObjetivo(Valor),topo(Pop,X),emboxa(X,L),peso(L,Peso),Peso=<Valor,
			write('Geracao '), write(N), write(':'), nl, write(Pop), nl,
			write('Na '),write(N),write("º geracao foi atingido um valor igual ou inferior ao pretendido : "),write(X),!.
/*condição de paragem tendo em conta o tempo de processamento*/
gera_geracao(_,_,_,_,_):-get_time(Tf),
						   /* vai buscar o tempo atual e final*/
						   tempoInicial(Ti),
						   /* vai buscar o tempo de processamento permitido*/
						   tempoProcessamento(Minutos), 
						   /*converte em segundos*/
						   Segundos is Minutos*60,
						   /* calcula o tempo decorrido desde o inicio*/ 
						   Dif is Tf-Ti,
						   /* verifica se o programa ja corre á mais tempo que aquele permitido*/
						   Dif>Segundos,
						   /* se sim informa o utilizador*/
						   write('atingiu o tempo limite de processamento passado por parametro'),!.
/* vai gerar e imprimir as proximas gerações*/
gera_geracao(Best,N,G,Pop,IterWithoutChanges):-
	write('Geracao '), write(N), write(':'), nl, write(Pop), nl,
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
	valoresAcrescentar(Header,Lista,Valores),
	/*vai verificar o tamanho da lista a juntar*/
	tamanhoLista(Valores,Numeros),
	/* vai buscar o tamanho da população geral dada como parametro no inicio*/
	populacao(NP),
	/* vai verificar quantos menbros da nova lista vamos ficar*/
	RestantesMembros is NP-Numeros, 
	/*vai reduzir a lista da nova geração para uma lista com apenas "RestantesMenbros", de maneira a que "RestantesMembros" + "Numeros" seja igual a "NP"*/
	cortaLista(Lista,RestoLista,RestantesMembros),
	/* vai juntar os bests da geração anterior com os menbros desta*/
	append(Valores,RestoLista,ListaFinal).
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
cruzar(Ind1,_,P1,P2,NInd11):-
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
