package eapli.shopfloor.SPM.application;

import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import eapli.shopfloor.SCM.domain.Mensagem;
import eapli.shopfloor.SCM.repositories.MensagemRepository;
import eapli.shopfloor.general.CodigoAlfaCurto;
import eapli.shopfloor.gestaochaofabrica.domain.LinhaProducao;
import eapli.shopfloor.gestaochaofabrica.domain.Maquina;
import eapli.shopfloor.gestaochaofabrica.repositories.MaquinaRepository;
import eapli.shopfloor.gestaoproducao.domain.OrdemProducao;
import eapli.shopfloor.infrastructure.persistence.PersistenceContext;
import eapli.shopfloor.usermanagement.domain.BaseRoles;

public class ConclusaoOrdemProducaoService {

	private final AuthorizationService authz = AuthzRegistry.authorizationService();
    private final MaquinaRepository maquinaRepository = PersistenceContext.repositories().maquinas();
    private final MensagemRepository mensagemRepository = PersistenceContext.repositories().mensagens();
	
    public boolean conclusaoOrdemProducao(LinhaProducao linhaProducao, OrdemProducao ordemProducao) {
    	
    	Maquina ultimaMaquina = ultimaMaqSequencia(linhaProducao);
    	
    	if (msgFinalAtividadeProcessada(ultimaMaquina, ordemProducao.identity()) == null) {
			return false;
		} else {
			return true;
		}
    	
    }
    
    private Maquina ultimaMaqSequencia(LinhaProducao linhaProducao) {
    	authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.SPM, BaseRoles.POWER_USER);
    	return maquinaRepository.ultimaPosicaoOcupadaPorLinhaProducao(linhaProducao);
    }
    
    private Mensagem msgFinalAtividadeProcessada(Maquina maquina, CodigoAlfaCurto codOrdemProd) {
    	authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.SPM, BaseRoles.POWER_USER);
    	return mensagemRepository.finalAtivUltimaMaqProcessada(maquina, codOrdemProd);
    }
    
}
