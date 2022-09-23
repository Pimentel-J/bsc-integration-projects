package eapli.shopfloor.app.backoffice.console.presentation.materiais;

import eapli.framework.actions.Action;

/**
 *João Pimentel 
 */
public class RegistarCategoriaMaterialAction implements Action {

    @Override
    public boolean execute() { return new RegistarCategoriaMaterialUI().show(); }
}
