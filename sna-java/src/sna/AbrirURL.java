package sna;

import java.awt.Desktop;
import java.net.URI;

/**
 * @author diogo
 */
public class AbrirURL {

    /* Este método recebe a matriz com a informação dos nós, 
    assim como o vetor da variável a ser medida e abre o URL 
    do nó com o valor da variável mais alto  */
    public static void abrirURLdaMatriz(String[][] nos, double[] variavel) {
        System.out.println("\n\nA página web do nó mais popular será aberta no browser predefinido.\n");
        int k = obterIndexMaior(variavel);
        abreURL(nos[k][4].trim());
    }

    /* Este método recebe o vetor da variável e devolve 
    o índice do nó com o valor mais alto */
    public static int obterIndexMaior(double[] variavel) {
        double maior = 0;
        int index = -1;
        for (int i = 0; i < variavel.length; i++) {
            if (variavel[i] > maior) {
                maior = variavel[i];
                index = i;
            }

        }
        return index;
    }

    /* Este método é a adaptação do método fornecido 
    pelos professores através do moodle */
   
    public static void abreURL(String webS) {

        try {
            Desktop desktop = java.awt.Desktop.getDesktop();
            URI oURL = new URI(webS);
            desktop.browse(oURL);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /*potenciais variáveis para teste
    String [][] nosTeste = {{a,a,a,www.google.com}{b,b,b,www.samsung.com}{c,c,c,www.microsoft.com}{d,d,d,www.ikea.com}};
    double[] variaveisTeste{3.567,2.326,5.789,1.765};
    OUTCOME ESPERADO : ABRIR PÀGINA DA MICROSOFT
    */
}
