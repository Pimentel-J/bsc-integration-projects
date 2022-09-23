package eapli.shopfloor.gestaoproducao.application;

import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import eapli.shopfloor.gestaoproducao.domain.Produto;
import eapli.shopfloor.gestaoproducao.repositories.ProdutoRepository;
import eapli.shopfloor.infrastructure.persistence.PersistenceContext;
import eapli.shopfloor.usermanagement.domain.BaseRoles;

public class ListProdutosService {

    private final AuthorizationService authz = AuthzRegistry.authorizationService();
    private final ProdutoRepository repository = PersistenceContext.repositories().produtos();

    public Iterable<Produto> allProdutos() {
        authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.GESTOR_PRODUCAO, BaseRoles.POWER_USER);

        return this.repository.findAll();
    }
}
