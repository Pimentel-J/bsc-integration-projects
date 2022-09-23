package eapli.shopfloor.gestaochaofabrica.repositories;

import eapli.framework.domain.repositories.DomainRepository;
import eapli.shopfloor.general.CodigoAlfaCurto;
import eapli.shopfloor.general.NumeroSerie;
import eapli.shopfloor.gestaochaofabrica.domain.LinhaProducao;
import eapli.shopfloor.gestaochaofabrica.domain.Maquina;

/**
 *Diogo
 */
public interface MaquinaRepository extends DomainRepository<CodigoAlfaCurto, Maquina> {

    Maquina containsMaquina(NumeroSerie idInterno);

    Iterable<Maquina> maquinasByLinhaProducao(LinhaProducao linhaProducao);

    Maquina ultimaPosicaoOcupadaPorLinhaProducao(LinhaProducao linhaProducao);
}
