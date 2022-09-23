package eapli.shopfloor.SPM.repositories;

import eapli.framework.domain.repositories.DomainRepository;
import eapli.shopfloor.SPM.domain.NotificacaoErroProcessamento;

/**
 *1181455
 */
public interface NotificacaoErroProcessamentoRepository extends DomainRepository<Long, NotificacaoErroProcessamento> {

    public Iterable<NotificacaoErroProcessamento> notificacoesErroAtivas();

}
