package eapli.shopfloor.gestaochaofabrica.domain;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import eapli.shopfloor.general.CodigoAlfaCurto;
import eapli.shopfloor.general.Descricao;

public class DepositoTest {
	
	private static final CodigoAlfaCurto CODIGO_DEP = CodigoAlfaCurto.valueOf("DEPTEST1");
	private static final Descricao DESCR_DEP = Descricao.valueOf("Depósito Teste 1");
	
	public DepositoTest() {
		
	}
	
 	@Test
    public void ensureDepositoComCodigoEDescricao() {
        new Deposito(CODIGO_DEP, DESCR_DEP);
        assertTrue(true);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureDepositoComCodigoNaoNulo() {
        new Deposito(null, DESCR_DEP);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void ensureDepositoComDescricaoNaoNulo() {
        new Deposito(CODIGO_DEP, null);
    }

}
