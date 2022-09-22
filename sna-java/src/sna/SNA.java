/*
 * Trabalho de LAPR1 - Análise de Redes Sociais
 * Aplicação SNA - 1NAB - Grupo 3 - The Wizards - 2018
 */
package sna;

import java.util.*;
import java.io.FileNotFoundException;

/**
 * Classe principal da Aplicação
 */
public class SNA {

    public final static Scanner in = new Scanner(System.in);
    public final static Formatter out = new Formatter(System.out);

    /* CONFIGS da APP - Constantes a declarar */
    public final static String SEPARADOR_FICH_NOS = ",";
    public final static int PARAMETROS_FICH_NOS = 5;
    public final static int PARAMETROS_FICH_RAMOS = 3;
    public final static int NUMERO_DE_METRICAS = 5;
    public final static int NUM_ARG_MODO_INTERAT = 3;
    public final static int NUM_ARG_MODO_NAO_INTERAT = 5;
    public final static int NUMERO_ALGARISMOS_FORMATACAO = 5;

    public static void main(String[] args) throws FileNotFoundException {
        System.out.println("\nARGUMENTOS PASSADOS POR PARÂMETRO\n");
        boolean orientado = Utils.validaGrafoOrientadoOuNao(args[args.length - 1]);
        boolean flag = Utils.validacaoArgumentos(args, orientado);
        // Se os argumentos introduzidos são inválidos sai do programa

        /* Estruturas de dados a declarar*/
        System.out.println("\n\nLEITURA DOS FICHEIROS\n");
        String[][] nos = Utils.lerFicheiroEPreencherMatriz(args[args.length - 2], PARAMETROS_FICH_NOS, orientado);
        String[][] ramos = Utils.lerFicheiroEPreencherMatriz(args[args.length - 1], PARAMETROS_FICH_RAMOS, orientado);
        Utils.validaRamosSemNos(nos, ramos);
        double[][] adjac = Utils.fazerMatrizAdjac(nos, ramos, orientado);
        int op;

        if (flag) {
            // MODO INTERATIVO
            if (orientado == false) {
                //MENU REDE NÃO ORIENTADA
                do {
                    op = menuRedeNotOrientada();
                    switch (op) {

                        case 1: //Apresentar rede
                            Utils.apresentaRede(nos, ramos);
                            break;
                        case 2:
                            Utils.cabecalhoMetodo("Grau de um nó");
                            double graus[] = grauNo.ObterVetorGrauDeEntrada(adjac);
                            Utils.mostrarResult(nos, graus, "GRAU", false);
                            AbrirURL.abrirURLdaMatriz(nos, graus);
                            Utils.pausa(true);
                            break;
                        case 3:
                            Utils.cabecalhoMetodo("Centralidade de vector próprio");
                            double centralidNos[] = CentralidadeVetorProprio.calculaCentralidadeDosNos(adjac);
                            Utils.mostrarResult(nos, centralidNos, "CENTRALIDADE", true);
                            AbrirURL.abrirURLdaMatriz(nos, centralidNos);
                            Utils.pausa(true);
                            break;
                        case 4:
                            Utils.cabecalhoMetodo("Grau médio");
                            float grauMed = grauMedio.calcularGrauMedio(adjac);
                            System.out.printf("%n%nGRAU MÉDIO: %5.3f%n", grauMed);
                            Utils.pausa(true);
                            break;
                        case 5:
                            Utils.cabecalhoMetodo("Densidade");
                            double densid = DensidadeMatrizAdjac.densidadeMatrizAdjac(adjac);
                            System.out.printf("%n%nDENSIDADE: %5.3f%n", densid);
                            Utils.pausa(true);
                            break;
                        case 6:
                            Utils.cabecalhoMetodo("Potências da matriz de adjacências");
                            int k = Utils.lerInteiros("\nIntroduza a potência que pretende:");
                            System.out.printf("%n%n%s%d%s%n%n", "MATRIZ DE POTÊNCIA ", k, ":");
                            String multiplicada = PotenciasMatrizAdjacencias.multiplicarNvezes(adjac, k, true);
                            System.out.printf("%s%n", multiplicada);
                            in.nextLine();
                            Utils.pausa(true);
                            break;
                        case 0:
                            System.out.println("\nShutdown OK");
                            System.exit(0);

                        default:
                            System.out.println("\nOpção incorreta. Repita");
                            break;
                    }
                } while (op != 0);
            
            } else {
                //MENU REDE ORIENTADA
                do {
                    op = menuRedeOrientada();
                    switch (op) {

                        case 1: //Apresentar rede
                            Utils.apresentaRede(nos, ramos);
                            break;
                        case 2:
                            Utils.cabecalhoMetodo("Grau de entrada de um nó");
                            double grauEntrada[] = grauNo.ObterVetorGrauDeEntrada(adjac);
                            Utils.mostrarResult(nos, grauEntrada, "GRAU ENTRADA", false);
                            AbrirURL.abrirURLdaMatriz(nos, grauEntrada);
                            Utils.pausa(true);
                            break;
                        case 3:
                            Utils.cabecalhoMetodo("Grau de saída de um nó");
                            double grauSaida[] = grauNo.ObterVetorGrauDeSaida(adjac);
                            Utils.mostrarResult(nos, grauSaida, "GRAU SAIDA", false);
                            AbrirURL.abrirURLdaMatriz(nos, grauSaida);
                            Utils.pausa(true);
                            break;
                        case 4:
                            Utils.cabecalhoMetodo("Vetor Page Rank");
                            opcaoPageRank(nos, adjac);
                            break;
                        case 0:
                            System.out.println("\nShutdown OK");
                            System.exit(0);

                        default:
                            System.out.println("\nOpção incorreta. Repita");
                            break;
                    }
                } while (op != 0);
            }

        } else {
            // MODO DE ESCRITA PARA FICHEIRO
            String nomeFichOutput = Utils.determinaNomeFichOutput(args[args.length - 1]);
            System.out.println("\n\nChamado método Calcular todas as medidas");
            if (orientado == false) {
                modoFich_redeNaoOrient(nos, ramos, adjac, nomeFichOutput, args[2].trim());
            } else {
                modoFich_redeOrient(nos, ramos, adjac, nomeFichOutput, args[2].trim(), args[4].trim());
            }
            System.out.printf("Exportada toda a informação para o ficheiro %s%n%n", nomeFichOutput);
        }
        // Fim do main    
    }

