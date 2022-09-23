package eapli.shopfloor.gestaochaofabrica.application;

import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import eapli.shopfloor.gestaochaofabrica.repositories.LinhaProducaoRepository;
import eapli.shopfloor.gestaochaofabrica.domain.LinhaProducao;

import eapli.shopfloor.infrastructure.persistence.PersistenceContext;
import eapli.shopfloor.usermanagement.domain.BaseRoles;

/**
 *Diogo
 */
public class ListLinhasProducaoService {

    private final AuthorizationService authz = AuthzRegistry.authorizationService();
    private final LinhaProducaoRepository repository = PersistenceContext.repositories().linhasProducao();

    public Iterable<LinhaProducao> allLinhasProducao() {
        authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.GESTOR_CHAO_FABRICA, BaseRoles.POWER_USER);

        return this.repository.findAll();
    }
    
    public Iterable<LinhaProducao> linhasProducaoComProcessamentoAtivo() {
        authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.GESTOR_CHAO_FABRICA, BaseRoles.SPM, BaseRoles.POWER_USER);

        return this.repository.linhasProducaoComProcessamentoAtivo();
    }
    
}
