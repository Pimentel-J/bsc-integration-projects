package eapli.shopfloor.infrastructure.bootstrapers.demo;

import eapli.framework.actions.Action;
import eapli.framework.domain.repositories.ConcurrencyException;
import eapli.framework.domain.repositories.IntegrityViolationException;
import eapli.shopfloor.gestaoproducao.application.IntroduzirOrdemProducaoController;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static eapli.shopfloor.infrastructure.bootstrapers.TestDataConstants.*;
import java.util.Calendar;

/**
 *.se.1181483
 */
public class OrdemProducaoBootstrapper implements Action {

    private static final Logger LOGGER = LogManager.getLogger(OrdemProducaoBootstrapper.class);
    

    @Override
    public boolean execute() {
    	System.out.println("execute ordemProducaoBootstrapper");
        register(CODIGO_FABRICO_PRD_1, ID_ORDEM_PRD_1, DATA_EMISSAO_1, DATA_PREV_EXEC_1, QTD_ORDEM_PRD_1, ID_ENCOMENDA_1);
        register(CODIGO_FABRICO_PRD_2, ID_ORDEM_PRD_2, DATA_EMISSAO_2, DATA_PREV_EXEC_2, QTD_ORDEM_PRD_2, ID_ENCOMENDA_2);
        return true;
    }

    private void register(final String codigoFabrico, final String idOrdem, final Calendar dataEmissao,
            final Calendar dataPrevExec, final int quantidade, final String idEncomenda) {
        final IntroduzirOrdemProducaoController controlador = new IntroduzirOrdemProducaoController();
        
        controlador.adicionarProduto(codigoFabrico);
        controlador.adicionarIdOrdem(idOrdem);
        controlador.adicionarDataEmissao(dataEmissao);
        controlador.adicionarDataPrevistaExecucao(dataPrevExec);
        controlador.adicionarQuantidade(quantidade);
        controlador.adicionarEncomenda(idEncomenda);

        try {
            controlador.introduzirOrdemProducao();
        } catch (final IntegrityViolationException | ConcurrencyException ex) {
            LOGGER.warn("Assuming {} already exists (activate trace log for details)", codigoFabrico);
            LOGGER.trace("Assuming existing record", ex);
        }
    }

}
