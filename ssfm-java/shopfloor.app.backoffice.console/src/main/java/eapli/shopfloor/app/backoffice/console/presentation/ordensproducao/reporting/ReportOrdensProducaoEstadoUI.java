package eapli.shopfloor.app.backoffice.console.presentation.ordensproducao.reporting;

import eapli.framework.presentation.console.AbstractListUI;
import eapli.framework.presentation.console.ListWidget;
import eapli.framework.presentation.console.SelectWidget;
import eapli.framework.visitor.Visitor;
import eapli.shopfloor.app.backoffice.console.presentation.ordensproducao.EstadoOrdemProducaoPrinter;
import eapli.shopfloor.gestaoproducao.domain.EstadoOrdemProducao;
import eapli.shopfloor.reporting.ordensproducao.application.OrdemProducaoEstadoReportingController;
import eapli.shopfloor.reporting.ordensproducao.dto.OrdensProducao;

/**
 * Esta classe cria uma lista das ordens de produção com um estado selecionado
 *
 *João Pimentel 
 */
public class ReportOrdensProducaoEstadoUI extends AbstractListUI<OrdensProducao> {

    private final OrdemProducaoEstadoReportingController controller = new OrdemProducaoEstadoReportingController();

    @Override
    protected Iterable<OrdensProducao> elements() { return this.controller.reportOrdensProducao(); }

    @Override
    protected Visitor<OrdensProducao> elementPrinter() {
        return new OrdensProducaoPrinter();
    }

    protected Visitor<OrdensProducao> elementPendentePrinter() {
        return new OrdensProducaoPendentePrinter();
    }

    @Override
    protected String elementName() {
        return "Ordem Produção";
    }

    @Override
    protected String listHeader() {
        return "ORDENS DE PRODUÇÃO\n" + String.format("%3s%-15s%-15s%-20s%-15s%-15s%-15s%-15s%-12s%-35s",
                "", "Código", "Data Emissão", "Data Prev. Exec.", "Produto", "Cód. Fabrico", "Quantidade",
                "Linha Prod.", "Execução", "Estado");
    }

    protected String listPendenteHeader() {
        return "ORDENS DE PRODUÇÃO\n" + String.format("%3s%-15s%-15s%-20s%-15s%-15s%-15s%-35s",
                "", "Código", "Data Emissão", "Data Prev. Exec.", "Produto", "Cód. Fabrico", "Quantidade", "Estado");
    }

    @Override
    protected String emptyMessage() {
        return "Sem data!";
    }

    @Override
    public String headline() {
        return "REPORT";
    }

    @Override
    protected boolean doShow() {

        EstadoOrdemProducao selectedElement = selectEstadosOrdemProducao();
        Iterable<OrdensProducao> elems = this.controller.procuraPorEstadoSelecionado(selectedElement);

        if (!elems.iterator().hasNext()) {
            System.out.println("Não existem Ordens de produção com o estado selecionado!");
        } else {
            if (selectedElement.equals(EstadoOrdemProducao.PENDENTE)) {
                new ListWidget(this.listPendenteHeader(), elems, this.elementPendentePrinter()).show();
            } else {
                new ListWidget(this.listHeader(), elems, this.elementPrinter()).show();
            }
        }
        return true;
    }

    private EstadoOrdemProducao selectEstadosOrdemProducao() {
        System.out.println("CONSULTAR ORDENS DE PRODUÇÃO");
        final Iterable<EstadoOrdemProducao> listEstadoOrdemProducao = controller.listEstadoOrdemProducao();
        final SelectWidget<EstadoOrdemProducao> selectorEstadoOrdemProducao = new SelectWidget<>(
                "Selecione um Estado de Ordem de Produção", listEstadoOrdemProducao, new EstadoOrdemProducaoPrinter());
        selectorEstadoOrdemProducao.show();
        return selectorEstadoOrdemProducao.selectedElement();
    }

}
