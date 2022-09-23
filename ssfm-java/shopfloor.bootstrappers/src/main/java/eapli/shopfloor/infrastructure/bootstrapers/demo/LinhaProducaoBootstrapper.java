package eapli.shopfloor.infrastructure.bootstrapers.demo;

import eapli.framework.actions.Action;
import eapli.framework.domain.repositories.ConcurrencyException;
import eapli.framework.domain.repositories.IntegrityViolationException;
import eapli.framework.general.domain.model.Designation;
import eapli.shopfloor.general.CodigoAlfaCurto;
import eapli.shopfloor.general.Descricao;
import eapli.shopfloor.gestaochaofabrica.application.RegistarLinhaProducaoController;
import eapli.shopfloor.gestaochaofabrica.application.RegistarMaquinaController;
import eapli.shopfloor.gestaochaofabrica.domain.LinhaProducao;
import eapli.shopfloor.gestaochaofabrica.repositories.LinhaProducaoRepository;
import eapli.shopfloor.infrastructure.persistence.PersistenceContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

import static eapli.shopfloor.infrastructure.bootstrapers.TestDataConstants.*;

/**
 *João Pimentel 
 */
public class LinhaProducaoBootstrapper implements Action {
    private static final Logger LOGGER = LogManager.getLogger(LinhaProducaoBootstrapper.class);

    @Override
    public boolean execute() {
        register(ID_LINHA_PRODUCAO_LP1);
        register(ID_LINHA_PRODUCAO_LP2);
        register(ID_LINHA_PRODUCAO_LP3);
        return true;
    }

    private void register(final String id) {
        final RegistarLinhaProducaoController controlador = new RegistarLinhaProducaoController();

        try{
            controlador.registarLinhaProducao(id);
        }catch (final IntegrityViolationException | ConcurrencyException ex){
            LOGGER.warn("Assuming {} already exists (activate trace log for details)", id);
            LOGGER.trace("Assuming existing record", ex);
        }
    }

}
