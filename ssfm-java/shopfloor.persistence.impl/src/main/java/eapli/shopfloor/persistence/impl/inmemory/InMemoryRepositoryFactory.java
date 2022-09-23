package eapli.shopfloor.persistence.impl.inmemory;

import eapli.shopfloor.clientusermanagement.repositories.ClientUserRepository;
import eapli.shopfloor.clientusermanagement.repositories.SignupRequestRepository;
import eapli.shopfloor.gestaochaofabrica.repositories.DepositoRepository;
import eapli.shopfloor.gestaochaofabrica.repositories.LinhaProducaoRepository;
import eapli.shopfloor.gestaochaofabrica.repositories.MaquinaRepository;
import eapli.shopfloor.gestaoproducao.repositories.AtividadeRepository;
import eapli.shopfloor.gestaoproducao.repositories.CategoriaMaterialRepository;
import eapli.shopfloor.gestaoproducao.repositories.MaterialRepository;
import eapli.shopfloor.gestaoproducao.repositories.MovimentoRepository;
import eapli.shopfloor.infrastructure.bootstrapers.BaseBootstrapper;
import eapli.shopfloor.infrastructure.persistence.RepositoryFactory;
import eapli.framework.domain.repositories.TransactionalContext;
import eapli.framework.infrastructure.authz.domain.repositories.UserRepository;
import eapli.framework.infrastructure.authz.repositories.impl.InMemoryUserRepository;
import eapli.shopfloor.SCM.repositories.IdParaIpRepository;
import eapli.shopfloor.SCM.repositories.MensagemRepository;
import eapli.shopfloor.SPM.repositories.NotificacaoErroProcessamentoRepository;
import eapli.shopfloor.gestaoproducao.repositories.ExecucaoOrdemProducaoRepository;
import eapli.shopfloor.gestaoproducao.repositories.OrdemProducaoRepository;
import eapli.shopfloor.gestaoproducao.repositories.ProdutoRepository;
import eapli.shopfloor.reporting.notificacoeserroprocessamento.repositories.NotificacaoErroProcessReportingRepository;
import eapli.shopfloor.reporting.ordensproducao.repositories.OrdemProducaoReportingRepository;
import eapli.shopfloor.reporting.produtos.repositories.ProdutoReportingRepository;

/**
 *
 * Created by nuno on 20/03/16.
 */
public class InMemoryRepositoryFactory implements RepositoryFactory {

	static {
		// only needed because of the in memory persistence
		new BaseBootstrapper().execute();
	}

	@Override
	public UserRepository users(final TransactionalContext tx) {
		return new InMemoryUserRepository();
	}

	@Override
	public UserRepository users() {
		return users(null);
	}


	@Override
	public ClientUserRepository clientUsers(final TransactionalContext tx) {

		return new InMemoryClientUserRepository();
	}

	@Override
	public ClientUserRepository clientUsers() {
		return clientUsers(null);
	}

	@Override
	public SignupRequestRepository signupRequests() {
		return signupRequests(null);
	}

	@Override
	public DepositoRepository depositos() {
		return null;
	}

	@Override
	public SignupRequestRepository signupRequests(final TransactionalContext tx) {
		return new InMemorySignupRequestRepository();
	}


	@Override
	public TransactionalContext newTransactionalContext() {
		// in memory does not support transactions...
		return null;
	}

	@Override
	public MaterialRepository materiais() {
		return null;
	}

	@Override
	public MaquinaRepository maquinas() {
		return null;
	}

	@Override
	public LinhaProducaoRepository linhasProducao() {
		return null;
	}

	@Override
	public CategoriaMaterialRepository categoriasMaterial() {
		return null;
	}

    @Override
    public ProdutoRepository produtos() { return null; }

	@Override
	public ProdutoReportingRepository produtoReporting() { return null; }


	@Override
	public OrdemProducaoReportingRepository ordemProducaoReporting() { return null; }

    @Override
    public OrdemProducaoRepository ordensProducao() {
        return null;
    }

    @Override
    public ExecucaoOrdemProducaoRepository execucoesOrdensProducao() {
        return null;
    }

    @Override
    public IdParaIpRepository idsParaIps() {
        return null;
    }

    @Override
    public MensagemRepository mensagens() {
        return null;
    }

	@Override
	public AtividadeRepository atividades() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MovimentoRepository movimentos() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NotificacaoErroProcessamentoRepository notificacoesErroProcessamento() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NotificacaoErroProcessReportingRepository notificacaoErroProcessamentoReporting() { return null; }
    
}
