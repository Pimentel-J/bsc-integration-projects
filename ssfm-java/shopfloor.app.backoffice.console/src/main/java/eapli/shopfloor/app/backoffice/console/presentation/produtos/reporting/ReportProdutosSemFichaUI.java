package eapli.shopfloor.app.backoffice.console.presentation.produtos.reporting;

import eapli.framework.presentation.console.AbstractListUI;
import eapli.framework.visitor.Visitor;
import eapli.shopfloor.reporting.produtos.application.ProdutoReportingController;
import eapli.shopfloor.reporting.produtos.dto.ProdutosSemFicha;

/**
 * Esta classe cria uma lista dos produtos sem ficha de produção
 *
 *João Pimentel 
 */
public class ReportProdutosSemFichaUI extends AbstractListUI<ProdutosSemFicha> {

    private final ProdutoReportingController controller = new ProdutoReportingController();

    @Override
    protected Iterable<ProdutosSemFicha> elements() {
        return this.controller.reportProdutosSemFicha();
    }

    @Override
    protected Visitor<ProdutosSemFicha> elementPrinter() {
        return new ProdutosSemFichaPrinter();
    }

    @Override
    protected String elementName() {
        return "Ficha Produção";
    }

    @Override
    protected String listHeader() {
        return "PRODUTOS SEM FICHA PRODUÇÃO\n" + String.format("%3s%-15s%-10s", "", "Código", "Descrição");
    }

    @Override
    protected String emptyMessage() {
        return "Sem data!";
    }

    @Override
    public String headline() {
        return "REPORT";
    }

}
