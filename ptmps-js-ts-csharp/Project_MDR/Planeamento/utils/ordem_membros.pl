% condição: encontrar N1 e N2 constam nos restantes nós contidos no caminho
ordem_membros(No1,No2,[No1|L]):-member(No2,L),!.
ordem_membros(No1,No2,[_|L]):-ordem_membros(No1,No2,L).