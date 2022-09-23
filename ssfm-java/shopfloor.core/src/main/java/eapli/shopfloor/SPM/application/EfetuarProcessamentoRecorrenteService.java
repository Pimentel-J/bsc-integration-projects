package eapli.shopfloor.SPM.application;

import java.time.Duration;
import java.util.Calendar;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class EfetuarProcessamentoRecorrenteService {

	private Calendar inicio;
	private Duration periodicidade;
	
    public EfetuarProcessamentoRecorrenteService(Calendar inicio, Duration periodicidade) {
    	this.inicio = inicio;
    	this.periodicidade = periodicidade;
    	
    }
    
    public void correrServico() {
//    	TimerTask inicioProcessamento = new TarefaProcessamentoRecorrente(inicio);
//    	Timer timer = new Timer();
//    	
//    	timer.schedule(inicioProcessamento, inicio.getTime());
    	
    	long delay = Duration.between(Calendar.getInstance(new Locale("pt")).toInstant(), inicio.toInstant()).toMillis();
    	
    	ThreadProcessamentoRecorrente threadProcess = new ThreadProcessamentoRecorrente(inicio, delay, periodicidade.toMillis());
		Thread thread = new Thread(threadProcess);
		thread.start();
		
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
    	
    }
	
}
