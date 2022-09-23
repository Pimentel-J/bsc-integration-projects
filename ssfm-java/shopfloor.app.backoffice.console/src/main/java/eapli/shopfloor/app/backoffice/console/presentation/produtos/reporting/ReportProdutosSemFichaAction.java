package eapli.shopfloor.app.backoffice.console.presentation.produtos.reporting;


import eapli.framework.actions.Action;

/**
 *João Pimentel 
 */
public class ReportProdutosSemFichaAction implements Action {

    @Override
    public boolean execute() { return new ReportProdutosSemFichaUI().show(); }
}
