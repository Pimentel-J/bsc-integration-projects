package eapli.shopfloor.gestaoproducao.domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;

import eapli.framework.domain.model.ValueObject;

@Embeddable
@Access(AccessType.FIELD)
public class QtdMovimento implements ValueObject{

	private static final long serialVersionUID = 1L;
	private float quantidade;
	
    
    protected QtdMovimento() {
        // for ORM
    }
    
    public QtdMovimento(final float quantidade) {
        if (quantidade < 0) {
            throw new IllegalArgumentException(
                    "A quantidade deve ser um número >= 0");
        }
        this.quantidade = quantidade;
    }
    
    public float quantidade() {
    	return this.quantidade;
    }
    
    public static QtdMovimento valueOf(final float quantidade) {
        return new QtdMovimento(quantidade);
    }
	
}
