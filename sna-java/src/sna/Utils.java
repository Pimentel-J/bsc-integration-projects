/*
 * Trabalho de LAPR1 - Análise de Redes Sociais
 * Aplicação SNA - 1NAB - Grupo 3 - The Wizards - 2018
 */
package sna;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Formatter;
import java.util.Scanner;
import static sna.SNA.in;

/**
 * Classe com os métodos utilitários
 */
public class Utils {

    // MÉTODOS PARA VALIDAÇÃO DOS PARÂMETROS DE ENTRADA
    /**
     * Valida os argumentos e retorna true se estivermos no modo interativo e
     * false se não estivermos
     *
     * @param args
     * @return flag (true ou false)
     */
    public static boolean validacaoArgumentos(String[] args, boolean orientado) {
        for (int i = 0; i < args.length; i++) {
            System.out.println(args[i]);
        }
        boolean flag = true;
        if (!(args.length == 7 || args.length == 3 || args.length == 5)) {
            System.out.println("ERRO: Número de argumentos inválido");
            imprimirArgsExemplo();
            System.exit(1);
        } else if (args[0].equals("-t")) {
            flag = false;
            if (orientado && !(args[1].equals("-k") && args[3].equals("-d"))) {
                System.out.println("ERRO: O segundo argumento tem que ser -k e o quarto -d");
                imprimirArgsExemplo();
                System.exit(1);
            } else if (orientado && args.length != 7) {
                System.out.println("ERRO: No modo não interativo e com um grafo orientado são precisos 7 argumentos");
                imprimirArgsExemplo();
                System.exit(1);
            } else if (!orientado && args.length != 5) {
                System.out.println("ERRO: No modo não interativo e com um grafo não orientado são precisos 5 argumentos");
                imprimirArgsExemplo();
                System.exit(1);
            }
        } else if (!args[0].equals("-n")) {
            System.out.println("ERRO: Primeiro argumento tem que ser -n ou -t");
            imprimirArgsExemplo();
            System.exit(1);
        }
        return flag;
    }

    /**
     * Retorna uma flag, falsa se o grafo não é orientado, verdadeira se é
     * orientado recebe como argumento o nome do ficheiro
     *
     * @param nomeFich
     * @return orientado - true ou false
     * @throws FileNotFoundException
     */
    public static boolean validaGrafoOrientadoOuNao(String nomeFich) throws FileNotFoundException {
        boolean orientado = true, flag = true;
        Scanner leitor = new Scanner(new File(nomeFich));
        while (leitor.hasNextLine() && flag) {
            String linha = leitor.nextLine();
            String[] check = linha.split(":");
            if ((linha.trim()).length() == 0) {
                System.out.println("ERRO: Primeira linha vazia");
                imprimirArgsExemplo();
                System.exit(1);
            }
            if (check.length < 2) {
                System.out.println("ERRO: Falta indicar um ficheiro de entrada");
                imprimirArgsExemplo();
                System.exit(1);
            } else {
                linha = check[1];
                linha = linha.trim();
                if (linha.equals("nonoriented")) {
                    orientado = false;
                } else if (!(linha.equals("oriented") || linha.equals("nonoriented"))) {
                    System.exit(1);
                }
            }
            flag = false;
        }
        leitor.close();
        return orientado;
    }

    public static void imprimirArgsExemplo() {
        System.out.println("Modo interativo: java -jar SNA.jar -n rs_nomedarede_nos.csv rs_nomedarede_ramos.csv\n"
                + "Modo ficheiro (não orientado): java -jar SNA.jar -t -k XX rs_nomedarede_nos.csv rs_nomedarede_ramos.csv\n"
                + "Modo ficheiro (orientado): java -jar SNA.jar -t -k XX -d YY rs_nomedarede_nos.csv rs_nomedarede_ramos.csv");
    }

