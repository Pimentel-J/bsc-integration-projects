package eapli.shopfloor.gestaoproducao.application;

import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import eapli.shopfloor.gestaoproducao.domain.EncomendaInOrdemProducao;
import eapli.shopfloor.gestaoproducao.repositories.OrdemProducaoRepository;
import eapli.shopfloor.infrastructure.persistence.PersistenceContext;
import eapli.shopfloor.usermanagement.domain.BaseRoles;

/**
 *João Pimentel 
 */
public class ListEncomendaService {

    private final AuthorizationService authz = AuthzRegistry.authorizationService();
    private final OrdemProducaoRepository repository = PersistenceContext.repositories().ordensProducao();

    public Iterable<EncomendaInOrdemProducao> allEncomendas() {
        authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.GESTOR_PRODUCAO, BaseRoles.POWER_USER);

        return this.repository.allEncomendas();
    }
}
