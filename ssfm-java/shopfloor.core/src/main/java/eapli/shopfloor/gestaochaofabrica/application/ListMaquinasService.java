package eapli.shopfloor.gestaochaofabrica.application;

import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import eapli.shopfloor.gestaochaofabrica.domain.LinhaProducao;
import eapli.shopfloor.gestaochaofabrica.domain.Maquina;
import eapli.shopfloor.gestaochaofabrica.repositories.MaquinaRepository;
import eapli.shopfloor.infrastructure.persistence.PersistenceContext;
import eapli.shopfloor.usermanagement.domain.BaseRoles;

/**
 *Diogo
 */
public class ListMaquinasService {

    private final AuthorizationService authz = AuthzRegistry.authorizationService();
    private final MaquinaRepository repository = PersistenceContext.repositories().maquinas();

    public Iterable<Maquina> allMaquinas() {
        authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.GESTOR_CHAO_FABRICA, BaseRoles.POWER_USER);

        return this.repository.findAll();
    }

    public Iterable<Maquina> allMaquinasByLinha(LinhaProducao linhaProducao) {
        authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.GESTOR_CHAO_FABRICA, BaseRoles.POWER_USER);

        return this.repository.maquinasByLinhaProducao(linhaProducao);
    }

    public Maquina ultimaPosicaoOcupadaPorLinhaProducao(LinhaProducao linhaProducao) {
        authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.GESTOR_CHAO_FABRICA, BaseRoles.POWER_USER);

        return this.repository.ultimaPosicaoOcupadaPorLinhaProducao(linhaProducao);
    }
}