    private static int menuRedeNotOrientada() {
        String texto = "\n\n|============| SNA App 1.0 - Rede Não Orientada |============|\n\n"
                + " Escolha a opção pretendida e prima ENTER: \n "
                + " 1 - Apresentar a rede \n "
                + " 2 - Grau de um nó \n "
                + " 3 - Centralidade de vector próprio  \n "
                + " 4 - Grau Médio \n "
                + " 5 - Densidade \n "
                + " 6 - Potências da matriz de adjacências \n "
                + " 0 - Terminar \n";
        
        int escolha = Utils.validarOpcaoMenu(texto);
        return escolha;
    }
    
    private static int menuRedeOrientada() {
        String texto = "\n\n|============| SNA App 1.0 - Rede Orientada |============|\n\n"
                + " Escolha a opção pretendida e prima ENTER: \n "
                + " 1 - Apresentar a rede \n "
                + " 2 - Grau de entrada de um nó \n "
                + " 3 - Grau de saída de um nó  \n "
                + " 4 - Vetor Page Rank \n "
                + " 0 - Terminar \n";
        
        int escolha = Utils.validarOpcaoMenu(texto);
        return escolha;
    }
    
    private static int menuPageRank() {
        String texto = "\n\n|============| SNA App 1.0 - Vetor Page Rank |============|\n\n"
                + " Escolha a opção pretendida e prima ENTER: \n "
                + " 1 - Algoritmo Page Rank \n "
                + " 2 - Centralidade de vetor próprio da matriz M \n "
                + " 0 - Voltar ao menu principal \n";
        
        int escolha = Utils.validarOpcaoMenu(texto);
        return escolha;
    }
    
