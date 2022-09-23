package eapli.shopfloor.SPM.application;

import java.util.Calendar;

import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import eapli.shopfloor.SCM.domain.Mensagem;
import eapli.shopfloor.SCM.repositories.MensagemRepository;
import eapli.shopfloor.general.CodigoAlfaCurto;
import eapli.shopfloor.gestaochaofabrica.domain.LinhaProducao;
import eapli.shopfloor.infrastructure.persistence.PersistenceContext;
import eapli.shopfloor.usermanagement.domain.BaseRoles;

public class ListMensagensService {

    private final AuthorizationService authz = AuthzRegistry.authorizationService();
    private final MensagemRepository repository = PersistenceContext.repositories().mensagens();

    public Iterable<Mensagem> mensagensDeLinhaProducao(LinhaProducao linhaProducao, Calendar inicioIntervalo, Calendar fimIntervalo) {
        authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.SPM, BaseRoles.POWER_USER);
        if (inicioIntervalo != null) {
			return this.repository.mensagensDeLinhaProducao(linhaProducao, inicioIntervalo, fimIntervalo);
		} else {
			return this.repository.mensagensDeLinhaProducao(linhaProducao, fimIntervalo);
		}
        
    }
    
    public Mensagem saveMensagem(Mensagem mensagem) {
    	return this.repository.save(mensagem);
    }
    
    public CodigoAlfaCurto ordemProducaoMensagem(LinhaProducao linhaProducao, Calendar inicioIntervalo) {
    	return this.repository.ordemProducaoMensagem(linhaProducao, inicioIntervalo);
    }
}
