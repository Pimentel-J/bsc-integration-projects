package eapli.shopfloor.gestaoproducao.domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import eapli.framework.domain.model.ValueObject;

@Embeddable
@Access(AccessType.FIELD)
public class QuantidadeMateriaPrima implements ValueObject {

	private static final long serialVersionUID = 1L;
	
	private float quantidade;
	@Enumerated(EnumType.STRING)
    private Unidade unidade;
    
    protected QuantidadeMateriaPrima() {
        // for ORM
    }
    
    public QuantidadeMateriaPrima(final float quantidade, final Unidade unidade) {
        if (quantidade < 0) {
            throw new IllegalArgumentException(
                    "A quantidade deve ser um número >= 0");
        }
        this.quantidade = quantidade;
        this.unidade = unidade;
    }
    
}
