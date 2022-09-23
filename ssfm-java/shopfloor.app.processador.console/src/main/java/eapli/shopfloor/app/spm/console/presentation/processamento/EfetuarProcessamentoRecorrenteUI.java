package eapli.shopfloor.app.spm.console.presentation.processamento;

import java.util.Calendar;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.util.Console;
import eapli.shopfloor.SPM.application.EfetuarProcessamentoRecorrenteController;


/**
 *1181455
 */
public class EfetuarProcessamentoRecorrenteUI extends AbstractUI {

    private static final Logger LOGGER = LogManager.getLogger(EfetuarProcessamentoRecorrenteUI.class);
    
    private final EfetuarProcessamentoRecorrenteController theController = new EfetuarProcessamentoRecorrenteController();
    
    private static final String FORMATO_DATA = "dd-MM-yyyy HH:mm:ss";
    private static final String FORMATO_DURACAO = "HH:mm:ss";

    
    @Override
    protected boolean doShow() {
        	
    	final Calendar inicioProcessamento = Console.readCalendar("Introduzir inicio do Processamento Recorrente \n(ex: 24-02-2020 21:47:00)", FORMATO_DATA);
    	final Calendar horasPeriod = Console.readCalendar("Introduzir periodicidade \n(ex: 00:15:00, para 15 minutos)", FORMATO_DURACAO);
        this.theController.executarServicoProcessamentoRecorrente(inicioProcessamento, horasPeriod);
    	return true;
    }


    @Override
    public String headline() {
        return "Processamento Imediato";
    }

}
