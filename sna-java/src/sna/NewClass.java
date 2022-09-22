/*
 */
package sna;

//import javax.swing.JOptionPane;

/**
 *
 * @author ASUS
 */
public class NewClass {

    public static void main(String[] args) {
        int x = 1, i = 1;
        while (x < 1000) {
            x = (int) Math.pow(2, x);
            i = i + 1;
        }
        System.out.println("i = " + i);

        int[][] vec1 = {{1, 2, 3}, {4, 5, 6}};
        int[][] vec2 = vec1;
        vec2[1][1] = 7;
        System.out.println("vec1[1][1] = " + vec1[1][1] + " vec2[1][1] = " + vec2[1][1]);
        System.out.println("linhas:" + vec1.length + " colunas:" + vec1[0].length);

        double perc = 25.6;
        System.out.println("A percentagem é de " + (int) (perc + 1.1) + "%");

        int[][] asd = {{1, 2}, {3, 4}, {5, 6}};
        int a = 1;
        int b = 2;
        test(asd, a, b);
        System.out.println("a=" + a + " b=" + b + " asd=" + asd[1][1]);
        
        int num = mods();
        System.out.println(num);
    }

    public static void test(int[][] asd, int a, int b) {
        a = a + 1;
        b = b + 2;
        asd[1][1] = a + b;
    }

    public static int mods() {
        int novo = 0;
        int num = 123456;

        do {
            int alg = num % 10;
            novo = novo * 10 + alg;
            num = num / 10;
        } while (num != 0);
        return novo;
    }
}
