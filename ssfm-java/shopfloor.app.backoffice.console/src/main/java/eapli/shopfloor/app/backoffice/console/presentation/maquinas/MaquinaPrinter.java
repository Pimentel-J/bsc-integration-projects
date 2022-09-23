package eapli.shopfloor.app.backoffice.console.presentation.maquinas;

import eapli.framework.visitor.Visitor;
import eapli.shopfloor.gestaochaofabrica.domain.Maquina;

/**
 *João Pimentel 
 */
public class MaquinaPrinter implements Visitor<Maquina> {

    @Override
    public void visit(final Maquina visitee) {
        System.out.printf("%-5s", visitee.identity());
    }
}
