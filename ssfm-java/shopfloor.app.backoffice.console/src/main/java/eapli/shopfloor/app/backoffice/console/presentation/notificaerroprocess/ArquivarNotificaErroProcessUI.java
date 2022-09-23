package eapli.shopfloor.app.backoffice.console.presentation.notificacoeserroprocessamento;

import eapli.framework.domain.repositories.ConcurrencyException;
import eapli.framework.domain.repositories.IntegrityViolationException;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.presentation.console.SelectWidget;
import eapli.shopfloor.SPM.application.ArquivarNotificacoesErroProcessController;
import eapli.shopfloor.SPM.domain.NotificacaoErroProcessamento;
import eapli.shopfloor.app.backoffice.console.presentation.MainMenu;
import eapli.shopfloor.app.backoffice.console.presentation.notificacoeserroprocessamento.reporting.NotificacoesErrosProcessamentoPrinter;

/**
 * Esta classe permite arquivar as notificações de erros de processamentos ativas
 *
 *João Pimentel 
 */
public class ArquivarNotificacoesErroProcessUI extends AbstractUI {

    private final ArquivarNotificacoesErroProcessController controller =
            new ArquivarNotificacoesErroProcessController();

    @Override
    protected boolean doShow() {

        final NotificacaoErroProcessamento notificacao = selectNotificacaoErroProcessamento();

        try {
            controller.arquivarNotificacaoErroProcess(notificacao);
            System.out.println("Notificação arquivada com sucesso!");
        } catch (ConcurrencyException | IntegrityViolationException ex) {
            if (ex instanceof ConcurrencyException) {
                System.out.println("WARNING: That entity has already been changed or deleted since you last read it");
            } else {
                System.out.println("Não existem notificações");
            }
        }
        return false;
    }

    private NotificacaoErroProcessamento selectNotificacaoErroProcessamento() {
        final Iterable<NotificacaoErroProcessamento> listNotificacoesErroAtivas =
                controller.listNotificacoesErroAtivas();
        final SelectWidget<NotificacaoErroProcessamento> selectorNotificacaoErroProcessamento =
                new SelectWidget<>(listHeader(), listNotificacoesErroAtivas,
                        new NotificacaoErroProcessamentoPrinter());
        selectorNotificacaoErroProcessamento.show();
        return selectorNotificacaoErroProcessamento.selectedElement();
    }

    @Override
    public String headline() {
        return "Arquivar Notificações de Erros de Processamento";
    }

    protected String listHeader() {
        return "### Selecione uma Notificação ###\n" + "--------------------------------\n" +
                String.format("%-4s%-23s%-12s%-12s%-14s%-15s", "    ", "Timestamp Geração", "Erro", "Máquina",
                        "Linha" + " Prod.", "Descrição");
    }
}
