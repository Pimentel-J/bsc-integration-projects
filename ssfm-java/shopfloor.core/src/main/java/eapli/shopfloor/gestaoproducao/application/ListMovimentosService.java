package eapli.shopfloor.gestaoproducao.application;

import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import eapli.shopfloor.gestaoproducao.domain.Movimento;
import eapli.shopfloor.gestaoproducao.repositories.MovimentoRepository;
import eapli.shopfloor.infrastructure.persistence.PersistenceContext;
import eapli.shopfloor.usermanagement.domain.BaseRoles;

/**
 *Diogo
 */

public class ListMovimentosService {

    private final AuthorizationService authz = AuthzRegistry.authorizationService();
    private final MovimentoRepository repository = PersistenceContext.repositories().movimentos();

    public Iterable<Movimento> allMovimentos() {
        authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.GESTOR_PRODUCAO, BaseRoles.POWER_USER);

        return this.repository.findAll();
    }
}

