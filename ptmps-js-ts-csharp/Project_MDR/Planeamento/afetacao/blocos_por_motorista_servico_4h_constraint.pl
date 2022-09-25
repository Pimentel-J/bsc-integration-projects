% ### PROJETO - Sprint D ###
% 3) Associação dos Motoristas aos Vehicle Duties
% 3.1) Obter a partir da lista de motoristas associados a um determinado serviço de viatura, incluindo o respetivo nº total de blocos 
% que têm disponível para associar a vehicle duties, uma nova lista com a quantidade de blocos por motorista de acordo com a 
% hard constraint - 4 horas seguidas de serviço prestado, no máximo.

% Conteúdo das seguintes Bases de Conhecimento no final deste ficheiro
% :- consult('./bloco.pl'). % servicoViatura(IDServicoViatura,StartTime,StartTime,ListaBlocos)
% :- consult('./servicoViatura.pl'). % bloco(ID,StartTime,StartTime,ListaViagens,IDServicoViatura)

:-dynamic motoristasNumBlocos/2.
:-dynamic motoristasNumBlocosResultado/2.
:-dynamic motoristasNumBlocosResultadoSemBlocoAlocado/2.
:-dynamic motoristasTemp/2.
:-dynamic blocosDoServico/1.
:-dynamic gruposBlocosDe4HorasMax/1.
:-dynamic gruposBlocosDe4HorasMaxSemMotoristaAlocado/1.
:-dynamic totalTime/1.
:-dynamic temp/1.

% Inputs - [SV,(Motorista,Qtd. de blocos)]
% ?- distribuirQtdBlocosPelosMotoristas(["7",[(276,8),(518,7),(432,3)]],Resultado).
% ?- distribuirQtdBlocosPelosMotoristas(["7",[(276,6),(518,5),(432,3)]],Resultado).
% Lista de blocos do SV "7" -> ["7","124","125","126","127","128","129","130","131","132","133","134","135","136","137","138"]
% ?- Output = ["7",[(276,4),(518,4),(276,4),(518,3),(432,1)]]
distribuirQtdBlocosPelosMotoristas([]).
distribuirQtdBlocosPelosMotoristas([SV|[NumBlocoMotorList]],Output):-
    forall(member((MotorId,NumBlocos), NumBlocoMotorList), assertz(motoristasNumBlocos(MotorId,NumBlocos))),
    servicoViatura(SV,_,_,SVBlocos),
    % forall(member(BlocoID, SVBlocos), assertz(blocosDoServico(BlocoID))),
    assertz(totalTime(0)),
    dividirBlocosDe4Horas(SVBlocos),
    dividirBlocosDe4HorasResultado(BlocosDe4HorasList),
    distribuirBlocosPelosMotoristas(NumBlocoMotorList,BlocosDe4HorasList),
    distribuirBlocosPelosMotoristasResultado(QtdBlocosPorMotorista4HorasMax,SobraMotoristas,SobraBlocos),
    format('~w ~w~n', ["Qtd. Blocos Por Motorista - 4 Horas Seguidas Max. (ID Motorista, Qtd.) ->", QtdBlocosPorMotorista4HorasMax]),
    format('~w ~w~n', ["Motoristas Que Sobraram Sem Bloco Atribuido (ID Motorista, Qtd.) ->", SobraMotoristas]),
    format('~w ~w~n', ["Blocos Sem Motorista Associado (ID Bloco) ->", SobraBlocos]),
    gerarResultado(SV,QtdBlocosPorMotorista4HorasMax,Output).

gerarResultado(SV,QtdBlocosPorMotorista4HorasMax,Output):-
    append([SV], [QtdBlocosPorMotorista4HorasMax], Output).
        

