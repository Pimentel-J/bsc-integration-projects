package eapli.shopfloor.gestaochaofabrica.application;


import eapli.framework.application.UseCaseController;
import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import eapli.shopfloor.general.CodigoAlfaCurto;
import eapli.shopfloor.gestaochaofabrica.domain.LinhaProducao;
import eapli.shopfloor.gestaochaofabrica.repositories.LinhaProducaoRepository;
import eapli.shopfloor.infrastructure.persistence.PersistenceContext;
import eapli.shopfloor.usermanagement.domain.BaseRoles;


@UseCaseController
public class RegistarLinhaProducaoController {

    private final LinhaProducaoRepository linhaProducaoRepository = PersistenceContext.repositories().linhasProducao();
    private final AuthorizationService authorizationService = AuthzRegistry.authorizationService();
    private final ListLinhasProducaoService listLinhasProducao = new ListLinhasProducaoService();

    public LinhaProducao registarLinhaProducao(final String idLinhaProducao) {
        authorizationService.ensureAuthenticatedUserHasAnyOf(BaseRoles.POWER_USER, BaseRoles.GESTOR_CHAO_FABRICA);

        final LinhaProducao novaLinhaProducao = new LinhaProducao(CodigoAlfaCurto.valueOf(idLinhaProducao));
        return linhaProducaoRepository.save(novaLinhaProducao);
    }

    public Iterable<LinhaProducao> listLinhasProducao() {
        return listLinhasProducao.allLinhasProducao();
    }

    public LinhaProducao getLinhaProducao(final String id) {
        return linhaProducaoRepository.ofIdentity(CodigoAlfaCurto.valueOf(id)).orElseThrow(IllegalStateException::new);
    }

}
