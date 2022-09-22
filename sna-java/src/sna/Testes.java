/*
 * Trabalho de LAPR1 - Análise de Redes Sociais
 * Aplicação SNA - 1NAB - Grupo 3 - The Wizards - 2018
 */
package sna;

/**
 * Classe destinada a testar todos os métodos das métricas, assim como os
 * utilitários
 * 
 * @author Equipa 3
 */
public class Testes {

    public static void main(String[] args) {
        runAllTests();
    }

    /**
     * Método para correr todos os testes
     */
    public static void runAllTests() {
        // TESTES A MÉTODOS UTILITÁRIOS
        System.out.println("\nTESTES AOS MÉTODOS UTILITÁRIOS\n");

        // Teste procurarStringEmMatriz
        String[][] matUtils = {{"aa", "bb"}, {"cc", "dd"}};
        teste_procurarStringEmMatriz("cc", matUtils, matUtils.length, 0, 1);
        // Teste fazerMatrizAdjac
        String[][] exempRamos = {{"cc", "aa", "1"}};
        double[][] expectMat = {{0, 1}, {1, 0}};
        teste_fazerMatrizAdjac(matUtils, exempRamos, expectMat);
        // Teste transformaMatrizEmString
        // Nota: a String seguinte só é válida como teste para SNA.NUMERO_ALGARISMOS_FORMATACAO = 5
        String stringTeste = "[    0,    1]\n[    1,    0]\n\n";
        teste_transformaMatrizEmString(stringTeste, expectMat);
        // FIM DOS TESTES A MÉTODOS UTILITÁRIOS

        // TESTES AOS MÉTODOS DAS MÉTRICAS
        System.out.println("\n\nTESTES AOS MÉTODOS DAS MÉTRICAS\n");
        
        // Testes dos graus dos nós (entrada e saida)
        double[][] teste321 = {{0, 1, 1, 0}, {1, 1, 0, 0}, {0, 1, 1, 0}, {1, 1, 1, 1}};
        double[] expe321 = {2, 2, 2, 4};
        double[] expe33 = {2, 4, 3, 1};
        // Teste dos graus de entrada
        teste_ObterVetorGrauDeEntrada(teste321, expe321);  
        // Teste dos graus de saída
        teste_ObterVetorGrauDeSaida(teste321, expe33);
        // Teste da centralidade de vetor próprio
        double mat322a[][] = {{3, 0, 0}, {0, 7, 0}, {0, 0, 2}};
        teste_procuraMaiorValorProp(mat322a, 1);
        double mat322b[][] = {{0, 1, 0}, {1, 0, 1}, {0, 1, 0}};
        double expected322b[] = {(double) 1 / 2, (double) Math.sqrt(2) / 2, (double) 1 / 2};
        teste_calculaCentralidadeDosNos(mat322b, expected322b);        
        // Teste do grau médio
        double[][] mat331 = {{0, 1, 0}, {1, 0, 0}, {0, 1, 0}};
        teste_grauMedio(mat331, 1);        
        // Teste da densidade de matriz de adjacências
        double[][] teste332 = {{0, 1, 1, 0}, {1, 1, 0, 0}, {0, 1, 1, 0}, {0, 0, 0, 0}};
        teste_densidadeMatrizAdjac(teste332, 0.5);        
        // Teste de potências da matriz de adjacências
        double[][] matA = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}}, matB = {{30, 36, 42}, {66, 81, 96}, {102, 126, 150}};
        testePotenciasMatrizAdjacencias(matA, matB);
        // Teste cálculo da matriz M (Page Rank)
        // testeAdjac - corresponde ao grafo do exemplo 3 (pág. 4) do documento em anexo (Shum, 2013) sobre o Page Rank
        // testeM - igual à matriz do exemplo 3 (pág. 5) do documento anteriormente referido (valores arredondados 3 c.d.)
        double[][] testeAdjac = {{0, 0, 1, 0, 0, 0, 0, 0}, {1, 0, 0, 1, 0, 0, 0, 0}, {1, 0, 0, 0, 0, 0, 0, 0}, {1, 1, 1, 0, 0, 0, 0, 0}, {0, 1, 0, 0, 0, 1, 0, 0}, {0, 0, 0, 0, 0, 0, 1, 1}, {0, 0, 0, 1, 1, 0, 0, 1}, {0, 0, 0, 0, 0, 1, 0, 0}};
        double[][] testeM = {{0.019, 0.019, 0.444, 0.019, 0.019, 0.019, 0.019, 0.019}, {0.302, 0.019, 0.019, 0.444, 0.019, 0.019, 0.019, 0.019}, {0.302, 0.019, 0.019, 0.019, 0.019, 0.019, 0.019, 0.019}, {0.302, 0.444, 0.444, 0.019, 0.019, 0.019, 0.019, 0.019}, {0.019, 0.444, 0.019, 0.019, 0.019, 0.444, 0.019, 0.019}, {0.019, 0.019, 0.019, 0.019, 0.019, 0.019, 0.869, 0.444}, {0.019, 0.019, 0.019, 0.444, 0.869, 0.019, 0.019, 0.444}, {0.019, 0.019, 0.019, 0.019, 0.019, 0.444, 0.019, 0.019}};
        double d = 0.85;
        testeMatrizM(testeAdjac, testeM, d);
        // Teste do Page Rank
        double[][] matrizTeste = {{0,1,1},{1,1,0},{0,0,1}};
        double[] resEsperado = {0.447, 0.894, 0};        
        teste_pageRank(matrizTeste, resEsperado);
        // FIM DOS TESTES AOS MÉTODOS DAS MÉTRICAS
    }
    
    // TESTES AOS MÉTODOS UTILITÁRIOS
    /**
     * Teste do método transformaMatrizEmString
     * @see Utils
     */
    public static void teste_transformaMatrizEmString(String stringTeste, double[][] mat) {
        String resultado = Utils.transformaMatrizEmString(mat);
        boolean flag = (stringTeste.equals(resultado));
        System.out.println("TransformaMatrizEmString: " + converteTrueFalse(flag));
    }

    /**
     * Teste do método procurarStringEmMatriz
     * @see Utils 
     */
    public static void teste_procurarStringEmMatriz(String elemento, String[][] matriz, int nLinhas, int coluna, int expected) {
        boolean sucesso = false;
        int pos = Utils.procurarStringEmMatriz(elemento, matriz, matriz.length, coluna);
        if (pos == expected) {
            sucesso = true;
        }
        System.out.println("procurarStringEmMatriz: " + converteTrueFalse(sucesso));
    }
    
    /**
     * Teste do método fazerMatrizAdjac
     * @see Utils
     */
    public static void teste_fazerMatrizAdjac(String[][] nos, String[][] ramos, double[][] expected) {
        boolean sucesso = true;
        double[][] result = Utils.fazerMatrizAdjac(nos, ramos, false);
        for (int i = 0; i < result.length; i++) {
            for (int j = 0; j < result.length; j++) {
                if (Math.abs((double) (result[i][j] - expected[i][j])) > 0.000001) {
                    sucesso = false;
                }
            }
        }
        System.out.println("fazerMatrizAdjac: " + converteTrueFalse(sucesso));
    }
    // FIM DOS TESTES AOS MÉTODOS UTILITÁRIOS
    

    // TESTES AOS MÉTODOS DAS MÉTRICAS
    /**
     * @author diogo
     * @see grauNo
     * @param adjac - matriz de adjacências
     * @param testegraus - resultado esperado
     */
   //TESTE AO MÉTODO PARA OBTER O VETOR DOS GRAUS DE ENTRADA DOS NÓS
     public static void teste_ObterVetorGrauDeEntrada(double adjac[][], double testegraus[]) {
        boolean sucesso = true;
        double[] grauteste = grauNo.ObterVetorGrauDeEntrada(adjac);
        for (int i = 0; i < grauteste.length; i++) {
            if (grauteste[i] != testegraus[i]) {
                sucesso = false;
            }

        }
        System.out.println("ObterVetorGrausdeEntrada: " + converteTrueFalse(sucesso));
    }
    //TESTE AO MÉTODO PARA OBTER O VETOR DOS GRAUS DE SAIDA DOS NÓS
    public static void teste_ObterVetorGrauDeSaida(double adjac[][], double testegraus[]) {
        boolean sucesso = true;
        double[] grauteste = grauNo.ObterVetorGrauDeSaida(adjac);
        for (int i = 0; i < grauteste.length; i++) {
            if (grauteste[i] != testegraus[i]) {
                sucesso = false;
            }

        }
        System.out.println("ObterVetorGrausDeSaida: " + converteTrueFalse(sucesso));
    }
    
    // Fim teste

    /**
     * @author francisco
     * @see CentralidadeVetorProprio
     * @param mat - matriz de adjacências
     * @param expected - resultado esperado
     */
    public static void teste_procuraMaiorValorProp(double[][] mat, int expected) {
        boolean sucesso = false;
        int pos = CentralidadeVetorProprio.procuraMaiorValorProp(mat);
        if (pos == expected) {
            sucesso = true;
        }
        System.out.println("procuraMaiorValorProp: " + converteTrueFalse(sucesso));
    }

    /**
     * @author francisco
     * @see CentralidadeVetorProprio
     * @param mat - matriz de adjacências
     * @param expected - resultado esperado
     */
    public static void teste_calculaCentralidadeDosNos(double[][] mat, double[] expected) {
        boolean sucesso = true;
        double result[] = CentralidadeVetorProprio.calculaCentralidadeDosNos(mat);
        for (int i = 0; i < expected.length; i++) {
            if (Math.abs((double) (result[i] - expected[i])) > 0.000001) {
                sucesso = false;
            }
        }
        System.out.println("calculaCentralidadeDosNos: " + converteTrueFalse(sucesso));
    }
    // Fim teste CentralidadeVetorProprio

    /**
     * @author joaopimentel
     * @see grauMedio
     * @param mat - matriz de adjacências
     * @param expected - resultado esperado
     */
    public static void teste_grauMedio(double[][] mat, int expected) {
        boolean resultado = true;
        float testeGrauMedio = grauMedio.calcularGrauMedio(mat);
        if (testeGrauMedio != expected) {
            resultado = false;
        }
        System.out.println("grauMedio: " + converteTrueFalse(resultado));
    }
    // Fim teste

    /**
     * @author Nuno
     * @see DensidadeMatrizAdjac
     * @param adjac - matriz de adjacências
     * @param expectedDens - resultado esperado
     */
    public static void teste_densidadeMatrizAdjac(double[][] adjac, double expectedDens) {
        boolean sucesso = false;
        double teste = DensidadeMatrizAdjac.densidadeMatrizAdjac(adjac);
        if(teste==expectedDens)
        {
            sucesso=true;
        }
        System.out.println("densidadeMatrizAdjac: " + converteTrueFalse(sucesso));
    }
    // Fim teste
    
    /**
     * @author frederico
     * @see PotenciasMatrizAdjacencias
     * @param matA - matriz de adjacências
     * @param matB - resultado esperado
     */
    public static void testePotenciasMatrizAdjacencias(double[][] matA, double[][] matB) {
        String multiplicada = PotenciasMatrizAdjacencias.multiplicarNvezes(matA, 2, true);
        String resultado = Utils.transformaMatrizEmString(matB);
        String flag = (multiplicada.equals(resultado)) ? "Sucesso" : "Falhado";
        System.out.println("PotenciasMatrizAdjacencias (segunda potência a título de exemplo): " + flag);
    }
    // Fim teste PotenciasMatrizAdjacencias
    
    /**
     * @author joaopimentel
     * @param mat - matriz de adjacências
     * @param expected - resultado esperado
     * @param d - damping factor
     */
    public static void testeMatrizM(double[][] mat, double[][] expected, double d) {
        boolean resultado = true;
        double[][] matrizM = pageRank.calculoMatrizM(mat, d);
        for (int i = 0; i < mat.length; i++) {
            for (int k = 0; k < mat.length; k++) {
                if (Math.abs((double) (matrizM[i][k] - expected[i][k])) > 0.001) {
                    resultado = false;
                }
            }
        }
        System.out.println("Cálculo da matriz M: " + converteTrueFalse(resultado));
    }
    
    /**
     * @author frederico
     * @see PotenciasMatrizAdjacencias
     * @param matrizTeste
     * @param resEsperado em forma de vetor 
     */
    public static void teste_pageRank(double[][] matrizTeste, double[] resEsperado) {
        boolean flag = true;
        double[][] matrizM = pageRank.calculoMatrizM(matrizTeste, 1);
        double[][] resultado = pageRank.pageRank(matrizM, 20);
        double[] vetorPageRank = Utils.criaVetorAPartirUltLinhaMatriz(resultado);
        for (int i = 0; i < vetorPageRank.length; i++) {
            if (Math.abs((double) (vetorPageRank[i] - resEsperado[i])) > 0.01) {
                flag = false;
            }
        }
        System.out.println("Page Rank: " + converteTrueFalse(flag));
    }    
        
    // FIM DOS TESTES AOS MÉTODOS DAS MÉTRICAS
    
    /**
     * Converter o resultado do boolean para "Sucesso" ou "Falhado"
     * @param resultado (true ou false)
     * @return Sucesso/Falhado
     */
    public static String converteTrueFalse(boolean resultado) {
        if (resultado == true) {
            return "Sucesso";
        }
        return "Falhado";
    }
}
