package eapli.shopfloor.gestaochaofabrica.application;

import eapli.framework.application.UseCaseController;
import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import eapli.shopfloor.general.CodigoAlfaCurto;
import eapli.shopfloor.general.Descricao;
import eapli.shopfloor.gestaochaofabrica.domain.Deposito;
import eapli.shopfloor.gestaochaofabrica.repositories.DepositoRepository;
import eapli.shopfloor.infrastructure.persistence.PersistenceContext;
import eapli.shopfloor.usermanagement.domain.BaseRoles;

@UseCaseController
public class RegistarDepositoController {

    private final DepositoRepository depositoRepository = PersistenceContext.repositories().depositos();
    private final AuthorizationService authorizationService = AuthzRegistry.authorizationService();

    public Deposito registarDeposito(final String codigo, final String descricao) {
        authorizationService.ensureAuthenticatedUserHasAnyOf(BaseRoles.POWER_USER,
                BaseRoles.GESTOR_CHAO_FABRICA);

        final Deposito novoDeposito = new Deposito(CodigoAlfaCurto.valueOf(codigo), Descricao.valueOf(descricao));
        return depositoRepository.save(novoDeposito);
    }

}
