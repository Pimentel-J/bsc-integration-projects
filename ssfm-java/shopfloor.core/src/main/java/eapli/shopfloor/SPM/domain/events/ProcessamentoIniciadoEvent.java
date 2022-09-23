
package eapli.shopfloor.SPM.domain.events;

import java.util.Calendar;

import eapli.framework.domain.events.DomainEventBase;

/**
 *1181455
 */
public class ProcessamentoIniciadoEvent extends DomainEventBase {
	
	private final Calendar timestampProcessamento;
	
    private static final long serialVersionUID = 1L;

    public ProcessamentoIniciadoEvent(Calendar fimIntervaloProcessamento) {
    	this.timestampProcessamento = fimIntervaloProcessamento;
    }
    
    public Calendar timestampProcessamento() {
    	return this.timestampProcessamento;
    }

}
