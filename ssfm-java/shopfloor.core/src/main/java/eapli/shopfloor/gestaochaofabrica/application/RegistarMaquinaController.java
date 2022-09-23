package eapli.shopfloor.gestaochaofabrica.application;


import eapli.framework.application.UseCaseController;
import eapli.framework.general.domain.model.Description;
import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import eapli.shopfloor.general.CodigoAlfaCurto;
import eapli.shopfloor.general.Descricao;
import eapli.shopfloor.general.NumeroSerie;
import eapli.shopfloor.gestaochaofabrica.domain.LinhaProducao;
import eapli.shopfloor.gestaochaofabrica.domain.Maquina;
import eapli.shopfloor.gestaochaofabrica.repositories.MaquinaRepository;
import eapli.shopfloor.infrastructure.persistence.PersistenceContext;
import eapli.shopfloor.usermanagement.domain.BaseRoles;

import java.util.Calendar;

/**
 *Diogo
 */
@UseCaseController
public class RegistarMaquinaController {

    private final AuthorizationService authorizationService = AuthzRegistry.authorizationService();
    private final MaquinaRepository maquinaRepository = PersistenceContext.repositories().maquinas();
    private final ListMaquinasService maquinaService = new ListMaquinasService();
    private final ListLinhasProducaoService linhasProducaoService = new ListLinhasProducaoService();
    private final UpdateSequenciaMaquinasService updateSequenciaMaquinasService = new UpdateSequenciaMaquinasService();

    public Maquina registarMaquina(final String idMaquina, final String numSerie, final String descricao,
                                   final Calendar dataInstalacao, final String marca, String modelo,
                                   final LinhaProducao linhaProducao, Integer sequencia) {
        authorizationService.ensureAuthenticatedUserHasAnyOf(BaseRoles.POWER_USER, BaseRoles.GESTOR_CHAO_FABRICA);

        final Maquina novaMaquina = new Maquina(CodigoAlfaCurto.valueOf(idMaquina), NumeroSerie.valueOf(numSerie),
                Descricao.valueOf(descricao), dataInstalacao, Description.valueOf(marca),
                CodigoAlfaCurto.valueOf(modelo), linhaProducao, sequencia);

        return maquinaRepository.save(novaMaquina);
    }
    
    public Maquina registarMaquinaComProto(final String idMaquina, final String numSerie, final String descricao,
                                   final Calendar dataInstalacao, final String marca, String modelo,
                                   final LinhaProducao linhaProducao, final Integer sequencia, final int idProtocolo) {
        authorizationService.ensureAuthenticatedUserHasAnyOf(BaseRoles.POWER_USER, BaseRoles.GESTOR_CHAO_FABRICA);

        final Maquina novaMaquina = new Maquina(CodigoAlfaCurto.valueOf(idMaquina), NumeroSerie.valueOf(numSerie),
                Descricao.valueOf(descricao), dataInstalacao, Description.valueOf(marca),
                CodigoAlfaCurto.valueOf(modelo), linhaProducao, sequencia, idProtocolo);

        return maquinaRepository.save(novaMaquina);
    }

    public boolean containsMaquina(String idMaquina) {
        return maquinaRepository.containsOfIdentity(CodigoAlfaCurto.valueOf(idMaquina));
    }

    public Iterable<LinhaProducao> linhasProducao() {
        return this.linhasProducaoService.allLinhasProducao();
    }

    public Iterable<Maquina> procuraPorLinhaProducao(LinhaProducao linhaProducao) {
        return maquinaService.allMaquinasByLinha(linhaProducao);
    }

    public Integer ultimaPosicaoOcupadaPorLinhaProducao(LinhaProducao linhaProducao) {
        return (maquinaService.ultimaPosicaoOcupadaPorLinhaProducao(linhaProducao) ==
                null) ? -1 : maquinaService.ultimaPosicaoOcupadaPorLinhaProducao(linhaProducao).sequenciaMaquina();
    }

    public void updateSequenciaMaquinasLinhaProducao(LinhaProducao linha, Integer sequencia) {
        if (!(sequencia > ultimaPosicaoOcupadaPorLinhaProducao(linha))) {
            updateSequenciaMaquinasService.updateSequenciaMaquinasLinhaProducao(linha, sequencia);
        }
    }
}
