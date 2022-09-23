package eapli.shopfloor.gestaoproducao.domain;

import eapli.shopfloor.general.CodigoAlfaCurto;
import eapli.shopfloor.general.Descricao;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 *João Pimentel 
 */
public class CategoriaMaterialTest {

    private CategoriaMaterial instance = new CategoriaMaterial(CodigoAlfaCurto.valueOf("pl"),
            Descricao.valueOf("plastico"));
    private CodigoAlfaCurto instanceCodigo = CodigoAlfaCurto.valueOf("md");
    private Descricao instanceDescricao = Descricao.valueOf("madeira");

    @Test(expected = IllegalArgumentException.class)
    public void testCodigoCategoriaMaterialNotEmpty() {
        System.out.println("Código de categoria não pode ser vazio");
        new CategoriaMaterial(CodigoAlfaCurto.valueOf(""), instanceDescricao);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCodigoCategoriaMaterialNotNull() {
        System.out.println("Código de categoria não pode ser nulo");
        new CategoriaMaterial(null, instanceDescricao);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCodigoCategoriaMaterialCharLengthLimit() {
        System.out.println("Código de categoria tem no máximo 10 caracteres");
        new CategoriaMaterial(CodigoAlfaCurto.valueOf("madeirapinho"), instanceDescricao);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDescricaoCategoriaMaterialNotEmpty() {
        System.out.println("Descrição de categoria não pode ser vazio");
        new CategoriaMaterial(instanceCodigo, Descricao.valueOf(""));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDescricaoCategoriaMaterialNotNull() {
        System.out.println("Descrição de categoria não pode ser nulo");
        new CategoriaMaterial(instanceCodigo, null);
    }

    @Test
    public void testIdentity() {
        System.out.println("identity");
        assertEquals(CodigoAlfaCurto.valueOf("pl"), instance.identity());
    }

    @Test
    public void testDescricao() {
        System.out.println("descrição");
        assertEquals(Descricao.valueOf("plastico"), instance.descricao());
    }

    @Test
    public void testSameAs() {
        System.out.println("sameAs");
        assertTrue(instance.sameAs(instance));
    }

    @Ignore
    public void testHashCode() {
    }

    @Ignore
    public void testToString() {
    }
}