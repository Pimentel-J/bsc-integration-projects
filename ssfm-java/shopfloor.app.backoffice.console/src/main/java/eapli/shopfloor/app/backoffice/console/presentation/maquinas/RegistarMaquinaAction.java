package eapli.shopfloor.app.backoffice.console.presentation.maquinas;

import eapli.framework.actions.Action;

/**
 *João Pimentel 
 */
public class RegistarMaquinaAction implements Action {

    @Override
    public boolean execute() { return new RegistarMaquinaUI().show(); }
}
