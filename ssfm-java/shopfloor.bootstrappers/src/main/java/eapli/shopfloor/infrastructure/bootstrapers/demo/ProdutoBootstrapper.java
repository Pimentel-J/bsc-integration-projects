package eapli.shopfloor.infrastructure.bootstrapers.demo;

import eapli.framework.actions.Action;
import eapli.framework.domain.repositories.ConcurrencyException;
import eapli.framework.domain.repositories.IntegrityViolationException;

import eapli.shopfloor.gestaoproducao.application.AdicionarProdutoController;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static eapli.shopfloor.infrastructure.bootstrapers.TestDataConstants.*;

/**
 *Diogo
 */
public class ProdutoBootstrapper implements Action {

    private static final Logger LOGGER = LogManager.getLogger(ProdutoBootstrapper.class);

    @Override
    public boolean execute() {
        register(CODIGO_FABRICO_PRD1, CODIGO_COMERCIAL_PRD1, DESCRICAO_BREVE_PRD1, DESCRICAO_COMPLETA_PRD1, UNIDADE_PRD1, CATEGORIA_PRD1, DESCRICAO_CATEGORIA_PRD1);
        register(CODIGO_FABRICO_PRD2, CODIGO_COMERCIAL_PRD2, DESCRICAO_BREVE_PRD2, DESCRICAO_COMPLETA_PRD2, UNIDADE_PRD2, CATEGORIA_PRD2, DESCRICAO_CATEGORIA_PRD2);
        register(CODIGO_FABRICO_PRD3, CODIGO_COMERCIAL_PRD3, DESCRICAO_BREVE_PRD3, DESCRICAO_COMPLETA_PRD3, UNIDADE_PRD3, CATEGORIA_PRD3, DESCRICAO_CATEGORIA_PRD3);
        register(CODIGO_FABRICO_PRD4, CODIGO_COMERCIAL_PRD4, DESCRICAO_BREVE_PRD4, DESCRICAO_COMPLETA_PRD4, UNIDADE_PRD4, CATEGORIA_PRD4, DESCRICAO_CATEGORIA_PRD4);
        register(CODIGO_FABRICO_PRD5, CODIGO_COMERCIAL_PRD5, DESCRICAO_BREVE_PRD5, DESCRICAO_COMPLETA_PRD5, UNIDADE_PRD5, CATEGORIA_PRD5, DESCRICAO_CATEGORIA_PRD5);
        register(CODIGO_FABRICO_PRD6, CODIGO_COMERCIAL_PRD6, DESCRICAO_BREVE_PRD6, DESCRICAO_COMPLETA_PRD6, UNIDADE_PRD6, CATEGORIA_PRD6, DESCRICAO_CATEGORIA_PRD6);
        return true;
    }

    private void register(final String codigoFabrico, final String codigoComercial, final String descricaoBreve, final String descricaoCompleta,
            final String unidadeProduto, final String codigoCategoria, final String descricaoCategoria) {
        final AdicionarProdutoController controlador = new AdicionarProdutoController();

        try {
            controlador.adicionarProduto(codigoFabrico, codigoComercial, descricaoBreve, descricaoCompleta,
                    unidadeProduto, codigoCategoria, descricaoCategoria);
        } catch (final IntegrityViolationException | ConcurrencyException ex) {
            LOGGER.warn("Assuming {} already exists (activate trace log for details)", codigoFabrico);
            LOGGER.trace("Assuming existing record", ex);
        }
    }

}
