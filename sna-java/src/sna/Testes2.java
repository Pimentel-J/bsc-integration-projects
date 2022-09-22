/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sna;

/**
 *
 * @author ASUS
 */
public class Testes2 {
    public static void main(String[] args) {
                //TESTES EMPÍRICOS AO CUSTO COMPUTACIONAL DO PAGE RANK
        //VERSUS O CUSTO DE OBTER O VALOR PRÓPRIO
        //DESCOMENTAR UM DOS SEGUINTES PARA CORRER
        //NÃO CORRA OS DOIS DE CADA VEZ
        
        //TESTE PAGERANK
        /*
        double[][] matrizTeste = {{0,1,1},{1,1,0},{0,0,1}};
        double[] resEsperado = {0.447, 0.894, 0};
        for (int i = 0; i < 1000000; i++) {
        double[][] matrizM = pageRank.calculoMatrizM(matrizTeste, 0.45);
        double[][] resultado = pageRank.pageRank(matrizM, 1000);
        boolean flag = (1 == 1);
        }
        */
        //TESTE VALOR PRÓPRIO
        /*
        double[][] matrizTeste = {{0,1,1},{1,1,0},{0,0,1}};
        double[] resEsperado = {0.447, 0.894, 0};
        for (int i = 0; i < 10000000; i++) {
        double centralid[] = CentralidadeVetorProprio.calculaCentralidadeDosNos(matrizTeste);
        boolean flag = (1 == 1);
        }
        */
    }
}
