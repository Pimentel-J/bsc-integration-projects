package eapli.shopfloor.app.backoffice.console.presentation.materiais;

import eapli.shopfloor.gestaoproducao.domain.CategoriaMaterial;
import eapli.framework.visitor.Visitor;

/**
 *João Pimentel 
 */
public class CategoriaMaterialPrinter implements Visitor<CategoriaMaterial>{

    @Override
    public void visit(final CategoriaMaterial visitee) {
        System.out.printf("%-5s%-25s", visitee.identity(), visitee.descricao());
    }
}
