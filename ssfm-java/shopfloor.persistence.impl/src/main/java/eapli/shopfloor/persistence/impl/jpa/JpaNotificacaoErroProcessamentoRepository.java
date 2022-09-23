package eapli.shopfloor.persistence.impl.jpa;

import eapli.shopfloor.SPM.domain.EstadoNotificacaoErroProcessamento;
import eapli.shopfloor.SPM.domain.NotificacaoErroProcessamento;
import eapli.shopfloor.SPM.repositories.NotificacaoErroProcessamentoRepository;

import javax.persistence.TypedQuery;

public class JpaNotificacaoErroProcessamentoRepository extends BaseJpaRepositoryBase<NotificacaoErroProcessamento,
        Long, Long> implements NotificacaoErroProcessamentoRepository {

    private static final String NOTIFICACOES_ATIVAS =
            "SELECT n FROM NotificacaoErroProcessamento n WHERE n.estado = :estado";

    public JpaNotificacaoErroProcessamentoRepository() {
        super("id");
    }

    @Override
    public Iterable<NotificacaoErroProcessamento> notificacoesErroAtivas() {
        final TypedQuery<NotificacaoErroProcessamento> query = entityManager().createQuery(NOTIFICACOES_ATIVAS,
                NotificacaoErroProcessamento.class);
        query.setParameter("estado", EstadoNotificacaoErroProcessamento.ATIVA);
        return query.getResultList();
    }

}
