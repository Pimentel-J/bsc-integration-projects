package eapli.shopfloor.infrastructure.bootstrapers.demo;

import eapli.framework.actions.Action;
import eapli.framework.domain.repositories.ConcurrencyException;
import eapli.framework.domain.repositories.IntegrityViolationException;
import eapli.shopfloor.general.CodigoAlfaCurto;
import eapli.shopfloor.gestaochaofabrica.application.RegistarDepositoController;
import eapli.shopfloor.gestaoproducao.application.RegisterMaterialController;
import eapli.shopfloor.gestaoproducao.domain.CategoriaMaterial;
import eapli.shopfloor.gestaoproducao.domain.FichaTecnicaMaterial;
import eapli.shopfloor.gestaoproducao.repositories.CategoriaMaterialRepository;
import eapli.shopfloor.infrastructure.persistence.PersistenceContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static eapli.shopfloor.infrastructure.bootstrapers.TestDataConstants.*;

/**
 *Diogo
 */
public class DepositoBootstrapper implements Action {
    private static final Logger LOGGER = LogManager.getLogger(DepositoBootstrapper.class);




    @Override
    public boolean execute() {
        register(ID_DEPOSITO_DP1 ,DESCRICAO_DEPOSITO_DP1);
        register(ID_DEPOSITO_DP2, DESCRICAO_DEPOSITO_DP2);
        register(ID_DEPOSITO_DP3,DESCRICAO_DEPOSITO_DP3);
        register(ID_DEPOSITO_L030,DESCRICAO_DEPOSITO_L030);
        register(ID_DEPOSITO_L040,DESCRICAO_DEPOSITO_L040);
        register(ID_DEPOSITO_L042,DESCRICAO_DEPOSITO_L042);
        return true;
    }

    private void register(final String codigo, final String descricao) {
        final RegistarDepositoController controller = new RegistarDepositoController();

        try{
            controller.registarDeposito(codigo, descricao);
        }catch (final IntegrityViolationException | ConcurrencyException ex){
            LOGGER.warn("Assuming {} already exists (activate trace log for details)", codigo);
            LOGGER.trace("Assuming existing record",
                    descricao);
        }
    }

}
