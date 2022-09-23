package eapli.shopfloor.app.backoffice.console.presentation.produtos;

import eapli.framework.actions.Action;

/**
 *João Pimentel 
 */
public class AdicionarProdutoAction implements Action {

    @Override
    public boolean execute() { return new AdicionarProdutoUI().show(); }

}