    /**
     * Recebe uma string e verifica se todos os caracteres são números
     *
     * @param texto
     * @return resposta (true ou false)
     */
    public static boolean verificaNumero(String texto) {
        boolean resposta = true;
        texto = texto.trim();
        if (texto.length() > 0) {
            for (int i = 0; i < texto.length(); i++) {
                if (!Character.isDigit(texto.charAt(i))) {
                    resposta = false;
                    break;
                }
            }
        } else {
            resposta = false;
        }
        return resposta;
    }

    // FIM DOS MÉTODOS PARA VALIDAÇÃO DOS PARÂMETROS DE ENTRADA
    // MÉTODOS PARA CRIAÇÃO DAS ESTRUTURAS DE DADOS ATRAVÉS DA LEITURA DOS FICHEIROS
    /**
     * Validação de ramos sem nós
     *
     * @param nos
     * @param ramos
     */
    public static void validaRamosSemNos(String[][] nos, String[][] ramos) {
        for (int i = 0; i < ramos.length; i++) {
            int pos = procurarStringEmMatriz(ramos[i][0], nos, nos.length, 0);
            if (pos == -1) {
                System.exit(1);
            }
        }
        for (int i = 0; i < ramos.length; i++) {
            int pos = procurarStringEmMatriz(ramos[i][1], nos, nos.length, 0);
            if (pos == -1) {
                System.exit(1);
            }
        }
        System.out.println("\nTodos os ramos têm um nó associado");
    }

    /**
     * Lê um ficheiro cujo nome é passado por parâmetro, cria uma matriz de
     * strings, preenche-a com os dados do ficheiro e avisa para erros de
     * repetição.
     *
     * Depois usa a matriz que contém elementos repetidos, cria uma nova sem os
     * elementos repetidos e retorna-a.
     *
     * @param nomeFich - nome do ficheiro a ler
     * @param nColunas - número de colunas da matriz a criar
     * @param orientado - flag de grafo orientado
     * @return matriz criada já preenchida e validada
     * @throws FileNotFoundException
     */
    public static String[][] lerFicheiroEPreencherMatriz(String nomeFich, int nColunas, boolean orientado) throws FileNotFoundException {

        boolean flagNosJaFoiLido = (nColunas != SNA.PARAMETROS_FICH_NOS);

        int nEl = numeroLinhas(nColunas, nomeFich, orientado, flagNosJaFoiLido);
        String[][] tabela = new String[nEl][nColunas];
        preencherMatriz(tabela, nColunas, nomeFich, orientado, flagNosJaFoiLido);

        int nElutil = removerRepetidos(tabela, orientado, flagNosJaFoiLido);
        validaTamanhoMax(nElutil);
        String[][] novaTabela = new String[nElutil][nColunas];
        System.arraycopy(tabela, 0, novaTabela, 0, nElutil);

        return novaTabela;
    }

    // Validação do nº de linhas máximo
    public static void validaTamanhoMax(int nElutil) {
        if (nElutil > 200) {
            System.out.println("ERRO: Mais de 200 linhas num ficheiro");
            System.exit(1);
        }
    }

    /**
     * Conta as linhas não vazias do ficheiro e devolve o número de linhas
     * excetuando a linha de cabeçalho
     *
     * @param nColunas
     * @param nomeFich
     * @return nEl
     * @throws FileNotFoundException
     */
    public static int numeroLinhas(int nColunas, String nomeFich, boolean orientado, boolean flagNosJaFoiLido) throws FileNotFoundException {
        Scanner fInput = new Scanner(new File(nomeFich));
        int nEl = 0;
        int flag = 0;
        while (fInput.hasNextLine()) {
            String linha = fInput.nextLine();
            if ((linha.trim()).length() > 0) {
                nEl++;
            } else if ((linha.trim()).length() == 0) {
                flag++;
                if (flag > 1) {
                    System.out.println("ERRO: encontrada mais do que uma linha não vazia");
                    System.exit(1);
                }
            }
        }
        fInput.close();
        if (!orientado && flagNosJaFoiLido) {
            --nEl;
        }
        return --nEl;
    }

