/*
 * Trabalho de LAPR1 - Análise de Redes Sociais
 * Aplicação SNA - 1NAB - Grupo 3 - The Wizards - 2018
 */
package sna;

/**
 * @see Enunciado - nº 3.2.2 - Medidas ao Nível da Rede (Potências da Matriz de
 * Adjacências)
 * @author frederico
 */
public class PotenciasMatrizAdjacencias {

    /**
     * Multiplica uma matriz n vezes, com n maior do que 0 e menor do que a
     * ordem da matriz Retorna uma string formatada correctamente, pronta para
     * ser mostrada no ecrã ou para ser passada para o ficheiro
     *
     * @param mat - matriz de adjacências
     * @param k - o número de vezes que vamos multiplicar a matriz (potência)
     * @param flagModoTeste - determina se vai ser corrido no modo interativo ou
     * não-interativo
     * @return totalString - matriz de potência n
     */
    public static String multiplicarNvezes(double[][] mat, int k, boolean flagModoTeste) {
        if (flagModoTeste) {
            String totalString = "";
            if (k > 0 && k < mat.length) {
                double[][] original = new double[mat.length][mat[0].length];
                System.arraycopy(mat, 0, original, 0, mat.length);
                for (int i = 1; i < k; i++) {
                    mat = multiplicar(mat, original);
                }
                totalString = Utils.transformaMatrizEmString(mat);
            } else {
                System.out.println("ERRO: Potência fora do intervalo válido");
            }
            return totalString;
        } else {
            String totalString = "";
            if (k < 1) {
                System.out.println("ERRO: Potência não pode ser inferior a 1");
            } else if (k == 1) {
                totalString = totalString + Utils.transformaMatrizEmString(mat);
            } else if (1 < k && k < mat.length) {
                double[][] original = new double[mat.length][mat[0].length];
                System.arraycopy(mat, 0, original, 0, mat.length);
                for (int i = 1; i <= k; i++) {
                    if (i == 1) {
                        totalString = totalString + "\nMatriz de potência " + i + ":\n\n"
                                + Utils.transformaMatrizEmString(mat);
                    } else {
                        mat = multiplicar(mat, original);
                        totalString = totalString + "\nMatriz de potência " + i + ":\n\n"
                                + Utils.transformaMatrizEmString(mat);
                    }
                }
                return totalString;
            } else {
                System.out.println("ERRO: Potência pedida excede a ordem da matriz");
                return totalString = "";
            }
            return totalString;
        }
    }

    // Calcula o produto escalar da linha de uma matriz e da coluna de outra matriz
    public static double produtoEscalarLinhaPorColuna(double[][] mat1, double[][] mat2, int linha, int coluna) {
        double prod = 0;
        int cont = 0;
        for (int j = 0; j < mat2.length; j++) {
            prod = prod + mat1[linha][j] * mat2[cont][coluna];
            cont++;
        }
        return prod;
    }

    //Multiplica duas matrizes recorrendo ao produto escalar de uma linha da primeira por uma coluna da segunda
    public static double[][] multiplicar(double[][] mat, double[][] mat2) {
        double[][] novaMat = new double[mat.length][mat[0].length];
        for (int linha = 0; linha < mat.length; linha++) {
            for (int coluna = 0; coluna < mat[linha].length; coluna++) {
                novaMat[linha][coluna] = produtoEscalarLinhaPorColuna(mat, mat2, linha, coluna);
            }
        }
        return novaMat;
    }
}
