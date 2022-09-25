
:-dynamic geracoes/1.
:-dynamic populacao/1.
:-dynamic prob_cruzamento/1.
:-dynamic prob_mutacao/1.
:-dynamic perc_preservacao/1.
:-dynamic geracaoEstaganar/1.
:-dynamic valorObjetivo/1.
:-dynamic tempoInicial/1.
:-dynamic tempoProcessamento/1.
% tarefa(Id,TempoProcessamento,TempConc,PesoPenalizacao).
tarefa(t1,2,5,1).
tarefa(t2,4,7,6).
tarefa(t3,1,11,2).
tarefa(t4,3,9,3).
tarefa(t5,3,8,2).
% tarefas(NTarefas).
tarefas(5).
% parameteriza��o
inicializa:-
	/* vai ler o numero de geracoes que vai trabalhar*/
	write('Numero de novas Geracoes: '),read(NG),
    (retract(geracoes(_));true), ((NG>0,!,asserta(geracoes(NG)));
	/*não foi definido um limite para as gerações, nao sendo entao criterio de paragem*/
	(write('Limite de Geracoes nao estabelecido'),nl,asserta(geracoes(99999999)))),
	/*vai ler a dimensao da populacao*/
	write('Dimensao da Populacao: '),read(DP),
	(retract(populacao(_));true), tarefas(Num),permutacoes(Num,Resultado),
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
/*faz as permutacoes as possiveis tarefas*/
permutacoes(1,1):-!.
permutacoes(Valor,Resultado):-
	Valor1  is Valor-1,
	permutacoes(Valor1,Resultado1),
	Resultado is Valor * Resultado1.

gera:-
	inicializa,
    /* vai gerar uma população com o n cromossomas em que n é igual ao tamanho da base de conhecimento das tarefas*/
	gera_populacao(Pop),
	write('Pop='),write(Pop),nl,
    /* vai verificar o custo de cada cromossoma e envia o seu resultado pra uma nova lista*/
	avalia_populacao(Pop,PopAv),
	write('PopAv='),write(PopAv),nl,
    /* vai ordenar a lista dos valores*/
	ordena_populacao(PopAv,PopOrd),
    /* vai dizer quantas novas gerações vamos querer*/
	geracoes(NG),
	gera_geracao([],0,NG,PopOrd,0).
/* Gera uma população de individuos, vai criar tantos cromossomas consoante o tamanho da população
   O tamanho da população e mandado por parametro no inicializa
   O num tarefas ta descrito em cima
   A lista de tarefas esta na base de conhecimento*/
gera_populacao(Pop):-
	populacao(TamPop),
	tarefas(NumT),
	findall(Tarefa,tarefa(Tarefa,_,_,_),ListaTarefas),
	gera_populacao(TamPop,ListaTarefas,NumT,Pop).
/* base de inicio do gerapopulacao*/
gera_populacao(0,_,_,[]):-!.
gera_populacao(TamPop,ListaTarefas,NumT,[Ind|Resto]):-
    /*realiza uma iteração*/
	TamPop1 is TamPop-1,
    /* vai começar com a lista da população a vazio e ir acrescentando*/
	gera_populacao(TamPop1,ListaTarefas,NumT,Resto),
    /* gera um cromossoma e adiciona a lista da populacao*/
	gera_individuo(ListaTarefas,NumT,Ind),
    /*se esta combinação ainda nao existir*/
	not(member(Ind,Resto)).

/* se o cromossoma ja existir na lista simplesmente volta a iterar e nao dimunui o tamPop, porque a lista da população nao cresceu*/
gera_populacao(TamPop,ListaTarefas,NumT,L):-
	gera_populacao(TamPop,ListaTarefas,NumT,L).
/* base do gera-individuo*/
gera_individuo([G],1,[G]):-!.
/* vai gerar um cromossoma aleatorio com as tarefas que existem*/
gera_individuo(ListaTarefas,NumT,[G|Resto]):-
	NumTemp is NumT + 1, % To use with random
	random(1,NumTemp,N),
	retira(N,ListaTarefas,G,NovaLista),
	NumT1 is NumT-1,
	gera_individuo(NovaLista,NumT1,Resto).

