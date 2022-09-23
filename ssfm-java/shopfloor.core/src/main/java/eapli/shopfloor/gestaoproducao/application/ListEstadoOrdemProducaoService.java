package eapli.shopfloor.gestaoproducao.application;

import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import eapli.shopfloor.gestaoproducao.domain.EstadoOrdemProducao;
import eapli.shopfloor.infrastructure.persistence.PersistenceContext;
import eapli.shopfloor.reporting.ordensproducao.dto.OrdensProducao;
import eapli.shopfloor.reporting.ordensproducao.repositories.OrdemProducaoReportingRepository;
import eapli.shopfloor.usermanagement.domain.BaseRoles;

import java.util.Arrays;

/**
 *João Pimentel 
 */
public class ListEstadoOrdemProducaoService {

    private final AuthorizationService authz = AuthzRegistry.authorizationService();

    public Iterable<EstadoOrdemProducao> allEstadosOrdemProducao() {
        authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.GESTOR_PRODUCAO, BaseRoles.POWER_USER);

        Iterable<EstadoOrdemProducao> estados = Arrays.asList(EstadoOrdemProducao.values());

        return estados;
    }

}