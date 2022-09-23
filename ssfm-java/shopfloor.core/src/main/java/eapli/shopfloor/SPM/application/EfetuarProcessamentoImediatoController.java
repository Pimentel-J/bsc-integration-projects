package eapli.shopfloor.SPM.application;

import java.util.Calendar;

import eapli.framework.validations.Preconditions;
import eapli.shopfloor.SPM.domain.ProcessadorMensagensService;
import eapli.shopfloor.gestaochaofabrica.application.ListLinhasProducaoService;
import eapli.shopfloor.gestaochaofabrica.domain.LinhaProducao;

public class EfetuarProcessamentoImediatoController {

	private final ListLinhasProducaoService listLinhasProducaoService = new ListLinhasProducaoService();
	private final ProcessadorMensagensService processadorMensagens = new ProcessadorMensagensService();
	private final ResultadosProcessamentoPrinter resultadosPrinter = new ResultadosProcessamentoPrinter();
	
	public void executarServicoProcessamentoMensagens() {
		this.resultadosPrinter.print(processadorMensagens.executarProcessamento());
	}
	
	public Iterable<LinhaProducao> listarLinhasProducao() {
        return this.listLinhasProducaoService.linhasProducaoComProcessamentoAtivo();
    }
	
	public void definirInicioIntervaloProc(Calendar dataHora) {
		Preconditions.nonNull(dataHora);
		processadorMensagens.definirInicioIntervaloProc(dataHora);
	}
	
	public void definirFinalIntervaloProc(Calendar dataHora) {
		Preconditions.nonNull(dataHora);
		processadorMensagens.definirFinalIntervaloProc(dataHora);
	}
	
	public void selecionarLinhaProducao(LinhaProducao linha) {
		if (linha != null) {
			processadorMensagens.selecionarLinhaProducao(linha);
		}
	}
	
}
