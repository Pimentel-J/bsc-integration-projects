package eapli.shopfloor.SPM.application;

import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import eapli.shopfloor.general.CodigoAlfaCurto;
import eapli.shopfloor.gestaoproducao.domain.Produto;
import eapli.shopfloor.gestaoproducao.repositories.ProdutoRepository;
import eapli.shopfloor.infrastructure.persistence.PersistenceContext;
import eapli.shopfloor.usermanagement.domain.BaseRoles;

public class ValidaProdutoService {

	private final AuthorizationService authz = AuthzRegistry.authorizationService();
    private final ProdutoRepository repository = PersistenceContext.repositories().produtos();
	
    public Produto existeProduto(CodigoAlfaCurto codProduto) {
    	authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.SPM, BaseRoles.POWER_USER);
    	return repository.containsProduto(codProduto);
    }
    
}
