package eapli.shopfloor.app.backoffice.console.presentation.ordensproducao.reporting;

import eapli.framework.visitor.Visitor;
import eapli.shopfloor.gestaoproducao.domain.Encomenda;
import eapli.shopfloor.gestaoproducao.domain.EncomendaInOrdemProducao;

/**
 * Printer - Consultar Encomendas
 *
 *João Pimentel 
 */
public class EncomendasPrinter implements Visitor<EncomendaInOrdemProducao> {

    @Override
    public void visit(final EncomendaInOrdemProducao visitee) {
        System.out.printf("%-15s", visitee.encomenda().idEncomenda());
    }
}
