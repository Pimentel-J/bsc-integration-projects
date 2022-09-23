package eapli.shopfloor.SPM.application;

import eapli.framework.application.UseCaseController;
import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import eapli.shopfloor.SPM.domain.NotificacaoErroProcessamento;
import eapli.shopfloor.SPM.repositories.NotificacaoErroProcessamentoRepository;
import eapli.shopfloor.general.CodigoAlfaLongo;
import eapli.shopfloor.gestaoproducao.domain.CategoriaMaterial;
import eapli.shopfloor.infrastructure.persistence.PersistenceContext;
import eapli.shopfloor.usermanagement.domain.BaseRoles;

/**
 *João Pimentel 
 */
@UseCaseController
public class ArquivarNotificacoesErroProcessController {

    private final NotificacaoErroProcessamentoRepository repository =
            PersistenceContext.repositories().notificacoesErroProcessamento();
    private final AuthorizationService authService = AuthzRegistry.authorizationService();
    private final ListNotificacoesErroService service = new ListNotificacoesErroService();

    public NotificacaoErroProcessamento arquivarNotificacaoErroProcess(NotificacaoErroProcessamento notificacao) {
        authService.ensureAuthenticatedUserHasAnyOf(BaseRoles.GESTOR_CHAO_FABRICA, BaseRoles.POWER_USER);

        notificacao.arquivarNotificacaoErroProcess();

        return repository.save(notificacao);
    }

    public Iterable<NotificacaoErroProcessamento> listNotificacoesErroAtivas() {
        return service.allNotificacoesErroAtivas();
    }

}
