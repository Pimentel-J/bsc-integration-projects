package eapli.shopfloor.app.backoffice.console.presentation.notificacoeserroprocessamento;

import eapli.framework.visitor.Visitor;
import eapli.shopfloor.SPM.domain.NotificacaoErroProcessamento;

import java.text.SimpleDateFormat;

/**
 * Printer - Consultar Notificações de Erros de Processamentos
 *
 *João Pimentel 
 */
public class NotificacaoErroProcessamentoPrinter implements Visitor<NotificacaoErroProcessamento> {

    @Override
    public void visit(final NotificacaoErroProcessamento visitee) {
        String date = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(visitee.timestampGeracao().getTime());
        System.out.printf("%-24s%-14s%-12s%-13s%-30s", date, visitee.mensagem().erro(),
                visitee.mensagem().maquina().identity(), visitee.linhaProducao().identity(), visitee.descricao());
    }

}
