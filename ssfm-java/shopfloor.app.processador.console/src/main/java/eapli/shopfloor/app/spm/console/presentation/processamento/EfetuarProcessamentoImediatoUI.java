package eapli.shopfloor.app.spm.console.presentation.processamento;

import java.util.Calendar;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.presentation.console.SelectWidget;
import eapli.framework.util.Console;
import eapli.shopfloor.SPM.application.EfetuarProcessamentoImediatoController;
import eapli.shopfloor.SPM.application.ResultadosProcessamentoPrinter;
import eapli.shopfloor.app.backoffice.console.presentation.maquinas.LinhaProducaoPrinter;
import eapli.shopfloor.gestaochaofabrica.domain.LinhaProducao;
import eapli.shopfloor.gestaoproducao.application.ListOrdensProducaoService;


/**
 *1181455
 */
public class EfetuarProcessamentoImediatoUI extends AbstractUI {

    private static final Logger LOGGER = LogManager.getLogger(EfetuarProcessamentoImediatoUI.class);
    
    private static final int OPCAO_PROCESSAR = 1;
	private static final int OPCAO_ADICIONAR_LINHA = 2;
    
    private final EfetuarProcessamentoImediatoController theController = new EfetuarProcessamentoImediatoController();
    
    private final ListOrdensProducaoService listOrdensProducao = new ListOrdensProducaoService();
    
    private static final String FORMATO_DATA = "dd-MM-yyyy HH:mm:ss";

    
    @Override
    protected boolean doShow() {
        	
    	final Calendar inicioIntervalo = Console.readCalendar("Introduzir inicio do intervalo temporal \n(ex: 24-02-2020 21:47:00)", FORMATO_DATA);
    	this.theController.definirInicioIntervaloProc(inicioIntervalo);
    	final Calendar fimIntervalo = Console.readCalendar("Introduzir final do intervalo temporal \n(ex: 24-02-2020 21:47:00)", FORMATO_DATA);
    	this.theController.definirFinalIntervaloProc(fimIntervalo);
    	final Iterable<LinhaProducao> linhasProducao = this.theController.listarLinhasProducao();
        final SelectWidget<LinhaProducao> selectorLinhasProducao = new SelectWidget<>("Selecionar linha:", linhasProducao,
                new LinhaProducaoPrinter());
			
        int opcaoLoop;
        do {
        	
        	selectorLinhasProducao.show();
    		this.theController.selecionarLinhaProducao(selectorLinhasProducao.selectedElement());
        	
        	do {
        		opcaoLoop = Console.readInteger("Pressione 1 para Processar ou 2 para adicionar nova Linha de Produção:");
        	} while (!(opcaoLoop == OPCAO_PROCESSAR || opcaoLoop == OPCAO_ADICIONAR_LINHA));
        	
        } while (opcaoLoop == OPCAO_ADICIONAR_LINHA);
    	
    	this.theController.executarServicoProcessamentoMensagens();
        
    	return true;
    }


    @Override
    public String headline() {
        return "Processamento Imediato";
    }

}
