package eapli.shopfloor.app.backoffice.console.presentation.maquinas;

import eapli.framework.visitor.Visitor;
import eapli.shopfloor.gestaochaofabrica.domain.LinhaProducao;




    /**
     *Diogo
     */
    public class LinhaProducaoPrinter implements Visitor<LinhaProducao> {

        @Override
        public void visit(final LinhaProducao visitee) {
            System.out.printf("%-5s", visitee.identity());
        }
    }

