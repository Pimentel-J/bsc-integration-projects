/*
 * Trabalho de LAPR1 - Análise de Redes Sociais
 * Aplicação SNA - 1NAB - Grupo 3 - The Wizards - 2018
 */
package sna;

/**
 * @see Enunciado - nº 3.2.2 - Medidas ao Nível da Rede (Grau Médio)
 * @author joaopimentel
 */
public class grauMedio {

    /**
     * Calculo do grau médio (utiliza o return do método grauNo.ObterVetorGraus)
     *
     * @param adjac - matriz de adjacências
     * @return grau médio
     */
    public static float calcularGrauMedio(double[][] adjac) {
        double[] grauNosArray = grauNo.ObterVetorGrauDeEntrada(adjac);
        int somatorioNos = 0;
        for (int k = 0; k < grauNosArray.length; k++) {
            somatorioNos += grauNosArray[k];
        }
        float grauMedio = (float) somatorioNos / grauNosArray.length;

        return grauMedio;
    }
}
