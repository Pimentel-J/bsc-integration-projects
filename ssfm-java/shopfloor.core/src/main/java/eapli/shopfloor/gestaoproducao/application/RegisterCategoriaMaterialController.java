package eapli.shopfloor.gestaoproducao.application;


import eapli.framework.application.UseCaseController;
import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import eapli.shopfloor.general.CodigoAlfaCurto;
import eapli.shopfloor.general.Descricao;
import eapli.shopfloor.gestaoproducao.domain.CategoriaMaterial;
import eapli.shopfloor.gestaoproducao.repositories.CategoriaMaterialRepository;
import eapli.shopfloor.infrastructure.persistence.PersistenceContext;
import eapli.shopfloor.usermanagement.domain.BaseRoles;

/**
 *João Pimentel 
 */
@UseCaseController
public class RegisterCategoriaMaterialController {

    private final CategoriaMaterialRepository repository = PersistenceContext.repositories().categoriasMaterial();
    private final AuthorizationService authService = AuthzRegistry.authorizationService();

    public CategoriaMaterial registerCategoriaMaterial(final String codigo, final String descricao) {
        authService.ensureAuthenticatedUserHasAnyOf(BaseRoles.GESTOR_PRODUCAO, BaseRoles.POWER_USER);

        final CategoriaMaterial newCategoriaMaterial = new CategoriaMaterial(CodigoAlfaCurto.valueOf(codigo),
                Descricao.valueOf(descricao));
        return this.repository.save(newCategoriaMaterial);
    }

    public boolean containsCategoriaMaterial(String codigo) {
        return repository.containsOfIdentity(CodigoAlfaCurto.valueOf(codigo));
    }
}
