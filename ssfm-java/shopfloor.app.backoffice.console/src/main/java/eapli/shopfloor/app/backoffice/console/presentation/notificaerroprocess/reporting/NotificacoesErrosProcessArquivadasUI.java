package eapli.shopfloor.app.backoffice.console.presentation.notificacoeserroprocessamento.reporting;

import eapli.framework.presentation.console.AbstractListUI;
import eapli.framework.presentation.console.ListWidget;
import eapli.framework.presentation.console.SelectWidget;
import eapli.framework.util.Console;
import eapli.framework.visitor.Visitor;
import eapli.shopfloor.SPM.domain.TipoNotificacaoErroProcessamento;
import eapli.shopfloor.app.backoffice.console.presentation.maquinas.LinhaProducaoPrinter;
import eapli.shopfloor.app.backoffice.console.presentation.maquinas.MaquinaPrinter;
import eapli.shopfloor.gestaochaofabrica.domain.LinhaProducao;
import eapli.shopfloor.gestaochaofabrica.domain.Maquina;
import eapli.shopfloor.reporting.notificacoeserroprocessamento.application.ListFiltrosPesquisaNotificacoesService;
import eapli.shopfloor.reporting.notificacoeserroprocessamento.application.NotificacaoErroProcessArquivadaReportingController;
import eapli.shopfloor.reporting.notificacoeserroprocessamento.dto.NotificacoesErrosProcessamento;

import java.util.Calendar;

/**
 * Esta classe cria uma lista das notificações de erros de processamentos arquivadas
 *
 *João Pimentel
 */
public class NotificacoesErrosProcessamentoArquivadasUI extends AbstractListUI<NotificacoesErrosProcessamento> {

    private final NotificacaoErroProcessArquivadaReportingController controller =
            new NotificacaoErroProcessArquivadaReportingController();

    @Override
    protected Iterable<NotificacoesErrosProcessamento> elements() { return this.controller.reportNotificacoesErroProcessArquivadasGeral(); }

    @Override
    protected Visitor<NotificacoesErrosProcessamento> elementPrinter() {
        return new NotificacoesErrosProcessamentoPrinter();
    }

    @Override
    protected String elementName() {
        return "Notificação de Erro de Processamento";
    }

    @Override
    protected String listHeader() {
        return "### Notificações de Erros de Processamento Arquivadas ###\n" +
                "-----------------------------------------------------\n" +
                String.format("%-4s%-23s%-12s%-14s%-15s", "    ",
                        "Timestamp Geração", "Máquina", "Linha Prod.", "Descrição");
    }

    @Override
    protected String emptyMessage() {
        return "Sem dados!";
    }

    @Override
    public String headline() {
        return "REPORT";
    }

    @Override
    protected boolean doShow() {

        Iterable<NotificacoesErrosProcessamento> elems = this.controller.reportNotificacoesErroProcessArquivadasGeral();
        final ListFiltrosPesquisaNotificacoesService.Filtros filtro = selectFiltro();

        switch (filtro) {
            case GERAL:
                elems = elements();
                break;
            case TIPO_ERRO:
                TipoNotificacaoErroProcessamento tipo = selectTipoErro();
                elems = this.controller.reportNotificacoesErroProcessArquivadasPorTipoErro(tipo);
                break;
            case DATA_ERRO:
                final Calendar dataErro = Console.readCalendar("Data do erro (dd-mm-yyyy):", "dd-MM-yyyy");
                elems = this.controller.reportNotificacoesErroProcessArquivadasPorDataErro(dataErro);
                break;
            case LINHA_PRODUCAO:
                final LinhaProducao linha = selectLinhaProducao();
                elems = this.controller.reportNotificacoesErroProcessArquivadasPorLinhaProducao(linha);
                break;
            case MAQUINA:
                final Maquina maquina = selectMaquina();
                elems = this.controller.reportNotificacoesErroProcessArquivadasPorMaquina(maquina);
                break;
            default:
                System.out.println("Opção inválida!");
                doShow();
        }

        if (!elems.iterator().hasNext()) {
            System.out.println("Não existem notificações!");
        } else {
            new ListWidget(this.listHeader(), elems, this.elementPrinter()).show();
        }
        return true;
    }

    private ListFiltrosPesquisaNotificacoesService.Filtros selectFiltro() {
        final Iterable<ListFiltrosPesquisaNotificacoesService.Filtros> listFiltros = controller.listFiltros();
        final SelectWidget<ListFiltrosPesquisaNotificacoesService.Filtros> selectorFiltros = new SelectWidget<>(
                "Selecione um filtro de pesquisa", listFiltros, new FiltrosPesquisaNotificacoesPrinter());
        selectorFiltros.show();
        return selectorFiltros.selectedElement();
    }

    private TipoNotificacaoErroProcessamento selectTipoErro() {
        final Iterable<TipoNotificacaoErroProcessamento> listFiltros = controller.listTipoErro();
        final SelectWidget<TipoNotificacaoErroProcessamento> selectorFiltros = new SelectWidget<>("Selecione um tipo " +
                "de erro", listFiltros, new TipoNotificacaoErroProcessamentoPrinter());
        selectorFiltros.show();
        return selectorFiltros.selectedElement();
    }

    private LinhaProducao selectLinhaProducao() {
        final Iterable<LinhaProducao> linhasProducao = controller.listLinhasProducao();
        final SelectWidget<LinhaProducao> selectorLinhaProducao = new SelectWidget<>("Selecione uma Linha de " +
                "Produção", linhasProducao, new LinhaProducaoPrinter());
        selectorLinhaProducao.show();
        return selectorLinhaProducao.selectedElement();
    }

    private Maquina selectMaquina() {
        final Iterable<Maquina> maquinas = controller.listMaquinas();
        final SelectWidget<Maquina> selectorMaquina = new SelectWidget<>("Selecione uma Máquina", maquinas,
                new MaquinaPrinter());
        selectorMaquina.show();
        return selectorMaquina.selectedElement();
    }
}
