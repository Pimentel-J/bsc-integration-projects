package eapli.shopfloor.persistence.impl.jpa;

import eapli.shopfloor.SPM.domain.EstadoNotificacaoErroProcessamento;
import eapli.shopfloor.SPM.domain.TipoNotificacaoErroProcessamento;
import eapli.shopfloor.gestaochaofabrica.domain.LinhaProducao;
import eapli.shopfloor.gestaochaofabrica.domain.Maquina;
import eapli.shopfloor.reporting.notificacoeserroprocessamento.dto.NotificacoesErrosProcessamento;
import eapli.shopfloor.reporting.notificacoeserroprocessamento.repositories.NotificacaoErroProcessReportingRepository;

import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 *João Pimentel 
 */
public class JpaNotificacaoErroProcessReportingRepository extends BaseJpaReportingRepositoryBase
        implements NotificacaoErroProcessReportingRepository {

    private static final String NEW_DTO_OBJECT = "new eapli.shopfloor.reporting.notificacoeserroprocessamento.dto." +
            "NotificacoesErrosProcessamento(n.tipo, n.timestampGeracao, n.mensagem, n.linhaProducao, n.descricao)";

    private static final String NOTIFICACOES_GERAL = "SELECT " + NEW_DTO_OBJECT + "FROM NotificacaoErroProcessamento n " +
            "WHERE n.estado =:estado";
    private static final String NOTIFICACOES_POR_TIPO_ERRO = "SELECT " + NEW_DTO_OBJECT + "FROM NotificacaoErroProcessamento n " +
            "WHERE n.estado =:estado AND n.tipo = :erro";
    private static final String NOTIFICACOES_POR_DATA_ERRO = "SELECT " + NEW_DTO_OBJECT + "FROM NotificacaoErroProcessamento n " +
            "WHERE n.estado =:estado AND n.timestampGeracao BETWEEN :dataInicio AND :dataFim";
    private static final String NOTIFICACOES_POR_LINHA = "SELECT " + NEW_DTO_OBJECT + "FROM NotificacaoErroProcessamento n " +
            "WHERE n.estado =:estado AND n.linhaProducao = :linha";
    private static final String NOTIFICACOES_POR_MAQUINA = "SELECT " + NEW_DTO_OBJECT + "FROM NotificacaoErroProcessamento n " +
            "WHERE n.estado =:estado AND n.mensagem.maquina = :maquina";

    JpaNotificacaoErroProcessReportingRepository() { super(); }

    @Override
    public Iterable<NotificacoesErrosProcessamento> reportNotificacoesErroProcessGeral(EstadoNotificacaoErroProcessamento estado) {
        final TypedQuery<NotificacoesErrosProcessamento> query =
                entityManager().createQuery(NOTIFICACOES_GERAL, NotificacoesErrosProcessamento.class);
        query.setParameter("estado", estado);
        return query.getResultList();
    }

    @Override
    public Iterable<NotificacoesErrosProcessamento> reportNotificacoesErroProcessPorTipoErro(
            TipoNotificacaoErroProcessamento erro, EstadoNotificacaoErroProcessamento estado) {
        final TypedQuery<NotificacoesErrosProcessamento> query =
                entityManager().createQuery(NOTIFICACOES_POR_TIPO_ERRO, NotificacoesErrosProcessamento.class);
        query.setParameter("erro", erro);
        query.setParameter("estado", estado);
        return query.getResultList();
    }

    @Override
    public Iterable<NotificacoesErrosProcessamento> reportNotificacoesErroProcessPorDataErro(
            Calendar dataErro, EstadoNotificacaoErroProcessamento estado) {
        final TypedQuery<NotificacoesErrosProcessamento> query =
                entityManager().createQuery(NOTIFICACOES_POR_DATA_ERRO, NotificacoesErrosProcessamento.class);
        Calendar dataFim = Calendar.getInstance();
        dataFim.setTime(dataErro.getTime());
        dataFim.add(Calendar.DAY_OF_YEAR,1);
        query.setParameter("dataInicio", dataErro);
        query.setParameter("dataFim", dataFim);
        query.setParameter("estado", estado);
        return query.getResultList();
    }

    @Override
    public Iterable<NotificacoesErrosProcessamento> reportNotificacoesErroProcessPorLinhaProducao(
            LinhaProducao linha, EstadoNotificacaoErroProcessamento estado) {
        final TypedQuery<NotificacoesErrosProcessamento> query =
                entityManager().createQuery(NOTIFICACOES_POR_LINHA, NotificacoesErrosProcessamento.class);
        query.setParameter("linha", linha);
        query.setParameter("estado", estado);
        return query.getResultList();
    }

    @Override
    public Iterable<NotificacoesErrosProcessamento> reportNotificacoesErroProcessPorMaquina(
            Maquina maquina, EstadoNotificacaoErroProcessamento estado) {
        final TypedQuery<NotificacoesErrosProcessamento> query =
                entityManager().createQuery(NOTIFICACOES_POR_MAQUINA, NotificacoesErrosProcessamento.class);
        query.setParameter("maquina", maquina);
        query.setParameter("estado", estado);
        return query.getResultList();
    }

}
