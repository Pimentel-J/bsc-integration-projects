package eapli.shopfloor.app.backoffice.console.presentation.exportacao;

import eapli.framework.actions.Action;

/**
 *João Pimentel 
 */
public class ExportarFicheiroReportAction implements Action {

    @Override
    public boolean execute() { return new ExportarFicheiroReportUI().show(); }

}
