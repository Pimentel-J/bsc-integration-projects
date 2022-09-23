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
 * Controller - Consultar Notificações de Erros de Processamentos Ativas
 *
 *João Pimentel 
 */
@UseCaseController
public class NotificacaoErroProcessAtivaReportingController {

    private final NotificacaoErroProcessReportingRepository repository =
            PersistenceContext.repositories().notificacaoErroProcessamentoReporting();
    private final ListLinhasProducaoService listLinhasProducaoService = new ListLinhasProducaoService();
    private final ListMaquinasService listMaquinasService = new ListMaquinasService();
    private final ListFiltrosPesquisaNotificacoesService listFiltrosService = new ListFiltrosPesquisaNotificacoesService();
    private final ListTipoNotificacaoErroProcessService listTipoNotificacaoService = new ListTipoNotificacaoErroProcessService();

    public Iterable<NotificacoesErrosProcessamento> reportNotificacoesErroProcessAtivasGeral() {
        return repository.reportNotificacoesErroProcessGeral(EstadoNotificacaoErroProcessamento.ATIVA);
    }

    public Iterable<NotificacoesErrosProcessamento> reportNotificacoesErroProcessAtivasPorTipoErro(TipoNotificacaoErroProcessamento erro) {
        return repository.reportNotificacoesErroProcessPorTipoErro(erro, EstadoNotificacaoErroProcessamento.ATIVA);
    }

    public Iterable<NotificacoesErrosProcessamento> reportNotificacoesErroProcessAtivasPorDataErro(Calendar dataErro) {
        return repository.reportNotificacoesErroProcessPorDataErro(dataErro, EstadoNotificacaoErroProcessamento.ATIVA);
    }

    public Iterable<LinhaProducao> listLinhasProducao() {
        return listLinhasProducaoService.allLinhasProducao();
    }

    public Iterable<NotificacoesErrosProcessamento> reportNotificacoesErroProcessAtivasPorLinhaProducao(LinhaProducao linha) {
        return repository.reportNotificacoesErroProcessPorLinhaProducao(linha, EstadoNotificacaoErroProcessamento.ATIVA);
    }

    public Iterable<Maquina> listMaquinas() {
        return listMaquinasService.allMaquinas();
    }

    public Iterable<NotificacoesErrosProcessamento> reportNotificacoesErroProcessAtivasPorMaquina(Maquina maquina) {
        return repository.reportNotificacoesErroProcessPorMaquina(maquina, EstadoNotificacaoErroProcessamento.ATIVA);
    }

    public Iterable<ListFiltrosPesquisaNotificacoesService.Filtros> listFiltros() {
        return listFiltrosService.allFiltrosPesquisaNotificacoes();
    }

    public Iterable<TipoNotificacaoErroProcessamento> listTipoErro() {
        return listTipoNotificacaoService.allTiposNotificacaoErroProcessamento();
    }
}
