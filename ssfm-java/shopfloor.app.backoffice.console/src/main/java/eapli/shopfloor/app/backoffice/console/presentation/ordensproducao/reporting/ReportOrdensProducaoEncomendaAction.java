package eapli.shopfloor.app.backoffice.console.presentation.ordensproducao.reporting;

import eapli.framework.actions.Action;

/**
 *João Pimentel 
 */
public class ReportOrdensProducaoEncomendaAction implements Action {

    @Override
    public boolean execute() { return new ReportOrdensProducaoEncomendaUI().show(); }

}
