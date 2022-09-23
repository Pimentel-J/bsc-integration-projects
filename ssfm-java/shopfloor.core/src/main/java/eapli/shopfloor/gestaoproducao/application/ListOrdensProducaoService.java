package eapli.shopfloor.gestaoproducao.application;

import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import eapli.shopfloor.gestaoproducao.domain.OrdemProducao;
import eapli.shopfloor.gestaoproducao.repositories.OrdemProducaoRepository;
import eapli.shopfloor.infrastructure.persistence.PersistenceContext;
import eapli.shopfloor.usermanagement.domain.BaseRoles;

/**
 *Diogo
 */

public class ListOrdensProducaoService {

    private final AuthorizationService authz = AuthzRegistry.authorizationService();
    private final OrdemProducaoRepository repository = PersistenceContext.repositories().ordensProducao();

    public Iterable<OrdemProducao> allOrdensProducao() {
        authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.GESTOR_PRODUCAO, BaseRoles.POWER_USER, BaseRoles.SPM);

        return this.repository.findAll();
    }
}

