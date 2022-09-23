package eapli.shopfloor.SPM.application;

import java.util.Calendar;
import java.util.TimerTask;

import eapli.framework.infrastructure.eventpubsub.EventPublisher;
import eapli.framework.infrastructure.eventpubsub.impl.inprocess.InProcessPubSub;


/**
 *1181455
 *
 */
public class TarefaProcessamentoRecorrente extends TimerTask {
	
	private final EventPublisher dispatcher = InProcessPubSub.publisher();
	private final Calendar timestampProcessamento;
	
	public TarefaProcessamentoRecorrente(Calendar timestampProcessamento) {
		this.timestampProcessamento = timestampProcessamento;
	}
	
	@Override
	public void run() {
		
//		ThreadProcessamentoRecorrente threadProcess = new ThreadProcessamentoRecorrente(timestampProcessamento);
//		Thread thread = new Thread(threadProcess);
//		thread.start();
//		
//		try {
//			thread.join();
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
		
	}
	
	
}
