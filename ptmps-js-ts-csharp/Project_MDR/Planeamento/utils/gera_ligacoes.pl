% Gerar todas as ligações diretas entre pontos de rendição/estações de recolha
:-dynamic liga/3.
gera_ligacoes():-findall(_,
    % seleção de pontos de rendição ou estações de recolha
    ((no(_,No1,true,false,_,_);no(_,No1,false,true,_,_)),
    (no(_,No2,true,false,_,_);no(_,No2,false,true,_,_)),
    % verificação se são pontos diferentes
    No1\=No2,
    % vai buscar linha(s) e retira a lista de nós pertencente a essa linha (caminho)
    linha(_,N,LNos,_,_),
    % verifica a ordem dos nós na linha
    ordem_membros(No1,No2,LNos),
    % introduz novo facto na BC
    assertz(liga(No1,No2,N))),_).

gera_ligacoes_http():-findall(_,
    % seleção de pontos de rendição ou estações de recolha
    ((no(_,No1,true,false,_,_);no(_,No1,false,true,_,_)),
    (no(_,No2,true,false,_,_);no(_,No2,false,true,_,_)),
    % verificação se são pontos diferentes
    No1\=No2,
    % vai buscar linha(s) e retira a lista de nós pertencente a essa linha (caminho)
    linha(_,N,LNos,_,_),
    % verifica a ordem dos nós na linha
    ordem_membros(No1,No2,LNos),
    % introduz novo facto na BC
    http_session_assert(liga(No1,No2,N))),_).