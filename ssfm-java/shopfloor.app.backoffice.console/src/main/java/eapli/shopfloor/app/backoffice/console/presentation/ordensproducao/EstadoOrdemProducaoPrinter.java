package eapli.shopfloor.app.backoffice.console.presentation.ordensproducao;


import eapli.framework.visitor.Visitor;
import eapli.shopfloor.gestaoproducao.domain.EstadoOrdemProducao;

/**
 *João Pimentel 
 */
public class EstadoOrdemProducaoPrinter implements Visitor<EstadoOrdemProducao> {

    @Override
    public void visit(final EstadoOrdemProducao visitee) {
        System.out.printf("%-5s", visitee.toString());
    }

}
