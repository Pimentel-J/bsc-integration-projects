package eapli.shopfloor.app.backoffice.console.presentation.maquinas;

import eapli.framework.actions.Action;

/**
 *João Pimentel 
 */
public class ImportarConfigMaquinaAction implements Action {

    @Override
    public boolean execute() { return new ImportarConfigMaquinaUI().show(); }
}
