package eapli.shopfloor.reporting.ordensproducao.application;

import eapli.framework.application.UseCaseController;
import eapli.shopfloor.gestaoproducao.application.ListEncomendaService;
import eapli.shopfloor.gestaoproducao.application.ListEstadoOrdemProducaoService;
import eapli.shopfloor.gestaoproducao.domain.Encomenda;
import eapli.shopfloor.gestaoproducao.domain.EncomendaInOrdemProducao;
import eapli.shopfloor.gestaoproducao.domain.EstadoOrdemProducao;
import eapli.shopfloor.infrastructure.persistence.PersistenceContext;
import eapli.shopfloor.reporting.ordensproducao.dto.OrdensProducao;
import eapli.shopfloor.reporting.ordensproducao.repositories.OrdemProducaoReportingRepository;

/**
 * Controller - Consultar Ordens de Produção por Encomenda
 *
 *João Pimentel 
 */
@UseCaseController
public class OrdemProducaoEncomendaReportingController {

    private final OrdemProducaoReportingRepository repository = PersistenceContext.repositories().ordemProducaoReporting();
    private final ListEncomendaService listEncomendasService = new ListEncomendaService();

    public Iterable<OrdensProducao> reportOrdensProducao() {
        return repository.reportOrdensProducao();
    }

    public Iterable<EncomendaInOrdemProducao> listEncomendas() {
        return listEncomendasService.allEncomendas();
    }

    public Iterable<OrdensProducao> procuraPorEncomendaSelecionada(EncomendaInOrdemProducao encomenda) {
        return repository.reportOrdensProducao(encomenda);
    }
}
