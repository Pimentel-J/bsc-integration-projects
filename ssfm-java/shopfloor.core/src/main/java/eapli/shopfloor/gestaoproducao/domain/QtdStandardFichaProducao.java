package eapli.shopfloor.gestaoproducao.domain;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import eapli.framework.domain.model.ValueObject;
import eapli.framework.validations.NumberPredicates;

public class QtdStandardFichaProducao implements ValueObject{

	private static final long serialVersionUID = 1L;
	
	private float quantidade;
	@Enumerated(EnumType.STRING)
    private Unidade unidade;
    
    protected QtdStandardFichaProducao() {
        // for ORM
    }

    public QtdStandardFichaProducao(final int quantidade, final Unidade unidade) {
        if (!NumberPredicates.isNonNegative(quantidade)) {
            throw new IllegalArgumentException(
                    "A quantidade deve ser um número >= 0");
        }
        this.quantidade = quantidade;
        this.unidade = unidade;
    }

    public float quantidade() {
        return quantidade;
    }

    public Unidade unidade() {
        return unidade;
    }
	
}
