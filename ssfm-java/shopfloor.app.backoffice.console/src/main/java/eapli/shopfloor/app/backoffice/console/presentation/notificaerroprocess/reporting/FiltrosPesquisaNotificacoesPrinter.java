package eapli.shopfloor.app.backoffice.console.presentation.notificacoeserroprocessamento.reporting;

import eapli.framework.visitor.Visitor;
import eapli.shopfloor.reporting.notificacoeserroprocessamento.application.ListFiltrosPesquisaNotificacoesService;

/**
 * Printer - Consultar Filtros para pesquisa de Notificações de Erros de Processamentos
 *
 *João Pimentel 
 */
public class FiltrosPesquisaNotificacoesPrinter implements Visitor<ListFiltrosPesquisaNotificacoesService.Filtros> {

    @Override
    public void visit(final ListFiltrosPesquisaNotificacoesService.Filtros visitee) {
        System.out.printf("%-15s", visitee.name());
    }

}
