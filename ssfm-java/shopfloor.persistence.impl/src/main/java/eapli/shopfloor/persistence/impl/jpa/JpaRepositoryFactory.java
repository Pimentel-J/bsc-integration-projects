package eapli.shopfloor.persistence.impl.jpa;

import eapli.shopfloor.core.Application;
import eapli.shopfloor.clientusermanagement.repositories.SignupRequestRepository;
import eapli.shopfloor.gestaochaofabrica.repositories.DepositoRepository;
import eapli.shopfloor.gestaochaofabrica.repositories.LinhaProducaoRepository;
import eapli.shopfloor.gestaochaofabrica.repositories.MaquinaRepository;
import eapli.shopfloor.gestaoproducao.repositories.AtividadeRepository;
import eapli.shopfloor.gestaoproducao.repositories.CategoriaMaterialRepository;
import eapli.shopfloor.gestaoproducao.repositories.MaterialRepository;
import eapli.shopfloor.gestaoproducao.repositories.MovimentoRepository;
import eapli.shopfloor.infrastructure.persistence.RepositoryFactory;
import eapli.framework.domain.repositories.TransactionalContext;
import eapli.framework.infrastructure.authz.domain.repositories.UserRepository;
import eapli.framework.infrastructure.authz.repositories.impl.JpaAutoTxUserRepository;
import eapli.framework.infrastructure.repositories.impl.jpa.JpaAutoTxRepository;
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
 * Created by nuno on 21/03/16.
 */
public class JpaRepositoryFactory implements RepositoryFactory{

    @Override
    public UserRepository users(final TransactionalContext autoTx) {
        return new JpaAutoTxUserRepository(autoTx);
    }

    @Override
    public UserRepository users() {
        return new JpaAutoTxUserRepository(Application.settings().getPersistenceUnitName(),
                Application.settings().getExtendedPersistenceProperties());
    }

    @Override
    public JpaClientUserRepository clientUsers(final TransactionalContext autoTx) {
        return new JpaClientUserRepository(autoTx);
    }

    @Override
    public JpaClientUserRepository clientUsers() {
        return new JpaClientUserRepository(Application.settings().getPersistenceUnitName());
    }

    @Override
    public SignupRequestRepository signupRequests(final TransactionalContext autoTx) {
        return new JpaSignupRequestRepository(autoTx);
    }

    @Override
    public SignupRequestRepository signupRequests() {
        return new JpaSignupRequestRepository(Application.settings().getPersistenceUnitName());
    }

    @Override
    public DepositoRepository depositos() {
        return new JpaDepositoRepository();
    }

    @Override
    public TransactionalContext newTransactionalContext() {
        return JpaAutoTxRepository.buildTransactionalContext(Application.settings().getPersistenceUnitName(),
                Application.settings().getExtendedPersistenceProperties());
    }

    @Override
    public ProdutoRepository produtos() {
        return new JpaProdutoRepository();
    }

    @Override
    public MaterialRepository materiais() {
        return new JpaMaterialRepository();
    }

    @Override
    public MaquinaRepository maquinas() {
        return new JpaMaquinaRepository();
    }

    @Override
    public CategoriaMaterialRepository categoriasMaterial() {
        return new JpaCategoriaMaterialRepository();
    }

    @Override
    public LinhaProducaoRepository linhasProducao() {
        return new JpaLinhaProducaoRepository();
    }

    @Override
    public ProdutoReportingRepository produtoReporting() {
        return new JpaProdutoReportingRepository();
    }

    @Override
    public OrdemProducaoReportingRepository ordemProducaoReporting() {
        return new JpaOrdemProducaoReportingRepository();
    }

    @Override
    public OrdemProducaoRepository ordensProducao() {
        return new JpaOrdemProducaoRepository();
    }

    @Override
    public ExecucaoOrdemProducaoRepository execucoesOrdensProducao(){
        return new JpaExecucaoOrdemProducaoRepository();
    }
    
    @Override
    public IdParaIpRepository idsParaIps(){
        return new JpaIdParaIpRepository();
    }
    
    @Override
    public MensagemRepository mensagens(){
        return new JpaMensagemRepository();
    }
    
    @Override
    public AtividadeRepository atividades(){
        return new JpaAtividadeRepository();
    }
    
    @Override
    public MovimentoRepository movimentos(){
        return new JpaMovimentoRepository();
    }

	@Override
	public NotificacaoErroProcessamentoRepository notificacoesErroProcessamento() {
		return new JpaNotificacaoErroProcessamentoRepository();
	}

    @Override
    public NotificacaoErroProcessReportingRepository notificacaoErroProcessamentoReporting() {
        return new JpaNotificacaoErroProcessReportingRepository();
    }

}