% ### Distribuir blocos pelos motoristas tendo em contra a hard constraint - 4 horas seguidas no máximo ###
% Teste1 -> ?- distribuirBlocosPelosMotoristas([(276,8),(518,9),(432,3)],[["7", "124", "125", "126"],["127", "128", "129", "130"]]).
% Output1 - ?- listing(motoristasNumBlocosResultado) -> [(276,4),(518,4)]
% Output1 - ?- listing(motoristasNumBlocosResultadoSemBlocoAlocado) -> [(276,4),(518,5),(432,3)]
% Teste2 -> ?- distribuirBlocosPelosMotoristas([(276,8),(518,7),(432,3)],[["7", "124", "125", "126"], ["127", "128", "129", "130"], ["131", "132", "133", "134"], ["135", "136", "137", "138"]]).
% Output2 - ?- listing(motoristasNumBlocosResultado) -> [(276,4),(518,4),(276,4),(518,3),(432,1)]
% Output2 - ?- listing(motoristasNumBlocosResultadoSemBlocoAlocado) -> [((432,2)]
% Teste3 -> ?- distribuirBlocosPelosMotoristas([(276,5),(518,4),(432,1)],[["7", "124", "125", "126"],["127", "128", "129", "130"],["131","132","133","134"]]).
% Output3 - ?- listing(motoristasNumBlocosResultado) -> [(276,4),(518,4),(276,1),(432,1)]
% Output3 - ?- listing(gruposBlocosDe4HorasMaxSemMotoristaAlocado) -> ["133","134"]
distribuirBlocosPelosMotoristas([],[]):-!.
distribuirBlocosPelosMotoristas(Motoristas,[]):-!,
    forall(member((MotorId,NumBlocos), Motoristas), assertz(motoristasNumBlocosResultadoSemBlocoAlocado(MotorId,NumBlocos))).
distribuirBlocosPelosMotoristas([],Blocos):-!,
    forall(member(SVBloco, Blocos), assertz(gruposBlocosDe4HorasMaxSemMotoristaAlocado(SVBloco))).

distribuirBlocosPelosMotoristas([Motorista1|NumBlocoMotorList],[Blocos1|BlocosDe4HorasList]):-
    length(Blocos1,QtdBlocosParaDistribuir),
    getQtdBlocosLivres(Motorista1,QtdBlocosMotorLivres),
    getMotoristaID(Motorista1,Motorista1ID),

% Verificar a quantidade de blocos disponíveis em cada motorista
% Se a qtd. de blocos livres do motorista for menor que o lote de blocos (de 4 horas máx.), 
% também atualiza a lista com o lote de blocos de 4 horas em conformidade
    ((QtdBlocosMotorLivres < QtdBlocosParaDistribuir) ->
        assertz(motoristasNumBlocosResultado(Motorista1ID,QtdBlocosMotorLivres)),
        removeN1osElems(QtdBlocosMotorLivres,Blocos1,Blocos1Sobra),
        insereNoInicio(Blocos1Sobra,BlocosDe4HorasList,BlocosDe4HorasListNonFlat),!,
        flatten(BlocosDe4HorasListNonFlat, BlocosDe4HorasListFlatten),      
        dividirBlocosDe4Horas(BlocosDe4HorasListFlatten),!,
        dividirBlocosDe4HorasResultado(BlocosDe4HorasListFlatten2),!,
        distribuirBlocosPelosMotoristas(NumBlocoMotorList,BlocosDe4HorasListFlatten2)
    ; % Em caso de igualdade, apenas insere o motorista e respetivos blocos na BC - motoristasNumBlocosResultado/2
    (((QtdBlocosMotorLivres = QtdBlocosParaDistribuir) -> 
    assertz(motoristasNumBlocosResultado(Motorista1ID,QtdBlocosMotorLivres)),
    !,distribuirBlocosPelosMotoristas(NumBlocoMotorList,BlocosDe4HorasList)
    ; % QtdBlocosMotorLivres > QtdBlocosParaDistribuir -> Sobram blocos disponíveis no motorista para alocar blocos depois
    NovaQtdBlocosLivres is QtdBlocosMotorLivres - QtdBlocosParaDistribuir,
    asserta(motoristasTemp(Motorista1ID,NovaQtdBlocosLivres)),
    assertz(motoristasNumBlocosResultado(Motorista1ID,QtdBlocosParaDistribuir)),
    % Colocar o motorista com blocos disponíveis na posição 2, de modo a ser novamente selecionado depois da pausa obrigatória
    insere((Motorista1ID,NovaQtdBlocosLivres),2,NumBlocoMotorList,NumBlocoMotorList2),
    !,distribuirBlocosPelosMotoristas(NumBlocoMotorList2,BlocosDe4HorasList)))).

% Tranforma os resultados de distribuirBlocosPelosMotoristas/2 numa lista final
distribuirBlocosPelosMotoristasResultado(QtdBlocosPorMotorista4HorasMax,SobraMotoristas,SobraBlocos):-
    findall((Motorista1ID,QtdBlocosMotorLivres),motoristasNumBlocosResultado(Motorista1ID,QtdBlocosMotorLivres),QtdBlocosPorMotorista4HorasMax),
    findall((Motorista1ID,QtdBlocosMotorLivres),motoristasNumBlocosResultadoSemBlocoAlocado(Motorista1ID,QtdBlocosMotorLivres),SobraMotoristas),
    findall(QtdBlocosParaDistribuir,gruposBlocosDe4HorasMaxSemMotoristaAlocado(QtdBlocosParaDistribuir),SobraBlocos),
% "Limpar" Base de Conhecimento
    (retractall(totalTime(_));true),
    (retractall(motoristasNumBlocos(_));true),
    (retractall(motoristasNumBlocosResultado(_));true),
    (retractall(motoristasNumBlocosResultadoSemBlocoAlocado(_));true),
    (retractall(motoristasTemp(_));true),
    (retractall(gruposBlocosDe4HorasMax(_));true),
    (retractall(gruposBlocosDe4HorasMaxSemMotoristaAlocado(_));true),!.

% ### UTILS ###
getQtdBlocosLivres((_,Res),Res).

getMotoristaID((Motorista1ID,_),Motorista1ID).

removeN1osElems(0,L,L):-!.
removeN1osElems(N,[_|L],L1):-N1 is N-1,removeN1osElems(N1,L,L1).

% Inserir um elemento numa dada posição numa lista
insere(X,1,L,[X|L]):-!.
insere(X,_,[],[X]):-!.
insere(X,N,[Y|L],[Y|L1]):-N1 is N-1,insere(X,N1,L,L1).


% ### Dividir uma lista de blocos numa lista de listas de blocos com a duração máxima de 4 horas ###
% Teste -> ?- dividirBlocosDe4Horas(["7","124","125","126","127","128","129","130","131","132","133","134","135","136","137","138"]).
% Resultado -> [["7", "124", "125", "126"], ["127", "128", "129", "130"], ["131", "132", "133", "134"], ["135", "136", "137", "138"]]
dividirBlocosDe4Horas([]):-!.

% Se for o último bloco, insere todos os blocos do lote temporário na BC - gruposBlocosDe4HorasMax/1
dividirBlocosDe4Horas([BlocoID|[]]):-
    assertz(temp(BlocoID)),
    inserirEmGruposBlocosDe4Horas(),
    (retract(totalTime(_));true),asserta(totalTime(0)),!.

dividirBlocosDe4Horas([BlocoID|RestoBlocos]):-
    % Determinar a druação do bloco e o total acumulado por lote de blocos
    bloco(BlocoID,StartTime,EndTime,_,_),
    BlocoTime is EndTime - StartTime,
    (retract(totalTime(OldTotalTime));true),
    TotalTime is OldTotalTime + BlocoTime,
    asserta(totalTime(TotalTime)),

    % Enquanto a soma do tempo deste lote de blocos for =< 14k, insere na lista
    ((TotalTime =< 14400) -> assertz(temp(BlocoID)),!,dividirBlocosDe4Horas(RestoBlocos);
    insereNoInicio(BlocoID,RestoBlocos,RestoBlocos2),
    inserirEmGruposBlocosDe4Horas(),
    (retract(totalTime(TotalTime));true),asserta(totalTime(0)),
    !,dividirBlocosDe4Horas(RestoBlocos2)).

% Tranforma o resultado de dividirBlocosDe4Horas/1 numa lista final
inserirEmGruposBlocosDe4Horas():-
    findall(BlocoIdTemp,temp(BlocoIdTemp),ListaTemp),
    assertz(gruposBlocosDe4HorasMax(ListaTemp)),(retractall(temp(_));true).

dividirBlocosDe4HorasResultado(BlocosDividosList):-
    findall(BlocoListTemp,gruposBlocosDe4HorasMax(BlocoListTemp),BlocosDividosList),
    (retractall(gruposBlocosDe4HorasMax(_));true),!.

% ### UTILS ###
insereNoInicio(X,L,[X|L]).

% ### Parte da BC dos ficheiros 'servicoViatura.pl' 'bloco.pl'
servicoViatura("7",28700,82440,["7","124","125","126","127","128","129","130","131","132","133","134","135","136","137","138"]).

bloco("4",21180,24680,["443","278","313"],"7").
bloco("5",24680,26580,["445","93"],"7").
bloco("6",26580,28740,["447","350","389"],"7").
bloco("7",28740,31920,["449","168","183"],"7").
bloco("124",31920,35520,["169","184"],"7").
bloco("125",35520,39120,["170","185"],"7").
bloco("126",39120,42720,["171","186"],"7").
bloco("127",42720,46320,["172","187"],"7").
bloco("128",46320,49920,["173","188"],"7").
bloco("129",49920,53520,["174","189"],"7").
bloco("130",53520,57120,["175","190"],"7").
bloco("131",57120,60720,["176","191"],"7").
bloco("132",60720,64320,["177","192"],"7").
bloco("133",64320,67920,["178","193"],"7").
bloco("134",67920,71520,["179","194"],"7").
bloco("135",71520,75120,["180","195"],"7").
bloco("136",75120,78720,["181","196"],"7").
bloco("137",78720,82320,["182","197"],"7").
bloco("138",82320,82440,["450"],"7").