    public static int removerRepetidos(String[][] v, boolean orientado, boolean flagNosJaFoiLido) {
        int nEl = v.length;
        for (int i = 0; i < nEl - 1; i++) {
            for (int j = i + 1; j < nEl; j++) {
                if (Arrays.equals(v[i], v[j])) {
                    if (!flagNosJaFoiLido) {
                        System.out.println("ERRO: Nó repetido (eliminado da estrutura de dados)");
                    }
                    if (flagNosJaFoiLido) {
                        System.out.println("ERRO: Ramo repetido (eliminado da estrutura de dados)");
                    }
                    for (int k = j; k < nEl - 1; k++) {
                        v[k] = v[k + 1];
                    }
                    j--;
                    nEl--;
                }
            }
        }
        if (orientado && flagNosJaFoiLido) {
            nEl--;
        }
        return nEl;
    }

    /**
     * Preenche uma matriz com n colunas e valida a informação repetida
     * automaticamente, avisando para um erro cada vez que ocorre uma repetição
     *
     * @param u
     * @param nColunas
     * @param nomeFich
     * @throws FileNotFoundException
     */
    public static void preencherMatriz(String[][] u, int nColunas, String nomeFich, boolean orientado, boolean flagNosJaFoiLido) throws FileNotFoundException {
        int num = (flagNosJaFoiLido) ? 1 : 0;
        Scanner fInput = new Scanner(new File(nomeFich));
        int cont = 0;
        while (fInput.hasNextLine()) {
            String linha = fInput.nextLine();
            if (cont > 1 + num) {
                u[cont - 2 - num] = linha.split(",");
            }
            cont++;
        }
        fInput.close();
    }

    /**
     * Método para validar nós repetidos
     *
     * @param mat
     * @param nEl
     * @param repetidos
     * @param nElrepetidos
     * @return nElrepetidos
     */
    public static int noRepetido(String[][] mat, int nEl, String[] repetidos, int nElrepetidos) {
        int cont;
        for (int i = 0; i < nEl; i++) {
            cont = numeroDeOcorrenciasDumaStringNumaMatriz(mat[i][0], mat, nEl, 0);
            if (cont > 1) {
                if (nElrepetidos == 0) {
                    repetidos[nElrepetidos] = mat[i][0];
                    nElrepetidos++;
                    System.out.println("ERRO: Nó repetido (eliminado da estrutura de dados)");
                } else {
                    cont = numeroDeOcorrenciasDumaStringNumaArray(mat[i][0], repetidos, nElrepetidos);
                    if (cont == 0) {
                        repetidos[nElrepetidos] = mat[i][0];
                        nElrepetidos++;
                        System.out.println("ERRO: Nó repetido (eliminado da estrutura de dados)");
                    }
                }
            }

        }
        return nElrepetidos;
    }

    /**
     * Cria uma matriz de adjacências e preenche-a com 0's e 1's consoante a
     * matriz dos ramos
     *
     * @param nos
     * @param ramos
     * @param orientado - determina se a matriz é simétrica ou não
     * @return matriz de adjacências
     */
    public static double[][] fazerMatrizAdjac(String[][] nos, String[][] ramos, boolean orientado) {
        double[][] adjac = new double[nos.length][nos.length];
        int from, to;
        for (int i = 0; i < ramos.length; i++) {
            from = procurarStringEmMatriz(ramos[i][0], nos, nos.length, 0);
            to = procurarStringEmMatriz(ramos[i][1], nos, nos.length, 0);
            if (from != -1 && to != -1) {
                if (adjac[from][to] == 0) {
                    adjac[from][to] = Integer.parseInt(ramos[i][2]);
                    if (orientado == false) {
                        adjac[to][from] = adjac[from][to];
                    }
                }
            }
        }
        return adjac;
    }
    // FIM DOS MÉTODOS PARA CRIAÇÃO DAS ESTRUTURAS DE DADOS ATRAVÉS DA LEITURA DOS FICHEIROS

