package eapli.shopfloor.SPM.application;

import java.util.Calendar;

import eapli.shopfloor.util.Tempo;

public class EfetuarProcessamentoRecorrenteController {

	
	public void executarServicoProcessamentoRecorrente(Calendar inicio, Calendar periodicidade) {
		EfetuarProcessamentoRecorrenteService servico = new EfetuarProcessamentoRecorrenteService(inicio, Tempo.calendarToDuration(periodicidade));
		servico.correrServico();
	}
	
}
