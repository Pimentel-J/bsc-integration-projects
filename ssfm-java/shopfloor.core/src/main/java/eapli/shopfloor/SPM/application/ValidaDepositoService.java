package eapli.shopfloor.SPM.application;

import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import eapli.shopfloor.general.CodigoAlfaCurto;
import eapli.shopfloor.gestaochaofabrica.domain.Deposito;
import eapli.shopfloor.gestaochaofabrica.repositories.DepositoRepository;
import eapli.shopfloor.infrastructure.persistence.PersistenceContext;
import eapli.shopfloor.usermanagement.domain.BaseRoles;

public class ValidaDepositoService {

	private final AuthorizationService authz = AuthzRegistry.authorizationService();
    private final DepositoRepository repository = PersistenceContext.repositories().depositos();
	
    public Deposito existeDeposito(CodigoAlfaCurto codDeposito) {
    	authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.SPM, BaseRoles.POWER_USER);
    	return repository.containsDeposito(codDeposito);
    }
    
}
