/*
 * Trabalho de LAPR1 - Análise de Redes Sociais
 * Aplicação SNA - 1NAB - Grupo 3 - The Wizards - 2018
 */
package sna;

/**
 * @see Enunciado - nº 3.2.2 - Medidas ao Nível da Rede (Densidade)
 * @author NunoMessederFerreira
 */
public class DensidadeMatrizAdjac {

    /**
     * Este método recebe a matriz de adjacências e calcula a sua densidade
     *
     * @param adjac - matriz de adjacências
     * @return densidade - densidade da matriz
     */
    public static double densidadeMatrizAdjac(double[][] adjac) {
        double maxRamos = (adjac.length * adjac.length) - adjac.length;
        double densidade = 0;
        double sum = 0;
        for (int i = 0; i < adjac.length; i++) {
            for (int j = 0; j < adjac.length; j++) {
                sum += adjac[i][j];
            }
        }
        densidade = sum / maxRamos;
        return densidade;

    }
}
