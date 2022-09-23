package eapli.shopfloor.infrastructure.bootstrapers.demo;

import eapli.framework.actions.Action;
import eapli.shopfloor.gestaoproducao.application.RegisterCategoriaMaterialController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import eapli.framework.domain.repositories.ConcurrencyException;
import eapli.framework.domain.repositories.IntegrityViolationException;

import static eapli.shopfloor.infrastructure.bootstrapers.TestDataConstants.*;

/**
 *João Pimentel 
 */
public class CategoriaMaterialBootstrapper implements Action {
    private static final Logger LOGGER = LogManager.getLogger(CategoriaMaterialBootstrapper.class);

    @Override
    public boolean execute() {
        register(CATEGORIA_CODIGO_MADEIRA, CATEGORIA_DESCRICAO_MADEIRA);
        register(CATEGORIA_CODIGO_PEDRA, CATEGORIA_DESCRICAO_PEDRA);
        register(CATEGORIA_CODIGO_COBRE, CATEGORIA_DESCRICAO_COBRE);
        return true;
    }

    private void register(final String codigo, final String descricao) {
        final RegisterCategoriaMaterialController controlador = new RegisterCategoriaMaterialController();

        try{
            controlador.registerCategoriaMaterial(codigo, descricao);
        }catch (final IntegrityViolationException | ConcurrencyException ex){
            LOGGER.warn("Assuming {} already exists (activate trace log for details)", codigo);
            LOGGER.trace("Assuming existing record", ex);
        }
    }

}
