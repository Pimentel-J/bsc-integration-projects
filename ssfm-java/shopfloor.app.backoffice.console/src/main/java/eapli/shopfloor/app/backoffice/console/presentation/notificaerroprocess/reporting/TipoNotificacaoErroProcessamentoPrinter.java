package eapli.shopfloor.app.backoffice.console.presentation.notificacoeserroprocessamento.reporting;

import eapli.framework.visitor.Visitor;
import eapli.shopfloor.SPM.domain.TipoNotificacaoErroProcessamento;

/**
 * Printer - Consultar Tipos de Notificação de Erro de Processamento
 *
 *João Pimentel
 */
public class TipoNotificacaoErroProcessamentoPrinter implements Visitor<TipoNotificacaoErroProcessamento> {

    @Override
    public void visit(final TipoNotificacaoErroProcessamento visitee) {
        System.out.printf("%-15s", visitee.name());
    }

}
