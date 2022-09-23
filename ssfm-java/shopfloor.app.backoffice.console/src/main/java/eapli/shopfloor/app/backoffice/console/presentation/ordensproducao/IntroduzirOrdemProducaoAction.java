package eapli.shopfloor.app.backoffice.console.presentation.ordensproducao;

import eapli.framework.actions.Action;

/**
 *João Pimentel 
 */
public class IntroduzirOrdemProducaoAction implements Action {

    @Override
    public boolean execute() { return new IntroduzirOrdemProducaoUI().show(); }

}
