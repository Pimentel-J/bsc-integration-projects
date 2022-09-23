package eapli.shopfloor.app.backoffice.console.presentation.notificacoeserroprocessamento;

import eapli.framework.actions.Action;

/**
 *João Pimentel 
 */
public class ArquivarNotificacoesErroProcessAction implements Action {

    @Override
    public boolean execute() { return new ArquivarNotificacoesErroProcessUI().show(); }

}
