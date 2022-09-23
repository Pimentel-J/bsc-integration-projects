package eapli.shopfloor.reporting.ordensproducao.repositories;

import eapli.framework.domain.repositories.ReportingRepository;
import eapli.shopfloor.gestaoproducao.domain.Encomenda;
import eapli.shopfloor.gestaoproducao.domain.EncomendaInOrdemProducao;
import eapli.shopfloor.gestaoproducao.domain.EstadoOrdemProducao;
import eapli.shopfloor.reporting.ordensproducao.dto.OrdensProducao;

/**
 * Repository - Consultar Ordens de Produção
 *
 *João Pimentel 
 */
@ReportingRepository
public interface OrdemProducaoReportingRepository {

    Iterable<OrdensProducao> reportOrdensProducao();

    Iterable<OrdensProducao> reportOrdensProducao(EstadoOrdemProducao estado);

    Iterable<OrdensProducao> reportOrdensProducao(EncomendaInOrdemProducao encomenda);

}
