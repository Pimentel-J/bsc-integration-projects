package eapli.shopfloor.reporting.notificacoeserroprocessamento.repositories;

import eapli.framework.domain.repositories.ReportingRepository;
import eapli.shopfloor.SPM.domain.EstadoNotificacaoErroProcessamento;
import eapli.shopfloor.SPM.domain.TipoNotificacaoErroProcessamento;
import eapli.shopfloor.gestaochaofabrica.domain.LinhaProducao;
import eapli.shopfloor.gestaochaofabrica.domain.Maquina;
import eapli.shopfloor.reporting.notificacoeserroprocessamento.dto.NotificacoesErrosProcessamento;

import java.util.Calendar;

/**
 * Repository - Consultar Notificações de Erros de Processamentos
 *
 *João Pimentel 
 */
@ReportingRepository
public interface NotificacaoErroProcessReportingRepository {

    Iterable<NotificacoesErrosProcessamento> reportNotificacoesErroProcessGeral(EstadoNotificacaoErroProcessamento estado);

    Iterable<NotificacoesErrosProcessamento> reportNotificacoesErroProcessPorTipoErro(TipoNotificacaoErroProcessamento erro,
                                                                                      EstadoNotificacaoErroProcessamento estado);

    Iterable<NotificacoesErrosProcessamento> reportNotificacoesErroProcessPorDataErro(Calendar dataErro,
                                                                                      EstadoNotificacaoErroProcessamento estado);

    Iterable<NotificacoesErrosProcessamento> reportNotificacoesErroProcessPorLinhaProducao(LinhaProducao linha,
                                                                                           EstadoNotificacaoErroProcessamento estado);

    Iterable<NotificacoesErrosProcessamento> reportNotificacoesErroProcessPorMaquina(Maquina maquina,
                                                                                     EstadoNotificacaoErroProcessamento estado);

}
