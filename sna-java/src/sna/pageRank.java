/*
 * Trabalho de LAPR1 - Análise de Redes Sociais
 * Aplicação SNA - 1NAB - Grupo 3 - The Wizards - 2018
 */
package sna;

/**
 * @see Enunciado - nº 3.3.1 - Medidas ao Nível dos Nós (Page Rank)
 * @author frederico & joaopimentel
 */
public class pageRank {

    /**
     * Chama o método que obtém a Matriz M, inicializa um vector a 1s e
     * multiplica a matriz pelo vector k vezes.
     *
     * @param matrizM
     * @param Kiteracoes
     * @return tabela com a evolução do vector pagerank ao longo das várias
     * iterações disposto ao longo das linhas
     */
    public static double[][] pageRank(double[][] matrizM, int Kiteracoes) {
        double[][] matrizVetores = new double[Kiteracoes + 1][matrizM.length];
        double[] vectorRes = new double[matrizM.length];
        for (int i = 0; i < vectorRes.length; i++) {
            vectorRes[i] = 1;
        }
        completarMatrizComVetor(matrizVetores, vectorRes, 0);
        obterRes(vectorRes, Kiteracoes, matrizM, matrizVetores);
        return matrizVetores;
    }

    // inserir um vetor numa linha da matriz
    public static void completarMatrizComVetor(double[][] matriz, double[] vetor, int linha) {
        for (int i = 0; i < matriz[0].length; i++) {
            matriz[linha][i] = vetor[i];
        }
    }

    /**
     * Obtém o produto de uma matriz por um vector; valida para testar se a
     * operação é possível.
     *
     * @param vectorRes
     * @param Kiteracoes
     * @param matrizM
     * @param tabela
     */
    public static void obterRes(double[] vectorRes, int Kiteracoes, double[][] matrizM, double[][] tabela) {
        if (Kiteracoes < 1) {
            System.out.println("ERRO: Introduza um k válido");
            System.exit(1);
        } else {
            for (int i = 1; i <= Kiteracoes; i++) {
                vectorRes = multiplicaMatrizPorColuna(matrizM, vectorRes);
                // É SÓ PARA NORMALIZAR O ÚLTIMO VETOR!!
                if (i == Kiteracoes) {
                    CentralidadeVetorProprio.normalizaVetorProprio(vectorRes);
                }
                completarMatrizComVetor(tabela, vectorRes, i);
            }
        }
    }

    public static double[] multiplicaMatrizPorColuna(double[][] M, double[] v) {
        if (M[0].length != v.length) {
            System.out.println("ERRO: Esta multiplicação não está definida");
        } else {
            for (int i = 0; i < v.length; i++) {
                double soma = 0;
                for (int j = 0; j < M[i].length; j++) {
                    soma = soma + M[i][j] * v[j];
                }
                v[i] = soma;
            }
        }
        return v;
    }

    /**
     * Cálculo da matriz M
     *
     * @param adjac - matriz de adjacências
     * @param d - damping factor
     * @return matriz M
     */
    public static double[][] calculoMatrizM(double[][] adjac, double d) {
        int N = adjac.length;
        double[][] matrizM = new double[N][N];
        double umMenosDdivN = (double) (1 - d) / N;
        double[][] matrizA = matrizA(adjac);
        double[][] matriz1 = matriz1(N);

        //1º e 2º termo da soma da equação para determinar a matriz M
        double[][] matrizTermo1 = matrizMulti(matrizA, d);
        double[][] matrizTermo2 = matrizMulti(matriz1, umMenosDdivN);

        //soma dos termos
        for (int i = 0; i < matrizTermo2.length; i++) {
            for (int k = 0; k < matrizTermo2.length; k++) {
                matrizM[i][k] = matrizTermo1[i][k] + matrizTermo2[i][k];
            }
        }
        return matrizM;
    }

    /**
     * All-one matrix (matriz M)
     *
     * @param n - dimensão da matriz
     * @return matriz1
     */
    public static double[][] matriz1(int n) {
        double[][] matriz1 = new double[n][n];
        for (int i = 0; i < matriz1.length; i++) {
            for (int k = 0; k < matriz1.length; k++) {
                matriz1[i][k] = 1;
            }
        }
        return matriz1;
    }

    /**
     * Multiplicação escalar (matriz M)
     *
     * @param matriz
     * @param numReal
     * @return resultado da multiplicação da matriz por um número real
     */
    public static double[][] matrizMulti(double[][] matriz, double numReal) {
        double[][] matrizResult = new double[matriz.length][matriz.length];
        for (int i = 0; i < matriz.length; i++) {
            for (int k = 0; k < matriz.length; k++) {
                matrizResult[i][k] = (double) matriz[i][k] * numReal;
            }
        }
        return matrizResult;
    }

    /**
     * Determinar matriz A (matriz M)
     *
     * @param adjac - matriz de adjacências
     * @return matriz A
     */
    public static double[][] matrizA(double[][] adjac) {
        double[][] matrizA = new double[adjac.length][adjac[0].length];
        double[] grauSaida = grauNo.ObterVetorGrauDeSaida(adjac);

        for (int i = 0; i < matrizA.length; i++) {
            for (int k = 0; k < matrizA[0].length; k++) {
                if (danglingNode(adjac, k) == true) {
                    matrizA[i][k] = (double) 1 / grauSaida.length;
                } else if (adjac[i][k] != 0) {
                    matrizA[i][k] = (double) 1 / grauSaida[k];
                }
            }
        }
        return matrizA;
    }

    /**
     * Verificar se o nó é um dangling node (matriz M)
     *
     * @param adjac - matriz de adjacências
     * @param numNo - posição do nó (coluna) na matriz
     * @return true - é um dangling node / false - não é um dangling node
     */
    public static boolean danglingNode(double[][] adjac, int numNo) {
        boolean flag = false;
        int soma = 0;
        for (int i = 0; i < adjac.length; i++) {
            soma += adjac[i][numNo];
        }
        if (soma == 0) {
            flag = true;
        }
        return flag;
    }
}
