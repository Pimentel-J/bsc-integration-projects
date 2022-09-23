package eapli.shopfloor.gestaoproducao.application;

import eapli.framework.application.UseCaseController;
import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import eapli.shopfloor.general.CodigoAlfaCurto;
import eapli.shopfloor.general.CodigoAlfaLongo;
import eapli.shopfloor.general.Descricao;
import eapli.shopfloor.gestaoproducao.domain.CategoriaMaterial;
import eapli.shopfloor.gestaoproducao.domain.FichaTecnicaMaterial;
import eapli.shopfloor.gestaoproducao.domain.Material;
import eapli.shopfloor.gestaoproducao.repositories.MaterialRepository;
import eapli.shopfloor.infrastructure.persistence.PersistenceContext;
import eapli.shopfloor.usermanagement.domain.BaseRoles;

/**
 *João Pimentel 
 */
@UseCaseController
public class RegisterMaterialController {

    private final MaterialRepository repository = PersistenceContext.repositories().materiais();
    private final AuthorizationService authService = AuthzRegistry.authorizationService();
    private final ListCategoriaMaterialService service = new ListCategoriaMaterialService();

    public Material registerMaterial(final String codigo, final String descricao, final String nomeFicha,
                                     final String pathFicha, final CategoriaMaterial categoriaMaterial) {
        authService.ensureAuthenticatedUserHasAnyOf(BaseRoles.GESTOR_PRODUCAO, BaseRoles.POWER_USER);

        final Material material = new Material(CodigoAlfaLongo.valueOf(codigo), Descricao.valueOf(descricao),
                FichaTecnicaMaterial.valueOf(nomeFicha, pathFicha), categoriaMaterial);
        return repository.save(material);
    }

    public Iterable<CategoriaMaterial> listCategoriasMaterial() {
        return service.allCategoriasMaterial();
    }

    public boolean containsMaterial(String codigo) {
        return repository.containsOfIdentity(CodigoAlfaLongo.valueOf(codigo));
    }
}
