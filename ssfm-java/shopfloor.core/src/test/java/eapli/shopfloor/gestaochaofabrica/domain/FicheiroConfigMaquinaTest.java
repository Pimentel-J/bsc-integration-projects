package eapli.shopfloor.gestaochaofabrica.domain;

import eapli.shopfloor.general.CodigoAlfaLongo;
import eapli.shopfloor.gestaoproducao.domain.Material;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 *João Pimentel 
 */
public class FicheiroConfigMaquinaTest {

    private String name = "testName";
    private String content = "testContent";
    FicheiroConfigMaquina instance = new FicheiroConfigMaquina(name, content);

    @Test(expected = IllegalArgumentException.class)
    public void testNomeFicheiroConfigMaquinaNotEmpty() {
        System.out.println("Nome do ficheiro não pode ser vazio");
        new FicheiroConfigMaquina("", content);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNomeFicheiroConfigMaquinaNotNull() {
        System.out.println("Nome do ficheiro não pode ser nulo");
        new FicheiroConfigMaquina(null, content);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConteudoFicheiroConfigMaquinaNotEmpty() {
        System.out.println("Conteúdo do ficheiro não pode ser vazio");
        new FicheiroConfigMaquina(name, "");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConteudoFicheiroConfigMaquinaNotNull() {
        System.out.println("Conteúdo do ficheiro não pode ser nulo");
        new FicheiroConfigMaquina(name, null);
    }

    @Test
    public void testEquals() {
        System.out.println("equals");
        assertTrue(instance.equals(instance));
    }

    @Ignore
    public void testHashCode() {
    }

    @Ignore
    public void testToString() {
    }
}