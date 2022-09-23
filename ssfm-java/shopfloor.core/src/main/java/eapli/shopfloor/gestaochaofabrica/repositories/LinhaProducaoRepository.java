package eapli.shopfloor.gestaochaofabrica.repositories;

import eapli.framework.domain.repositories.DomainRepository;
import eapli.shopfloor.general.CodigoAlfaCurto;
import eapli.shopfloor.gestaochaofabrica.domain.LinhaProducao;


public interface LinhaProducaoRepository extends DomainRepository<CodigoAlfaCurto, LinhaProducao> {

	Iterable<LinhaProducao> linhasProducaoComProcessamentoAtivo();
}
