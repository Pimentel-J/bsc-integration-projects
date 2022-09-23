package eapli.shopfloor.app.backoffice.console.presentation.notificacoeserroprocessamento.reporting;

import eapli.framework.actions.Action;

/**
 *João Pimentel [1161874]
 */
public class NotificacoesErrosProcessamentoAtivasAction implements Action {

    @Override
    public boolean execute() { return new NotificacoesErrosProcessamentoAtivasUI().show(); }

}