    /**
     * Calcula a matriz M e apresenta um submenu com duas opções de cálculo do 
     * vetor Page Rank, seja pelo algoritmo seja pela centralidade
     * 
     * @param nos matriz de Strings do ficheiro dos nós
     * @param adjac matriz de adjacências
     */
    private static void opcaoPageRank(String[][] nos, double[][] adjac) {
        int op;
        double d = Utils.lerDampingFactor();
        double[][] matrizM = pageRank.calculoMatrizM(adjac, d);
        do {
            op = menuPageRank();
            switch (op) {
                case 1:
                    Utils.cabecalhoMetodo("Algoritmo Page Rank");
                    int k = Utils.lerInteiros("\nIntroduza o número de iterações:");
                    double[][] vetores = pageRank.pageRank(matrizM, k);
                    System.out.print(Utils.imprimePageRank(nos, vetores));
                    double[] vetorPageRank = Utils.criaVetorAPartirUltLinhaMatriz(vetores);
                    AbrirURL.abrirURLdaMatriz(nos, vetorPageRank);
                    // FALTA ABRIR O URL
                    in.nextLine();
                    Utils.pausa(true);
                    break;
                case 2:
                    Utils.cabecalhoMetodo("Centralidade de vetor próprio da matriz M");
                    double[] vetor = CentralidadeVetorProprio.calculaCentralidadeDosNos(matrizM);
                    Utils.mostrarResult(nos, vetor, "CENTRALIDADE [M]", true);
                    AbrirURL.abrirURLdaMatriz(nos, vetor);
                    Utils.pausa(true);
                    break;
                case 0:
                    System.out.println("\nOperação bem sucedida");
                    break;
                default:
                    System.out.println("\nOpção incorreta. Repita");
                    break;
            }
        } while (op != 0);     
    }
    
    /**
     * Calcula as métricas relativas a redes não orientadas e imprime-as num ficheiro
     * 
     * @param nos matriz de Strings do ficheiro dos nós
     * @param ramos matriz de Strings do ficheiro dos ramos
     * @param adjac matriz de adjacências
     * @param nomeFich nome do ficheiro onde os dados vão ser escritos
     * @param kString parâmetro k passado como argumento na linha de comandos
     * @throws FileNotFoundException 
     */
    private static void modoFich_redeNaoOrient(String[][] nos, String[][] ramos, 
            double[][] adjac, String nomeFich, String kString) throws FileNotFoundException {
        
        // Calcula as métricas todas
        double graus[] = grauNo.ObterVetorGrauDeEntrada(adjac);
        double centralidNos[] = CentralidadeVetorProprio.calculaCentralidadeDosNos(adjac);
        float grauMed = grauMedio.calcularGrauMedio(adjac);
        double densid = DensidadeMatrizAdjac.densidadeMatrizAdjac(adjac);
        int k = Integer.parseInt(kString);
        String multiplicada = PotenciasMatrizAdjacencias.multiplicarNvezes(adjac, k, false);

        // Recebe os parâmetros e escreve no ficheiro
        Utils.escreveFich_redeNaoOrient(nomeFich, nos, ramos, graus, centralidNos, 
                grauMed, densid, multiplicada);
    }
    
    /**
     * Calcula as métricas relativas a redes orientadas e imprime-as num ficheiro
     * 
     * @param nos matriz de Strings do ficheiro dos nós
     * @param ramos matriz de Strings do ficheiro dos ramos
     * @param adjac matriz de adjacências
     * @param nomeFich nome do ficheiro onde os dados vão ser escritos
     * @param kString parâmetro k passado como argumento na linha de comandos
     * @param dString parâmetro d passado como argumento na linha de comandos 
     * @throws FileNotFoundException 
     */
    private static void modoFich_redeOrient(String[][] nos, String[][] ramos, 
            double[][] adjac, String nomeFich, String kString, String dString) throws FileNotFoundException {
        
        // Calcula as métricas todas
        double grauEntrada[] = grauNo.ObterVetorGrauDeEntrada(adjac);
        double grauSaida[] = grauNo.ObterVetorGrauDeSaida(adjac);
        double d = Double.parseDouble(dString);
        double[][] matrizM = pageRank.calculoMatrizM(adjac, d);
        int k = Integer.parseInt(kString);
        double[][] vetores = pageRank.pageRank(matrizM, k);
        String vetoresPageRank = Utils.imprimePageRank(nos, vetores);
        double centralid[] = CentralidadeVetorProprio.calculaCentralidadeDosNos(matrizM);

        // Recebe os parâmetros e escreve no ficheiro
        Utils.escreveFich_redeOrient(nomeFich, nos, ramos, grauEntrada, grauSaida, vetoresPageRank, centralid, d);
    }
// Fim da classe SNA    
}
