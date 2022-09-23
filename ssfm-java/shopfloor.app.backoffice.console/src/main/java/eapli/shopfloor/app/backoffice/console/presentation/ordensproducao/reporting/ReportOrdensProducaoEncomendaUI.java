package eapli.shopfloor.app.backoffice.console.presentation.ordensproducao.reporting;

import eapli.framework.presentation.console.AbstractListUI;
import eapli.framework.presentation.console.ListWidget;
import eapli.framework.presentation.console.SelectWidget;
import eapli.framework.visitor.Visitor;
import eapli.shopfloor.gestaoproducao.domain.EncomendaInOrdemProducao;
import eapli.shopfloor.reporting.ordensproducao.application.OrdemProducaoEncomendaReportingController;
import eapli.shopfloor.reporting.ordensproducao.dto.OrdensProducao;

/**
 * Esta classe cria uma lista das ordens de produção de uma encomenda selecionada
 *
 *João Pimentel 
 */
public class ReportOrdensProducaoEncomendaUI extends AbstractListUI<OrdensProducao> {

    private final OrdemProducaoEncomendaReportingController controller =
            new OrdemProducaoEncomendaReportingController();

    @Override
    protected Iterable<OrdensProducao> elements() { return this.controller.reportOrdensProducao(); }

    @Override
    protected Visitor<OrdensProducao> elementPrinter() { return new OrdensProducaoPendentePrinter(); }

    @Override
    protected String elementName() {
        return "Ordem Produção";
    }

    @Override
    protected String listHeader() {
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

        EncomendaInOrdemProducao selectedElement = selectEncomendas();
        Iterable<OrdensProducao> elems = this.controller.procuraPorEncomendaSelecionada(selectedElement);

        if (!elems.iterator().hasNext()) {
            System.out.println("Não existem ordens de produção!");
        } else {
            new ListWidget(this.listHeader(), elems, this.elementPrinter()).show();
        }
        return true;
    }

    private EncomendaInOrdemProducao selectEncomendas() {
        System.out.println("CONSULTAR ORDENS DE PRODUÇÃO");
        final Iterable<EncomendaInOrdemProducao> listEncomendas = controller.listEncomendas();
        final SelectWidget<EncomendaInOrdemProducao> selectorEncomendas = new SelectWidget<>("Selecione uma " +
                "Encomenda", listEncomendas, new EncomendasPrinter());
        selectorEncomendas.show();
        return selectorEncomendas.selectedElement();
    }

}
