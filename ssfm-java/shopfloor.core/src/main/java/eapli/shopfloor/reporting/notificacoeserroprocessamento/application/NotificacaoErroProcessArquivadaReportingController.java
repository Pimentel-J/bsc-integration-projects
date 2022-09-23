package eapli.shopfloor.reporting.notificacoeserroprocessamento.application;

import eapli.framework.application.UseCaseController;
import eapli.shopfloor.SPM.application.ListTipoNotificacaoErroProcessService;
import eapli.shopfloor.SPM.domain.EstadoNotificacaoErroProcessamento;
import eapli.shopfloor.SPM.domain.TipoNotificacaoErroProcessamento;
import eapli.shopfloor.gestaochaofabrica.application.ListLinhasProducaoService;
import eapli.shopfloor.gestaochaofabrica.application.ListMaquinasService;
import eapli.shopfloor.gestaochaofabrica.domain.LinhaProducao;
import eapli.shopfloor.gestaochaofabrica.domain.Maquina;
import eapli.shopfloor.infrastructure.persistence.PersistenceContext;
import eapli.shopfloor.reporting.notificacoeserroprocessamento.dto.NotificacoesErrosProcessamento;
import eapli.shopfloor.reporting.notificacoeserroprocessamento.repositories.NotificacaoErroProcessReportingRepository;

import java.util.Calendar;

/**
 * Controller - Consultar Notificações de Erros de Processamentos Arquivadas
 *
 *João Pimentel 
 */
@UseCaseController
public class NotificacaoErroProcessArquivadaReportingController {

    private final NotificacaoErroProcessReportingRepository repository =
            PersistenceContext.repositories().notificacaoErroProcessamentoReporting();
    private final ListLinhasProducaoService listLinhasProducaoService = new ListLinhasProducaoService();
    private final ListMaquinasService listMaquinasService = new ListMaquinasService();
    private final ListFiltrosPesquisaNotificacoesService listFiltrosService = new ListFiltrosPesquisaNotificacoesService();
    private final ListTipoNotificacaoErroProcessService listTipoNotificacaoService = new ListTipoNotificacaoErroProcessService();

    public Iterable<NotificacoesErrosProcessamento> reportNotificacoesErroProcessArquivadasGeral() {
        return repository.reportNotificacoesErroProcessGeral(EstadoNotificacaoErroProcessamento.ARQUIVADA);
    }

    public Iterable<NotificacoesErrosProcessamento> reportNotificacoesErroProcessArquivadasPorTipoErro(TipoNotificacaoErroProcessamento erro) {
        return repository.reportNotificacoesErroProcessPorTipoErro(erro, EstadoNotificacaoErroProcessamento.ARQUIVADA);
    }

    public Iterable<NotificacoesErrosProcessamento> reportNotificacoesErroProcessArquivadasPorDataErro(Calendar dataErro) {
        return repository.reportNotificacoesErroProcessPorDataErro(dataErro, EstadoNotificacaoErroProcessamento.ARQUIVADA);
    }

    public Iterable<LinhaProducao> listLinhasProducao() {
        return listLinhasProducaoService.allLinhasProducao();
    }

    public Iterable<NotificacoesErrosProcessamento> reportNotificacoesErroProcessArquivadasPorLinhaProducao(LinhaProducao linha) {
        return repository.reportNotificacoesErroProcessPorLinhaProducao(linha, EstadoNotificacaoErroProcessamento.ARQUIVADA);
    }

    public Iterable<Maquina> listMaquinas() {
        return listMaquinasService.allMaquinas();
    }

    public Iterable<NotificacoesErrosProcessamento> reportNotificacoesErroProcessArquivadasPorMaquina(Maquina maquina) {
        return repository.reportNotificacoesErroProcessPorMaquina(maquina, EstadoNotificacaoErroProcessamento.ARQUIVADA);
    }

    public Iterable<ListFiltrosPesquisaNotificacoesService.Filtros> listFiltros() {
        return listFiltrosService.allFiltrosPesquisaNotificacoes();
    }

    public Iterable<TipoNotificacaoErroProcessamento> listTipoErro() {
        return listTipoNotificacaoService.allTiposNotificacaoErroProcessamento();
    }

}
