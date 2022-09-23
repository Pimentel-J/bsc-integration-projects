
package eapli.shopfloor.SPM.application.eventhandlers;

import eapli.framework.domain.events.DomainEvent;
import eapli.framework.infrastructure.eventpubsub.EventHandler;
import eapli.framework.validations.Preconditions;
import eapli.shopfloor.SPM.domain.events.ProcessamentoIniciadoEvent;

/**
 *1181455
 *
 */
public class ProcessamentoIniciadoWatchDog implements EventHandler {

    @Override
    public void onEvent(final DomainEvent domainevent) {
        Preconditions.ensure(domainevent instanceof ProcessamentoIniciadoEvent);

        final ProcessamentoIniciadoEvent evento = (ProcessamentoIniciadoEvent) domainevent;
        final EfetuarProcessamentoOnProcessamentoIniciadoController controller = new EfetuarProcessamentoOnProcessamentoIniciadoController();
        controller.executarServicoProcessamentoMensagens(evento);
    }
}
