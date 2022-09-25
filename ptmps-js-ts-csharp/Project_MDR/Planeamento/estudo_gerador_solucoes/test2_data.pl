% 21 NÓS / 20 LINHAS

no("Baltar", "BALTR", t, f, -8.38716802227697, 41.1937898023744).
no("Besteiros", "BESTR", f, f, -8.34043029659082, 41.217018845589).
no("Cete", "CETE", t, f, -8.35164059584564, 41.183243425797).
no("Cristelo", "CRIST", t, f, -8.34639896125324, 41.2207801252676).
no("Duas Igrejas", "DIGRJ", f, f, -8.35481024956726, 41.2278665802794).
no("Estação (Lordelo)", "ESTLO", f, t, -8.4227924957086, 41.2521157104055).
no("Estação (Paredes)", "ESTPA", f, t, -8.33448520831829, 41.2082119860192).
no("Lordelo", "LORDL", t, f, -8.42293614720057, 41.2452627470645).
no("Mouriz", "MOURZ", t, f, -8.36577272258403, 41.1983610215263).
no("Parada de Todeia", "PARAD", t, f, -8.37023578802149, 41.1765780321068).
no("Paredes", "PARED", t, f, -8.33566951069481, 41.2062947118362).
no("Vila Cova de Carros", "VCCAR", t, f, -8.35109395257277, 41.2090666564063).
% BASE +4 NÓS
no("Campanha", "CAMPAN", t, f, -8.37109395257277, 41.2290666564063).
no("Foz", "FOZ", t, f, -8.36109395257277, 41.2190666564063).
no("Paranhos", "PARAN", f, f, -8.34109395257277, 41.2080666564063).
no("Bonfim", "BONF", f, f, -8.33109395257277, 41.2040666564063).

% TEST2a - 10 pontos de rendição / 2 estações de recolha
no("Aguiar de Sousa", "AGUIA", f, f, -8.4464785432391, 41.1293363229325).
no("Gandra", "GAND", f, f, -8.43958765792976, 41.1956579348384).
no("Recarei", "RECAR", f, f, -8.42215867462191, 41.1599363478137).
no("Sobrosa", "SOBRO", f, f, -8.38118071581788, 41.2557331783506).
no("Vandoma", "VANDO", f, f, -8.34160692293342, 41.2328015719913).

% TEST2b - 12 pontos de rendição / 2 estações de recolha
% no("Aguiar de Sousa", "AGUIA", t, f, -8.4464785432391, 41.1293363229325).
% no("Gandra", "GAND", t, f, -8.43958765792976, 41.1956579348384).
% no("Recarei", "RECAR", f, f, -8.42215867462191, 41.1599363478137).
% no("Sobrosa", "SOBRO", f, f, -8.38118071581788, 41.2557331783506).
% no("Vandoma", "VANDO", f, f, -8.34160692293342, 41.2328015719913).

% TEST2c - 14 pontos de rendição / 2 estações de recolha
% no("Aguiar de Sousa", "AGUIA", t, f, -8.4464785432391, 41.1293363229325).
% no("Gandra", "GAND", t, f, -8.43958765792976, 41.1956579348384).
% no("Recarei", "RECAR", t, f, -8.42215867462191, 41.1599363478137).
% no("Sobrosa", "SOBRO", t, f, -8.38118071581788, 41.2557331783506).
% no("Vandoma", "VANDO", f, f, -8.34160692293342, 41.2328015719913).

% TEST2d - 15 pontos de rendição / 2 estações de recolha
% no("Aguiar de Sousa", "AGUIA", t, f, -8.4464785432391, 41.1293363229325).
% no("Gandra", "GAND", t, f, -8.43958765792976, 41.1956579348384).
% no("Recarei", "RECAR", t, f, -8.42215867462191, 41.1599363478137).
% no("Sobrosa", "SOBRO", t, f, -8.38118071581788, 41.2557331783506).
% no("Vandoma", "VANDO", t, f, -8.34160692293342, 41.2328015719913).

% 16 LINHAS
linha("Paredes_Aguiar", 1, ["AGUIA","RECAR", "PARAD", "CETE", "PARED"], 31, 15700).
linha("Paredes_Aguiar", 3, ["PARED", "CETE","PARAD", "RECAR", "AGUIA"], 31, 15700).
linha("Paredes_Gandra", 5 , ["GAND", "VANDO", "BALTR", "MOURZ", "PARED"], 26, 13000).
linha("Paredes_Gandra", 8, ["PARED", "MOURZ", "BALTR", "VANDO", "GAND"], 26, 13000).
linha("Paredes_Lordelo", 9, ["LORDL","VANDO", "BALTR", "MOURZ", "PARED"], 29, 14300).
linha("Paredes_Lordelo", 11, ["PARED","MOURZ", "BALTR", "VANDO", "LORDL"], 29, 14300).
linha("Lordelo_Parada", 24, ["LORDL", "DIGRJ", "CRIST", "VCCAR", "BALTR", "PARAD"], 22, 11000).
linha("Lordelo_Parada", 26, ["PARAD", "BALTR", "VCCAR", "CRIST", "DIGRJ", "LORDL"], 22, 11000).
% linha("Cristelo_Baltar", nd0, ["CRIST", "VCCAR", "BALTR"], 8, 4000).
% linha("Baltar_Cristelo", nd1, ["BALTR", "VCCAR", "CRIST"], 8, 4000).
linha("Sobrosa_Cete", 22, ["SOBRO", "CRIST", "BESTR", "VCCAR", "MOURZ", "CETE"], 23, 11500).
linha("Sobrosa_Cete", 20, ["CETE", "MOURZ", "VCCAR", "BESTR", "CRIST", "SOBRO"], 23, 11500).
linha("Estação(Lordelo)_Lordelo",34,["ESTLO","LORDL"], 2,1500).
linha("Lordelo_Estação(Lordelo)",35,["LORDL","ESTLO"], 2,1500).
linha("Estação(Lordelo)_Sobrosa",36,["ESTLO","SOBRO"], 5,1500).
linha("Sobrosa_Estação(Lordelo)",37,["SOBRO","ESTLO"], 5,1800).
linha("Estação(Paredes)_Paredes",38,["ESTPA","PARED"], 2,1500).
linha("Paredes_Estação(Paredes)",39,["PARED","ESTPA"], 2,1500).
% BASE +4 LINHAS
linha("Sobrosa_Lordelo", 42, ["SOBRO", "CRIST", "BALTR", "VANDO", "MOURZ", "LORDL"], 25, 12500).
linha("Sobrosa_Lordelo", 44, ["LORDL", "MOURZ", "VANDO", "BALTR", "CRIST", "SOBRO"], 25, 12500).
linha("Campanha_Foz", 46, ["CAMPAN", "BONF", "PARAN", "PARAD", "VCCAR", "FOZ"], 20, 10500).
linha("Campanha_Foz", 48, ["FOZ", "VCCAR", "PARAD", "PARAN", "BONF", "CAMPAN"], 20, 10500).
