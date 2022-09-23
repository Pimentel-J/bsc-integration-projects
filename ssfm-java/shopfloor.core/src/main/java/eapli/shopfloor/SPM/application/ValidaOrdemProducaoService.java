package eapli.shopfloor.SPM.application;

import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import eapli.shopfloor.general.CodigoAlfaCurto;
import eapli.shopfloor.gestaoproducao.domain.OrdemProducao;
import eapli.shopfloor.gestaoproducao.repositories.OrdemProducaoRepository;
import eapli.shopfloor.infrastructure.persistence.PersistenceContext;
import eapli.shopfloor.usermanagement.domain.BaseRoles;

public class ValidaOrdemProducaoService {

	private final AuthorizationService authz = AuthzRegistry.authorizationService();
    private final OrdemProducaoRepository repository = PersistenceContext.repositories().ordensProducao();
	
    public OrdemProducao existeOrdemProducao(CodigoAlfaCurto codOrdem) {
    	authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.SPM, BaseRoles.POWER_USER);
    	return repository.containsOrdemProducao(codOrdem);
    }
    
    public OrdemProducao saveOrdemProducao(OrdemProducao ordem) {
    	authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.SPM, BaseRoles.POWER_USER);
    	return repository.save(ordem);
    }
    
}
