package eapli.shopfloor.gestaochaofabrica.application;

import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import eapli.shopfloor.gestaochaofabrica.domain.LinhaProducao;
import eapli.shopfloor.gestaochaofabrica.domain.Maquina;
import eapli.shopfloor.gestaochaofabrica.repositories.MaquinaRepository;
import eapli.shopfloor.infrastructure.persistence.PersistenceContext;
import eapli.shopfloor.usermanagement.domain.BaseRoles;

/**
 *João Pimentel 
 */
public class UpdateSequenciaMaquinasService {

    private final AuthorizationService authorizationService = AuthzRegistry.authorizationService();
    private final MaquinaRepository maquinaRepository = PersistenceContext.repositories().maquinas();

    public void updateSequenciaMaquinasLinhaProducao(LinhaProducao linha, Integer sequencia) {
        authorizationService.ensureAuthenticatedUserHasAnyOf(BaseRoles.GESTOR_CHAO_FABRICA, BaseRoles.POWER_USER);

        for (Maquina maquina : maquinaRepository.maquinasByLinhaProducao(linha)) {
            if (!(maquina.sequenciaMaquina() < sequencia)) {
                maquina.updateSequenciaMaquina(maquina.sequenciaMaquina() + 1);
                maquinaRepository.save(maquina);
            }
        }
    }

}
