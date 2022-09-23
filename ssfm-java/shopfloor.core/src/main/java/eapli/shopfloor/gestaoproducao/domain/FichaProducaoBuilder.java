package eapli.shopfloor.gestaoproducao.domain;

import java.util.HashSet;
import java.util.Set;

import eapli.framework.domain.model.DomainFactory;

public class FichaProducaoBuilder implements DomainFactory<FichaProducao>{

	private QtdStandardFichaProducao quantidadeStandard;
    private final Set<MateriaPrima> materiasPrimas = new HashSet<>();
    
    public FichaProducaoBuilder withQuantidadeStandard(QtdStandardFichaProducao quantidade) {
    	this.quantidadeStandard = quantidade;
    	return this;
    }
    
    public FichaProducaoBuilder withMateriaPrima(MateriaPrima materiaPrima) {
    	this.materiasPrimas.add(materiaPrima);
    	return this;
    }
	
	@Override
	public FichaProducao build() {
		return new FichaProducao(this.quantidadeStandard, this.materiasPrimas);
	}

}
