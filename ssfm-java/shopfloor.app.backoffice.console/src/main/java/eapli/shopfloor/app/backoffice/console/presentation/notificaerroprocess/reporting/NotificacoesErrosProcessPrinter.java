package eapli.shopfloor.app.backoffice.console.presentation.notificacoeserroprocessamento.reporting;

import eapli.framework.visitor.Visitor;
import eapli.shopfloor.reporting.notificacoeserroprocessamento.dto.NotificacoesErrosProcessamento;

import java.text.SimpleDateFormat;

/**
 * Printer - Consultar Notificações de Erros de Processamentos
 *
 *João Pimentel 
 */
public class NotificacoesErrosProcessamentoPrinter implements Visitor<NotificacoesErrosProcessamento> {

    @Override
    public void visit(final NotificacoesErrosProcessamento visitee) {
        String date = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(visitee.timestampGeracao.getTime());
        System.out.printf("%-24s%-12s%-14s%-50s", date, visitee.mensagem.maquina().identity(),
                visitee.linhaProducao.identity(), visitee.descricao);
    }

}
