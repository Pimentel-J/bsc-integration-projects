package eapli.shopfloor.SPM.application;

import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import eapli.shopfloor.SPM.domain.NotificacaoErroProcessamento;
import eapli.shopfloor.SPM.repositories.NotificacaoErroProcessamentoRepository;
import eapli.shopfloor.infrastructure.persistence.PersistenceContext;
import eapli.shopfloor.usermanagement.domain.BaseRoles;

public class GuardaErrosService {

	private final AuthorizationService authz = AuthzRegistry.authorizationService();
    private final NotificacaoErroProcessamentoRepository notificacaoRepository = PersistenceContext.repositories().notificacoesErroProcessamento();
	
    public NotificacaoErroProcessamento saveNotificacao(NotificacaoErroProcessamento notificacao) {
    	authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.SPM, BaseRoles.POWER_USER);
    	return notificacaoRepository.save(notificacao);
    }
}
