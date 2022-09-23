package eapli.shopfloor.infrastructure.bootstrapers.demo;

import eapli.framework.actions.Action;
import eapli.framework.domain.repositories.ConcurrencyException;
import eapli.framework.domain.repositories.IntegrityViolationException;
import eapli.framework.time.util.Calendars;
import eapli.shopfloor.general.CodigoAlfaCurto;
import eapli.shopfloor.gestaochaofabrica.application.RegistarMaquinaController;
import eapli.shopfloor.gestaochaofabrica.domain.LinhaProducao;
import eapli.shopfloor.gestaochaofabrica.repositories.LinhaProducaoRepository;
import eapli.shopfloor.infrastructure.persistence.PersistenceContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Calendar;

import static eapli.shopfloor.infrastructure.bootstrapers.TestDataConstants.*;

/**
 *João Pimentel 
 */
public class MaquinaBootstrapper implements Action {
    private static final Logger LOGGER = LogManager.getLogger(MaquinaBootstrapper.class);

    private final LinhaProducaoRepository lpRepo = PersistenceContext.repositories().linhasProducao();

    private LinhaProducao getLinhaProducao(final String codigo) {
        return lpRepo.ofIdentity(CodigoAlfaCurto.valueOf(codigo)).orElseThrow(IllegalStateException::new);
    }

    @Override
    public boolean execute() {
        register(ID_MAQUINA_M1, NUM_SERIE_M1, DESCRICAO_MAQUINA_M1, DATA_INSTALACAO_M1, MARCA_M1, MODELO_M1,
                getLinhaProducao(ID_LINHA_PRODUCAO_LP1), SEQUENCIA_M1, ID_PROTOCOLO_M1);
        register(ID_MAQUINA_M2, NUM_SERIE_M2, DESCRICAO_MAQUINA_M2, DATA_INSTALACAO_M2, MARCA_M2, MODELO_M2,
                getLinhaProducao(ID_LINHA_PRODUCAO_LP1), SEQUENCIA_M2, ID_PROTOCOLO_M2);
        register(ID_MAQUINA_M3, NUM_SERIE_M3, DESCRICAO_MAQUINA_M3, DATA_INSTALACAO_M3, MARCA_M3, MODELO_M3,
                getLinhaProducao(ID_LINHA_PRODUCAO_LP1), SEQUENCIA_M3, ID_PROTOCOLO_M3);
        register(ID_MAQUINA_M4, NUM_SERIE_M4, DESCRICAO_MAQUINA_M4, DATA_INSTALACAO_M4, MARCA_M4, MODELO_M4,
                getLinhaProducao(ID_LINHA_PRODUCAO_LP2), SEQUENCIA_M4, ID_PROTOCOLO_M4);
        register(ID_MAQUINA_M5, NUM_SERIE_M5, DESCRICAO_MAQUINA_M5, DATA_INSTALACAO_M5, MARCA_M5, MODELO_M5,
                getLinhaProducao(ID_LINHA_PRODUCAO_LP2), SEQUENCIA_M5, ID_PROTOCOLO_M5);
        register(ID_MAQUINA_M6, NUM_SERIE_M6, DESCRICAO_MAQUINA_M6, DATA_INSTALACAO_M6, MARCA_M6, MODELO_M6,
                getLinhaProducao(ID_LINHA_PRODUCAO_LP2), SEQUENCIA_M6, ID_PROTOCOLO_M6);
        return true;
    }

    private void register(final String idMaquina, final String numSerie, final String descricao,
                          final String dataInstalacao, final String marca, final String modelo,
                          final LinhaProducao linhaProducao, final Integer sequencia, final int idProtocolo) {
        final RegistarMaquinaController controlador = new RegistarMaquinaController();

        Calendar date = Calendars.parse(dataInstalacao, "dd-MM-yyyy");

        try {
            controlador.registarMaquinaComProto(idMaquina, numSerie, descricao, date, marca, modelo, linhaProducao, sequencia, idProtocolo);
        } catch (final IntegrityViolationException | ConcurrencyException ex) {
            LOGGER.warn("Assuming {} already exists (activate trace log for details)", idMaquina);
            LOGGER.trace("Assuming existing record", ex);
        }
    }
}