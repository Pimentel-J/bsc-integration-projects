/**
 *
 */
package eapli.shopfloor.infrastructure.persistence;

import eapli.framework.domain.repositories.TransactionalContext;
import eapli.framework.infrastructure.authz.domain.repositories.UserRepository;
import eapli.shopfloor.SCM.repositories.IdParaIpRepository;
import eapli.shopfloor.SCM.repositories.MensagemRepository;
import eapli.shopfloor.SPM.repositories.NotificacaoErroProcessamentoRepository;
import eapli.shopfloor.clientusermanagement.repositories.ClientUserRepository;
import eapli.shopfloor.clientusermanagement.repositories.SignupRequestRepository;
import eapli.shopfloor.gestaochaofabrica.repositories.DepositoRepository;
import eapli.shopfloor.gestaochaofabrica.repositories.LinhaProducaoRepository;
import eapli.shopfloor.gestaochaofabrica.repositories.MaquinaRepository;
import eapli.shopfloor.gestaoproducao.repositories.*;
import eapli.shopfloor.reporting.notificacoeserroprocessamento.repositories.NotificacaoErroProcessReportingRepository;
import eapli.shopfloor.reporting.ordensproducao.repositories.OrdemProducaoReportingRepository;
import eapli.shopfloor.reporting.produtos.repositories.ProdutoReportingRepository;

/**
 *Paulo Gandra Sousa and G35
 *
 */
public interface RepositoryFactory {

    /**
     * factory method to create a transactional context to use in the repositories
     *
     * @return
     */
    TransactionalContext newTransactionalContext();

    /**
     *
     * @param autoTx the transactional context to enrol
     * @return
     */
    UserRepository users(TransactionalContext autoTx);

    /**
     * repository will be created in auto transaction mode
     *
     * @return
     */
    UserRepository users();

    /**
     *
     * @param autoTx the transactional context to enroll
     * @return
     */
    ClientUserRepository clientUsers(TransactionalContext autoTx);

    /**
     * repository will be created in auto transaction mode
     *
     * @return
     */
    ClientUserRepository clientUsers();

    /**
     *
     * @param autoTx the transactional context to enroll
     * @return
     */
    SignupRequestRepository signupRequests(TransactionalContext autoTx);

    /**
     * repository will be created in auto transaction mode
     *
     * @return
     */
    SignupRequestRepository signupRequests();


    DepositoRepository depositos();

    ProdutoRepository produtos();

    OrdemProducaoRepository ordensProducao();

    ExecucaoOrdemProducaoRepository execucoesOrdensProducao();

    CategoriaMaterialRepository categoriasMaterial();

    MaterialRepository materiais();

    MaquinaRepository maquinas();

    LinhaProducaoRepository linhasProducao();

    ProdutoReportingRepository produtoReporting();

    OrdemProducaoReportingRepository ordemProducaoReporting();

    IdParaIpRepository idsParaIps();

    MensagemRepository mensagens();

	AtividadeRepository atividades();

	MovimentoRepository movimentos();

	NotificacaoErroProcessamentoRepository notificacoesErroProcessamento();

    NotificacaoErroProcessReportingRepository notificacaoErroProcessamentoReporting();

}
