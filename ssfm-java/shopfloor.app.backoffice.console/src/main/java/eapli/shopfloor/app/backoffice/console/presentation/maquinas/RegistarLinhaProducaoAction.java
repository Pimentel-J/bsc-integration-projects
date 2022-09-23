package eapli.shopfloor.app.backoffice.console.presentation.maquinas;

import eapli.framework.actions.Action;

/**
 *João Pimentel 
 */
public class RegistarLinhaProducaoAction implements Action {

    @Override
    public boolean execute() { return new RegistarLinhaProducaoUI().show(); }

}
