/*
 * Trabalho de LAPR1 - Análise de Redes Sociais
 * Aplicação SNA - 1NAB - Grupo 3 - The Wizards - 2018
 */
package sna;

/**
 * @see Enunciado - nº 3.2.1 - Medidas ao Nível dos Nós (Grau de um Nó)
 * @author diogo
 */
public class grauNo {

    /**
     * Método para receber como parâmetro a matriz de adjacências e retornar um
     * vetor de inteiros, cujo índice está em concordância com o número de nós
     *
     * @param adjac - matriz de adjacências
     * @return grau dos nós
     */
    public static double[] ObterVetorGrauDeEntrada(double adjac[][]) {
        /* aqui é recebido como parâmetro a matriz de adjacências, que é impreterivelmente quadrada, daí o length, quer para o i quer para o j, serem iguais
        Este método é idêntico ao original pois retrata todas as entradas nos nós da rede*/
        double[] grausEntrada = new double[adjac.length];
        for (int i = 0; i < adjac.length; i++) {
            int sum = 0;
            for (int j = 0; j < adjac.length; j++) {

                sum += adjac[i][j];

            }
            grausEntrada[i] = sum;
        }
        return grausEntrada;

    }
     public static double[] ObterVetorGrauDeSaida(double adjac[][]) {
        /* aqui é recebido como parâmetro a matriz de adjacências, que é impreterivelmente quadrada, daí o length, quer para o i quer para o j, serem iguais
         este método é uma reutilização do método anterior de forma a poder obter a soma das colunas ao invés das linhas, que acaba por representar o numero 
         de ramos de saída*/
        double[] grausSaida = new double[adjac.length];
        for (int j = 0; j < adjac.length; j++) {
            int sum = 0;
            for (int i = 0; i < adjac.length; i++) {

                sum += adjac[i][j];

            }
            grausSaida[j] = sum;
        }
        return grausSaida;

    }
    
    
    
}
