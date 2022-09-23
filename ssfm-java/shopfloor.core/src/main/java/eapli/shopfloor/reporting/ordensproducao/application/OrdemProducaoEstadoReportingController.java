package eapli.shopfloor.reporting.ordensproducao.application;

import eapli.framework.application.UseCaseController;
import eapli.shopfloor.gestaoproducao.application.ListEstadoOrdemProducaoService;
import eapli.shopfloor.gestaoproducao.domain.EstadoOrdemProducao;
import eapli.shopfloor.infrastructure.persistence.PersistenceContext;
import eapli.shopfloor.reporting.ordensproducao.dto.OrdensProducao;
import eapli.shopfloor.reporting.ordensproducao.repositories.OrdemProducaoReportingRepository;

/**
 * Controller - Consultar Ordens de Produção por Estado
 *
 *João Pimentel 
 */
@UseCaseController
public class OrdemProducaoEstadoReportingController {

    private final OrdemProducaoReportingRepository repository = PersistenceContext.repositories().ordemProducaoReporting();
    private final ListEstadoOrdemProducaoService listEstadosOrdemProducao = new ListEstadoOrdemProducaoService();

    public Iterable<OrdensProducao> reportOrdensProducao() {
        return repository.reportOrdensProducao();
    }

    public Iterable<EstadoOrdemProducao> listEstadoOrdemProducao() {
        return listEstadosOrdemProducao.allEstadosOrdemProducao();
    }

    public Iterable<OrdensProducao> procuraPorEstadoSelecionado(EstadoOrdemProducao estado) {
        return repository.reportOrdensProducao(estado);
    }
}
