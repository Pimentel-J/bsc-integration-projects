:- dynamic no/6.

distance(PI,PF,Res):-findall((Lat,Long),no(_,PI,_,_,Lat,Long),Ponto1),
                     findall((Lat,Long),no(_,PF,_,_,Lat,Long),Ponto2),
                     distance2(Ponto1,Ponto2,Res).

distance2([(Lat1,Long1)],[(Lat2,Long2)],Result):- Result is ((6378.137 * acos( cos( Lat1 * 3.14159265358979323846 /180)*
                                                                cos( Lat2 * 3.14159265358979323846 /180)*
                                                                cos( (Long2 * 3.14159265358979323846 /180)
                                                              - (Long1 * 3.14159265358979323846 /180))
                                                              + sin( Lat1 * 3.14159265358979323846 /180)*
                                                                sin( Lat2 * 3.14159265358979323846 /180)))*1000).