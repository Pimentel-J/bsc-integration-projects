package eapli.shopfloor.app.backoffice.console.presentation.maquinas;

import eapli.framework.visitor.Visitor;
import eapli.shopfloor.gestaochaofabrica.domain.Maquina;


/**
 *Diogo
 */
public class SequentialPrinter implements Visitor<Maquina> {

    @Override
    public void visit(final Maquina visitee) {
        System.out.printf("%-6s%-15s", "", visitee.identity());
    }
}
