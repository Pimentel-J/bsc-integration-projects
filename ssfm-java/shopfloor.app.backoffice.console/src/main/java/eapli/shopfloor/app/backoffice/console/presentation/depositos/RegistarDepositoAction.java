package eapli.shopfloor.app.backoffice.console.presentation.depositos;

import eapli.framework.actions.Action;

/**
 *João Pimentel 
 */
public class RegistarDepositoAction implements Action {

    @Override
    public boolean execute() { return new RegistarDepositoUI().show(); }

}
