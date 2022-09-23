package eapli.shopfloor.app.backoffice.console.presentation.produtos;

import eapli.framework.actions.Action;

/**
 *João Pimentel 
 */
public class ImportarProdutosAction implements Action {

    @Override
    public boolean execute() { return new ImportarProdutosUI().show(); }

}