/* vai retirar um elemento a uma lista*/
retira(1,[G|Resto],G,Resto).
retira(N,[G1|Resto],G,[G1|Resto1]):-
	N1 is N-1,
	retira(N1,Resto,G,Resto1).

/*base do avalia populacao*/
avalia_populacao([],[]).
/* ATENCÃO -- Ind *V o que esta a fazer?????????????????????*/
avalia_populacao([Ind|Resto],[Ind*V|Resto1]):-
	avalia(Ind,V),
    /*vai repetir para todos os cromossomas de uma população*/
	avalia_populacao(Resto,Resto1).
/*Exemplo:
tarefa(t1,2,5,1).
tarefa(t2,4,7,6).
tarefa(t3,1,11,2).
tarefa(t4,3,9,3).
tarefa(t5,3,8,2).
t1,t2,t3,t4,t5
[t1|t2,t3,t4,t5], 2 , 2 - 5 < 0 logo 0 + 13 = 13
[t2|t3,t4,t5], 6 , 6 - 7 < 0 logo 0 + 13 = 13
[t3|t4,t5], 7 , 7 - 11 < 0 logo 0 + 13 = 13
[t4|t5], 10 , (10-9)*3 + 10 = 13
[t5], 13 , (13-8)*2 + 0 = 10
[], 13, 0
*/
/* segundo predicado do avalia que vai lidar com 3 argumentos*/
avalia(Seq,V):-
	avalia(Seq,0,V).

/* base do avalia*/
avalia([],_,0).
/* vai calcular o custo de cada cromossoma*/
avalia([T|Resto],Inst,V):-
	tarefa(T,Dur,Prazo,Pen),
	InstFim is Inst+Dur,
	avalia(Resto,InstFim,VResto),
	(
		(InstFim =< Prazo,!, VT is 0)
  ;
		(VT is (InstFim-Prazo)*Pen)
	),
	V is VT+VResto.
/* vai ordenar uma lista */
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
	cruzamento(Permutation,NPop1),
	/* faz as mutações*/
	mutacao(NPop1,NPop),
	/* vai verificar a quantidade de items a preservar desta geração atraves da taxa de preservação*/
	quantidade_preservar(Numeros),
	/* vai avaliar a população*/
	avalia_populacao(NPop,NPopAv),
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
	  cruzar(Ind2,Ind1,P1,P2,NInd2))
	;
	(NInd1=Ind1,NInd2=Ind2)),
	cruzamento(Resto,Resto1).

% Remove um elemento da lista a partir do índice
remove(X, [X|T], T).
remove(X, [H|T], [H|T1]):- remove(X,T,T1).

encontraeElem(0, [H|_], H):- !.
encontraeElem(I, [_|T], E):- X is I - 1, encontraeElem(X, T, E).
/*predicado para gerar pontos de cruzamento a ser usados no cruzar, para trabalharmos as mutações*/
gerar_pontos_cruzamento(P1,P2):-
	gerar_pontos_cruzamento1(P1,P2).
/*tendo em conta o numero de tarefas, vai produzir numeros aleatorios para retornar*/
gerar_pontos_cruzamento1(P1,P2):-
	/*tamanho da base de conhecimento de tarefas*/
    tarefas(N),
    /*acresenta 1 a esse tamanho*/
	NTemp is N+1,
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
cruzar(Ind1,Ind2,P1,P2,NInd11):-
	sublista(Ind1,P1,P2,Sub1),
	tarefas(NumT),
	R is NumT-P2,
	rotate_right(Ind2,R,Ind21),
	elimina(Ind21,Sub1,Sub2),
	P3 is P2 + 1,
	insere(Sub2,Sub1,P3,NInd1),
	eliminah(NInd1,NInd11).
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
	tarefas(N),
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
	tarefas(T),
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














