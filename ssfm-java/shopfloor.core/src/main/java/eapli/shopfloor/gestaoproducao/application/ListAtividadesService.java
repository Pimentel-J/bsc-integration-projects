package eapli.shopfloor.gestaoproducao.application;

import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import eapli.shopfloor.gestaoproducao.domain.Atividade;
import eapli.shopfloor.gestaoproducao.repositories.AtividadeRepository;
import eapli.shopfloor.infrastructure.persistence.PersistenceContext;
import eapli.shopfloor.usermanagement.domain.BaseRoles;

/**
 *Diogo
 */

public class ListAtividadesService {

    private final AuthorizationService authz = AuthzRegistry.authorizationService();
    private final AtividadeRepository repository = PersistenceContext.repositories().atividades();

    public Iterable<Atividade> allAtividades() {
        authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.GESTOR_PRODUCAO, BaseRoles.POWER_USER);

        return this.repository.findAll();
    }
}

