package eapli.shopfloor.app.backoffice.console.presentation.produtos;

import eapli.framework.actions.Action;

/**
 *João Pimentel 
 */
public class RegistarFichaProducaoAction implements Action {

    @Override
    public boolean execute() { return new RegistarFichaProducaoUI().show(); }

}
