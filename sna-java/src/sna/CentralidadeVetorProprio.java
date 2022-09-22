/*
 * Trabalho de LAPR1 - Análise de Redes Sociais
 * Aplicação SNA - 1NAB - Grupo 3 - The Wizards - 2018
 */
package sna;

import org.la4j.Matrix;
import org.la4j.decomposition.EigenDecompositor;
import org.la4j.matrix.dense.Basic2DMatrix;

/**
 * @see Enunciado - nº 3.2.1 - Medidas ao Nível dos Nós (Centralidade de Vetor
 * Próprio)
 * @author franciscoferreiradasilva
 */
public class CentralidadeVetorProprio {

    /**
     * Calcula a centralidade de cada nó/entidade através da matriz de 
     * adjacências. Como? 1- Usa a biblioteca la4j para calcular os valores e 
     * vetores próprios da matriz; 2- Seleciona o maior valor próprio e o vetor
     * próprio correspondente; 3- Normaliza o vetor próprio; 4- Cada coordenada
     * do vetor próprio corresponde à centralidade do nó com posição semelhante
     * na matriz dos nós
     *
     * @param adjac - matriz de adjacências
     * @return o vetor próprio
     */
    public static double[] calculaCentralidadeDosNos(double[][] adjac) {
        int pos;
        double centralidadeNos[] = new double[adjac.length];

        Matrix matriz = new Basic2DMatrix(adjac);
        EigenDecompositor proprio = new EigenDecompositor(matriz);
        Matrix[] resultado = proprio.decompose();
        
        double[][] valProp = resultado[1].toDenseMatrix().toArray();
        double[][] vetProp = resultado[0].toDenseMatrix().toArray();
        
        pos = procuraMaiorValorProp(valProp);
        for (int i = 0; i < centralidadeNos.length; i++) {
            centralidadeNos[i] = vetProp[i][pos];
        }
        normalizaVetorProprio(centralidadeNos);
        return centralidadeNos;
    }
    
    // Procura o maior valor próprio na matriz dos valores próprios e retorna o seu índice
    public static int procuraMaiorValorProp(double[][] valProp) {
        double maior = valProp[0][0];
        int pos = 0;
        for (int i = 1; i < valProp.length; i++) {
            if (valProp[i][i] > maior) {
                pos = i;
                maior = valProp[i][i];
            }
        }
        return pos;
    }
    
    // Normaliza o vetor próprio e força-o a valor positivo
    public static void normalizaVetorProprio(double[] centralidadeNos) {
        double var = 0;
        for (int i = 0; i < centralidadeNos.length; i++) {
            var += Math.pow(centralidadeNos[i], 2);
        }
        double fatorNorm = (double) 1 / Math.sqrt(var);
        for (int i = 0; i < centralidadeNos.length; i++) {
            centralidadeNos[i] = Math.abs(centralidadeNos[i] * fatorNorm);
        }
    }
    
}
