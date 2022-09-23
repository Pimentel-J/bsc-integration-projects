package eapli.shopfloor.gestaoproducao.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Version;

import eapli.framework.domain.model.DomainEntity;
import eapli.framework.validations.Preconditions;

/**
 *pmagalhaes
 *
 */
@Entity
public class FichaProducao implements DomainEntity<Long>{

	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue
    private Long id;
    @Version
    private Long version;
	
    @Embedded
    private QtdStandardFichaProducao quantidadeStandard;
    
    @ElementCollection
    private final Set<MateriaPrimaInFichaProducao> materiasPrimas = new HashSet<>();
    
    protected FichaProducao () {
    	// ORM
    }

    public FichaProducao (final QtdStandardFichaProducao quantidadeStandard, final Set<MateriaPrima> materiasPrimas) {
    	Preconditions.nonEmpty(materiasPrimas);
    	Preconditions.noneNull(quantidadeStandard);
    	this.quantidadeStandard = quantidadeStandard;
    	adicionarMateriasPrimas(materiasPrimas);
    }


    public QtdStandardFichaProducao quantidadeStandard() {
        return quantidadeStandard;
    }

    public Set<MateriaPrimaInFichaProducao> materiasPrimas() {
        return materiasPrimas;
    }

    private void adicionarMateriasPrimas(Set<MateriaPrima> materiasPrimas) {
    	for (final MateriaPrima materiaPrima : materiasPrimas) {
            adicionarMateriaPrima(materiaPrima);
        }
    }
    
    private void adicionarMateriaPrima(MateriaPrima materiaPrima) {
    	MateriaPrimaInFichaProducao mpInFichaProducao = new MateriaPrimaInFichaProducao(materiaPrima);
    	materiasPrimas.add(mpInFichaProducao);
    }
    
	@Override
	public Long identity() {
		return this.id;
	}

	@Override
	public boolean sameAs(Object other) {
		if (!(other instanceof FichaProducao)) {
            return false;
        }

        final FichaProducao that = (FichaProducao) other;
        if (this == that) {
            return true;
        }

        return identity().equals(that.identity());
        // FIXME compare other fields
	}

}
