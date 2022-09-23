package eapli.shopfloor.SPM.application;

import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import eapli.shopfloor.SPM.domain.TipoNotificacaoErroProcessamento;
import eapli.shopfloor.usermanagement.domain.BaseRoles;

import java.util.Arrays;

/**
 *João Pimentel 
 */
public class ListTipoNotificacaoErroProcessService {

    private final AuthorizationService authz = AuthzRegistry.authorizationService();

    public Iterable<TipoNotificacaoErroProcessamento> allTiposNotificacaoErroProcessamento() {
        authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.GESTOR_CHAO_FABRICA, BaseRoles.POWER_USER);

        Iterable<TipoNotificacaoErroProcessamento> tipos = Arrays.asList(TipoNotificacaoErroProcessamento.values());

        return tipos;
    }
}
