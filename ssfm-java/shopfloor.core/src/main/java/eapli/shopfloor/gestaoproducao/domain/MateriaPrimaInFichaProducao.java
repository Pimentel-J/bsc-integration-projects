package eapli.shopfloor.gestaoproducao.domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;

import eapli.framework.domain.model.ValueObject;
import eapli.framework.validations.Preconditions;

@Embeddable
@Access(AccessType.FIELD)
public class MateriaPrimaInFichaProducao implements ValueObject{

	private static final long serialVersionUID = 1L;
	
	@Embedded
	private final MateriaPrima materiaPrima;
	
	protected MateriaPrimaInFichaProducao() {
		// ORM
		materiaPrima = null;
	}
	
	public MateriaPrimaInFichaProducao(final MateriaPrima materiaPrima) {
		Preconditions.nonNull(materiaPrima);
		this.materiaPrima = materiaPrima;
	}
	

}
