% Gerar todas as liga��es diretas entre pontos de rendi��o/esta��es de recolha
:-dynamic liga/3.
gera_ligacoes():-findall(_,
    % sele��o de pontos de rendi��o ou esta��es de recolha
    ((no(_,No1,true,false,_,_);no(_,No1,false,true,_,_)),
    (no(_,No2,true,false,_,_);no(_,No2,false,true,_,_)),
    % verifica��o se s�o pontos diferentes
    No1\=No2,
    % vai buscar linha(s) e retira a lista de n�s pertencente a essa linha (caminho)
    linha(_,N,LNos,_,_),
    % verifica a ordem dos n�s na linha
    ordem_membros(No1,No2,LNos),
    % introduz novo facto na BC
    assertz(liga(No1,No2,N))),_).

gera_ligacoes_http():-findall(_,
    % sele��o de pontos de rendi��o ou esta��es de recolha
    ((no(_,No1,true,false,_,_);no(_,No1,false,true,_,_)),
    (no(_,No2,true,false,_,_);no(_,No2,false,true,_,_)),
    % verifica��o se s�o pontos diferentes
    No1\=No2,
    % vai buscar linha(s) e retira a lista de n�s pertencente a essa linha (caminho)
    linha(_,N,LNos,_,_),
    % verifica a ordem dos n�s na linha
    ordem_membros(No1,No2,LNos),
    % introduz novo facto na BC
    http_session_assert(liga(No1,No2,N))),_).