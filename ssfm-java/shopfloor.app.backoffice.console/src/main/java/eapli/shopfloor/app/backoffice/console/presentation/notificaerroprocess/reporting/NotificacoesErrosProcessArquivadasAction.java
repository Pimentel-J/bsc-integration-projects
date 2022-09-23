package eapli.shopfloor.app.backoffice.console.presentation.notificacoeserroprocessamento.reporting;

import eapli.framework.actions.Action;

/**
 *João Pimentel
 */
public class NotificacoesErrosProcessamentoArquivadasAction implements Action {

    @Override
    public boolean execute() { return new NotificacoesErrosProcessamentoArquivadasUI().show(); }

}
