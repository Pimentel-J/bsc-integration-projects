package eapli.shopfloor.SPM.application;

import java.util.Calendar;

import eapli.framework.domain.events.DomainEvent;
import eapli.framework.infrastructure.eventpubsub.EventPublisher;
import eapli.framework.infrastructure.eventpubsub.impl.inprocess.InProcessPubSub;
import eapli.shopfloor.SPM.domain.events.ProcessamentoIniciadoEvent;


/**
 *1181455
 *
 */
public class ThreadProcessamentoRecorrente implements Runnable{
	
	private final EventPublisher dispatcher = InProcessPubSub.publisher();
	private final Calendar timestampProcessamento;
	private final long delay;
	private final long periodicidade;
	
	public ThreadProcessamentoRecorrente(Calendar timestampProcessamento, long delay, long periodicidade) {
		this.timestampProcessamento = timestampProcessamento;
		this.delay = delay;
		this.periodicidade = periodicidade;
	}
	
	@Override
	public void run() {
		
		try {
			if (delay > 0) {
				Thread.sleep(delay);
			}
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		while (true) {
			
			final DomainEvent event = new ProcessamentoIniciadoEvent(timestampProcessamento);
        dispatcher.publish(event);
        	
        	try {
				Thread.sleep(periodicidade);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
        
		}
		
        
		
	}
	
	
}