    // MÉTODOS PARA OUTPUT
    public static void cabecalhoMetodo(String metodo) {
        System.out.printf("\nChamado método %s", metodo);
        System.out.println("\nApresentado resultado na consola");
    }

    public static void mostrarResult(String[][] nos, double[] resultado, String aApresentar, boolean casasDecimais) {
        System.out.printf("%n%n%s DAS ENTIDADES%n", aApresentar);
        System.out.println("============================================");
        System.out.printf("%22s%22s%n%n", "ENTIDADE", aApresentar);
        for (int i = 0; i < nos.length; i++) {
            if (casasDecimais == true) {
                System.out.printf("%22s%22.3f%n", nos[i][1], resultado[i]);
            } else {
                System.out.printf("%22s%22.0f%n", nos[i][1], resultado[i]);
            }
        }
        System.out.println();
    }

    // Retorna uma string com os nós para ser impressa
    public static String imprimeMatrizNos(String[][] matriz) {
        String fichNos = String.format("%s%n", "DESCRIÇÃO DAS ENTIDADES")
                + "========================================================================================================================="
                + String.format("%n%15s%22s%22s%22s%40s%n%n", "ID", "ENTIDADE", "TIPO DE ENTIDADE", "GÉNERO", "URL");
        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[0].length; j++) {
                if (j == 0) {
                    fichNos += String.format("%15s", matriz[i][j]);
                } else if (j == SNA.PARAMETROS_FICH_NOS - 1) {
                    fichNos += String.format("%40s%n", matriz[i][j]);
                } else {
                    fichNos += String.format("%22s", matriz[i][j]);
                }
            }
        }
        return fichNos;
    }

    // Retorna uma string com os ramos para ser impressa
    public static String imprimeMatrizRamos(String[][] matriz) {
        String fichRamos = String.format("%n%s%n", "DESCRIÇÃO DOS RELACIONAMENTOS")
                + "============================================="
                + String.format("%n%15s%15s%15s%n%n", "DE", "PARA", "PESO");
        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[0].length; j++) {
                fichRamos += String.format("%15s", matriz[i][j]);
            }
            fichRamos += "\n";
        }
        return fichRamos;
    }

    // Apresenta a rede
    public static void apresentaRede(String[][] nos, String[][] ramos) {
        cabecalhoMetodo("Apresentação da rede");
        System.out.printf("%n%n%s", Utils.imprimeMatrizNos(nos));
        Utils.pausa(false);
        System.out.print(Utils.imprimeMatrizRamos(ramos));
        Utils.pausa(true);
    }

    // Imprime a matriz que tem os vetores intermédios e o vetor Page Rank
    public static String imprimePageRank(String[][] nos, double[][] vetores) {
        String pageRank = String.format("%n%20s", "Nº ITERAÇÕES \\ NÓS");
        for (int i = 0; i < nos.length; i++) {
            pageRank += String.format("%22s", nos[i][1]);
        }
        pageRank += "\n\n";
        for (int i = 0; i < vetores.length; i++) {
            pageRank += String.format("%20d", i);
            for (int j = 0; j < vetores[0].length; j++) {
                pageRank += String.format("%22.3f", vetores[i][j]);
            }
            pageRank += "\n";
        }
        return pageRank;
    }

    /**
     * Recebe como parâmetros os resultados das métricas de uma rede não
     * orientada e escreve-os num ficheiro
     *
     * @throws FileNotFoundException
     */
    public static void escreveFich_redeNaoOrient(String nomeFich, String[][] nos,
            String[][] ramos, double[] graus, double[] centralid, float grauMedio,
            double densidade, String potencAdjac) throws FileNotFoundException {

        // Abre o ficheiro
        Formatter escreve = new Formatter(new File(nomeFich));
        String[] temp = nomeFich.split("_");
        escreve.format("%n%s%s%n%n%n", "ANÁLISE DE REDE SOCIAL NÃO ORIENTADA - ", temp[1]);

        // Imprime os ficheiros de entrada
        escreve.format("%s%n%n", imprimeMatrizNos(nos));
        escreve.format("%s%n%n%n", imprimeMatrizRamos(ramos));

        // Imprime as medidas ao nível dos nós
        escreve.format(cabecalho("MEDIDAS AO NÍVEL DOS NÓS"));
        escreve.format("%n%25s%10s%15s%n%n", "ENTIDADE", "GRAU", "CENTRALIDADE");
        for (int i = 0; i < nos.length; i++) {
            escreve.format("%25s%10.0f%15.3f%n", nos[i][1], graus[i], centralid[i]);
        }

        // Imprime as medidas ao nível da rede
        escreve.format("%n%n%n%s", cabecalho("MEDIDAS AO NÍVEL DA REDE"));
        escreve.format("%n%15s%10s%n%n", "MÉTRICA", "VALOR");
        escreve.format("%15s%10.3f%n%15s%10.3f%n", "Grau médio", grauMedio, "Densidade", densidade);
        escreve.format("%n%n%n%s", cabecalho("POTÊNCIAS DA MATRIZ DE ADJACÊNCIAS"));
        escreve.format("%n%s", potencAdjac);

        // Fecha o ficheiro
        escreve.format(footer());
        escreve.close();
    }

    /**
     * Recebe como parâmetros os resultados das métricas de uma rede orientada e
     * escreve-os num ficheiro
     *
     * @throws FileNotFoundException
     */
    public static void escreveFich_redeOrient(String nomeFich, String[][] nos,
            String[][] ramos, double[] grauEntrada, double[] grauSaida,
            String vetores, double[] centralid, double d) throws FileNotFoundException {

        // Abre o ficheiro
        Formatter escreve = new Formatter(new File(nomeFich));
        String[] temp = nomeFich.split("_");
        escreve.format("%n%s%s%n%n%n", "ANÁLISE DE REDE SOCIAL ORIENTADA - ", temp[1]);

        // Imprime os ficheiros de entrada
        escreve.format("%s%n%n", imprimeMatrizNos(nos));
        escreve.format("%s%n%n%n", imprimeMatrizRamos(ramos));

        // Imprime as medidas ao nível dos nós
        escreve.format(cabecalho("MEDIDAS AO NÍVEL DOS NÓS"));
        escreve.format("%n%25s%15s%15s%n%n", "ENTIDADE", "GRAU ENTRADA", "GRAU SAÍDA");
        for (int i = 0; i < nos.length; i++) {
            escreve.format("%25s%15.0f%15.0f%n", nos[i][1], grauEntrada[i], grauSaida[i]);
        }
        escreve.format(cabecalho("\n\n\nVETOR PAGE RANK OBTIDO PELO ALGORITMO PAGE RANK (E VETORES INTERMÉDIOS)"));
        escreve.format("%n%n%s%.3f%n", "damping factor = ", d);
        escreve.format("%s%n", vetores);
        escreve.format(cabecalho("\n\nVETOR PAGE RANK OBTIDO PELO CÁLCULO DA CENTRALIDADE DA MATRIZ M"));
        escreve.format("%n%25s%15s%n%n", "ENTIDADE", "CENTRALIDADE");
        for (int i = 0; i < nos.length; i++) {
            escreve.format("%25s%15.3f%n", nos[i][1], centralid[i]);
        }

        // Fecha o ficheiro
        escreve.format(footer());
        escreve.close();
    }

    public static String cabecalho(String titulo) {
        String cabec = String.format("%s%n", titulo)
                + String.format("==========================================================================");
        return cabec;
    }

    public static String footer() {
        Date data = new Date();
        String fim = String.format("%n%n%s", "Powered by The Wizards")
                + String.format("%n%s%n", data.toString());
        return fim;
    }
    // FIM DOS MÉTODOS PARA OUTPUT

    // MÉTODOS DIVERSOS
    /**
     * Método de pausa
     *
     * @param mensagemMenu - true = imprime a mensagem do menu; false = imprime
     * a mensagem continuar
     */
    public static void pausa(boolean mensagemMenu) {
        if (mensagemMenu == true) {
            System.out.println("\nPrima a tecla ENTER para voltar ao menu\n");
            in.nextLine();
        } else {
            System.out.println("\nPrima a tecla ENTER para continuar\n");
            in.nextLine();
        }
    }

    /**
     * Imprime um menu recebido como parametro e pede ao utilizador uma opção,
     * validando-a posteriormente
     *
     * @param menu
     * @return a opção escolhida
     */
    public static int validarOpcaoMenu(String menu) {
        int escolha = -1;
        boolean verdFalso;
        do {
            System.out.print(menu);
            String opcao = in.nextLine();
            verdFalso = Utils.verificaNumero(opcao);
            if (verdFalso == true) {
                escolha = Integer.parseInt(opcao);
            } else {
                System.out.println("\n\n\n|===============| ERRO |================|\n"
                        + "|                                       |\n"
                        + "| Por Favor introduza uma opção válida! |\n"
                        + "|                                       |\n"
                        + "|=======================================|");
                Utils.pausa(true);
            }
        } while (verdFalso == false);
        return escolha;
    }

    /**
     * Recebe um double, converte-o para String e acrescenta-lhe N espaços onde
     * N é a constante NUMERO_ALGARISMOS_FORMATACAO - Número de Algarismos do
     * int
     *
     * @param num
     * @return alg
     */
    public static String acrescentaEspacosAoDouble(double num) {
        int auxi = (int) num;
        String alg = Integer.toString(auxi);
        for (int k = alg.length(); k < SNA.NUMERO_ALGARISMOS_FORMATACAO; k++) {
            alg = " " + alg;
        }
        return alg;
    }

    /**
     * Recebe uma matriz e uma String, transforma a matriz numa String formatada
     * e concatena à matriz recebida. Devolve o resultado disso.
     *
     * @param mat
     * @return res
     */
    public static String transformaMatrizEmString(double[][] mat) {
        String res = "";
        for (int i = 0; i < mat.length; i++) {
            for (int j = 0; j < mat[i].length; j++) {
                if (j == 0) {
                    res = res + "[";
                }
                String strCondicionada = (j == mat[i].length - 1) ? "" : ",";
                res = res + acrescentaEspacosAoDouble(mat[i][j]) + strCondicionada;
                if (j == mat[i].length - 1) {
                    res = res + "]";
                }
            }
            res = res + "\n";
        }
        res = res + "\n";
        return res;
    }

    /**
     *
     * @param s - string
     * @param vec - vetor
     * @param nEl - nº de elementos do vetor
     * @return contador
     */
    public static int numeroDeOcorrenciasDumaStringNumaArray(String s, String[] vec, int nEl) {
        int contador = 0;
        for (int i = 0; i < nEl; i++) {
            if (vec[i].equals(s)) {
                contador++;
            }
        }
        return contador;
    }

    /**
     *
     * @param s1 - string 1
     * @param s2 - string 2
     * @param vec - vetor
     * @param nEl - nº de elementos do vetor
     * @return contador
     */
    public static int numeroDeOcorrenciasDumParDeStrings(String s1, String s2, String[][] vec, int nEl) {
        int contador = 0;
        for (int i = 0; i < nEl; i++) {
            if (vec[i][0].equals(s1) && vec[i][1].equals(s2)) {
                contador++;
            }
        }
        System.out.println(contador);
        return contador;
    }

    /**
     *
     * @param s - string
     * @param vec - vetor
     * @param nEl - nº de elementos do vetor
     * @param coluna na qual procurar
     * @return contador
     */
    public static int numeroDeOcorrenciasDumaStringNumaMatriz(String s, String[][] vec, int nEl, int coluna) {
        int contador = 0;
        for (int i = 0; i < nEl; i++) {
            if (vec[i][coluna].equalsIgnoreCase(s)) {
                contador++;
            }
        }
        return contador;
    }

    /**
     *
     * @param elemento
     * @param matriz
     * @param nLinhas
     * @param coluna
     * @return posição do elemento procurado
     */
    public static int procurarStringEmMatriz(String elemento, String[][] matriz, int nLinhas, int coluna) {
        int pos = -1, i = 0;
        while (pos == -1 && i < nLinhas) {
            if (matriz[i][coluna].equalsIgnoreCase(elemento)) {
                pos = i;
            }
            i++;
        }
        return pos;
    }

    /**
     *
     * @param elemento
     * @param vetor
     * @param nLinhas
     * @param coluna
     * @return posição do elemento procurado
     */
    public static int procurarStringEmVetor(String elemento, String[] vetor, int nLinhas, int coluna) {
        int pos = -1, i = 0;
        while (pos == -1 && i < nLinhas) {
            if (vetor[i].equalsIgnoreCase(elemento)) {
                pos = i;
            }
            i++;
        }
        return pos;
    }

    public static int lerInteiros(String mensagem) {
        int valor;
        System.out.println(mensagem);
        valor = SNA.in.nextInt();
        while (valor < 1) {
            System.out.println("\nERRO: Potência não pode ser inferior a 1.");
            System.out.println(mensagem);
            valor = SNA.in.nextInt();
        }
        return valor;
    }

    public static double lerDampingFactor() {
        double valor;
        System.out.println("\nIntroduza o valor da constante d (damping factor) <ex: 0,85> :");
        valor = SNA.in.nextDouble();
        while (valor < 0 || valor > 1) {
            System.out.println("\nERRO: O valor deverá estar entre 0 e 1.");
            System.out.println("\nIntroduza o valor da constante d (damping factor):");
            valor = SNA.in.nextDouble();
        }
        SNA.in.nextLine();
        return valor;
    }

    public static void print(double[][] matriz) {
        for (double[] tabela1 : matriz) {
            System.out.println(Arrays.toString(tabela1));
        }
    }

    public static void printS(String[][] matriz, int nEl) {
        for (int i = 0; i < nEl; i++) {
            System.out.println(Arrays.toString(matriz[i]));
        }
    }

    public static void printMatrizStrings(String[][] u) {
        for (int i = 0; i < u.length; i++) {
            System.out.println(Arrays.toString(u[i]));
        }
    }

    public static void printArray(double[] matriz) {
        System.out.println(Arrays.toString(matriz));
    }

    public static void printArrayS(String[] matriz) {
        System.out.println(Arrays.toString(matriz));
    }

    /**
     * Retorna o nome do ficheiro de output através do nome dos ficheiros de
     * entrada
     *
     * @param nomeFichEntrada
     * @return nome do ficheiro de output
     */
    public static String determinaNomeFichOutput(String nomeFichEntrada) {
        String temp[] = nomeFichEntrada.split("_");
        Calendar hoje = Calendar.getInstance();
        int dia = hoje.get(Calendar.DAY_OF_MONTH);
        int mes = hoje.get(Calendar.MONTH) + 1;
        int ano = hoje.get(Calendar.YEAR);
        String nomeFichOutput = "out_" + temp[1] + "_" + dia + "-" + mes + "-" + ano + ".txt";
        return nomeFichOutput;
    }

    // Cria um vetor double com os dados da última linha de uma matriz passada por parâmetro
    public static double[] criaVetorAPartirUltLinhaMatriz(double[][] matriz) {
        double[] vetor = new double[matriz[0].length];
        for (int i = 0; i < vetor.length; i++) {
            vetor[i] = matriz[matriz.length - 1][i];
        }
        return vetor;
    }
    // FIM DOS MÉTODOS DIVERSOS
}
