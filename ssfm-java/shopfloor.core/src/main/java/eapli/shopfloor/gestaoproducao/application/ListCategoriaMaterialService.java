package eapli.shopfloor.gestaoproducao.application;

import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import eapli.shopfloor.gestaoproducao.domain.CategoriaMaterial;
import eapli.shopfloor.gestaoproducao.repositories.CategoriaMaterialRepository;
import eapli.shopfloor.infrastructure.persistence.PersistenceContext;
import eapli.shopfloor.usermanagement.domain.BaseRoles;

/**
 *João Pimentel 
 */
public class ListCategoriaMaterialService {

    private final AuthorizationService authz = AuthzRegistry.authorizationService();
    private final CategoriaMaterialRepository repository = PersistenceContext.repositories().categoriasMaterial();

    public Iterable<CategoriaMaterial> allCategoriasMaterial() {
        authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.GESTOR_PRODUCAO, BaseRoles.POWER_USER);

        return this.repository.findAll();
    }
}
