package eapli.shopfloor.gestaoproducao.domain;

import eapli.shopfloor.general.CodigoAlfaCurto;
import eapli.shopfloor.general.CodigoAlfaLongo;
import eapli.shopfloor.general.Descricao;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 *João Pimentel 
 */
public class MaterialTest {

    private CategoriaMaterial categoria = new CategoriaMaterial(CodigoAlfaCurto.valueOf("mt"),
            Descricao.valueOf("metal"));
    private FichaTecnicaMaterial ficha = new FichaTecnicaMaterial("fmetal", "files");
    private Material instance = new Material(CodigoAlfaLongo.valueOf("mtl"), Descricao.valueOf("ferro"),
            ficha, categoria);
    private CodigoAlfaLongo instanceCodigo = CodigoAlfaLongo.valueOf("cr");
    private Descricao instanceDescricao = Descricao.valueOf("carvalho");

    @Test(expected = IllegalArgumentException.class)
    public void testCodigoMaterialNotEmpty() {
        System.out.println("Código de material não pode ser vazio");
        new Material(CodigoAlfaLongo.valueOf(""), instanceDescricao, ficha, categoria);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCodigoMaterialNotNull() {
        System.out.println("Código de material não pode ser nulo");
        new Material(null, instanceDescricao, ficha, categoria);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCodigoCategoriaMaterialCharLengthLimit() {
        System.out.println("Código de material tem no máximo 15 caracteres");
        new Material(CodigoAlfaLongo.valueOf("madeirapinhobravo"), instanceDescricao, ficha, categoria);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDescricaoMaterialNotEmpty() {
        System.out.println("Descrição de material não pode ser vazio");
        new Material(instanceCodigo, Descricao.valueOf(""), ficha, categoria);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDescricaoMaterialNotNull() {
        System.out.println("Descrição de material não pode ser nulo");
        new Material(instanceCodigo, null, ficha, categoria);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFichaTecnicaMaterialNotNull() {
        System.out.println("Ficha técnica de material não pode ser nulo");
        new Material(instanceCodigo, instanceDescricao, null, categoria);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCatgoriaMaterialNotNull() {
        System.out.println("Categoria de material não pode ser nulo");
        new Material(instanceCodigo, instanceDescricao, ficha, null);
    }

    @Test
    public void testIdentity() {
        System.out.println("identity");
        assertEquals(CodigoAlfaLongo.valueOf("mtl"), instance.identity());
    }

    @Test
    public void testDescricaoMaterial() {
        System.out.println("descricao");
        assertEquals(Descricao.valueOf("ferro"), instance.descricaoMaterial());
    }

    @Test
    public void testCategoriaMaterial() {
        System.out.println("categoriaMaterial");
        assertEquals(categoria, instance.categoriaMaterial());
    }

    @Test
    public void fichaTecnicaMaterial() {
        System.out.println("fichaTecnica");
        assertEquals(ficha, instance.fichaTecnicaMaterial());
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