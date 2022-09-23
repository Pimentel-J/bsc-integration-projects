package eapli.shopfloor.SPM.application;

import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import eapli.shopfloor.SPM.domain.NotificacaoErroProcessamento;
import eapli.shopfloor.SPM.repositories.NotificacaoErroProcessamentoRepository;
import eapli.shopfloor.infrastructure.persistence.PersistenceContext;
import eapli.shopfloor.usermanagement.domain.BaseRoles;

/**
 *João Pimentel 
 */
public class ListNotificacoesErroService {

    private final AuthorizationService authz = AuthzRegistry.authorizationService();
    private final NotificacaoErroProcessamentoRepository repository =
            PersistenceContext.repositories().notificacoesErroProcessamento();

    public Iterable<NotificacaoErroProcessamento> allNotificacoesErroAtivas() {
        authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.GESTOR_CHAO_FABRICA, BaseRoles.POWER_USER);

        return this.repository.notificacoesErroAtivas();
    }

}
