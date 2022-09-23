package eapli.shopfloor.SPM.application.eventhandlers;

import eapli.shopfloor.SPM.application.ResultadosProcessamentoPrinter;
import eapli.shopfloor.SPM.domain.ProcessadorMensagensService;
import eapli.shopfloor.SPM.domain.events.ProcessamentoIniciadoEvent;

public class EfetuarProcessamentoOnProcessamentoIniciadoController {

	private final ProcessadorMensagensService processadorMensagens = new ProcessadorMensagensService();
	private final ResultadosProcessamentoPrinter resultadosPrinter = new ResultadosProcessamentoPrinter();
	
	public void executarServicoProcessamentoMensagens(final ProcessamentoIniciadoEvent evento) {
		
		processadorMensagens.definirFinalIntervaloProc(evento.timestampProcessamento());
		this.resultadosPrinter.print(processadorMensagens.executarProcessamento());
	}
	
}
