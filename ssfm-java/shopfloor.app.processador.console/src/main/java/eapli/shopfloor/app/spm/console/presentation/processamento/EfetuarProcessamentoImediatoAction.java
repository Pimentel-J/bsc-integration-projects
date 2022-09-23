package eapli.shopfloor.app.spm.console.presentation.processamento;

import eapli.framework.actions.Action;

/**
 *João Pimentel 
 */
public class EfetuarProcessamentoImediatoAction implements Action {

    @Override
    public boolean execute() { return new EfetuarProcessamentoImediatoUI().show(); }
}
