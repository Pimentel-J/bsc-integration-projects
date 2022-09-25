sequence(X,Y,[X,Y|_]):-!.
sequence(X,Y,[_|L]):- sequence(X,Y,L).