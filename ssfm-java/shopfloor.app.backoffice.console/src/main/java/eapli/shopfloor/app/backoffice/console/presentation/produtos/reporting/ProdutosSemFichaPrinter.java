package eapli.shopfloor.app.backoffice.console.presentation.produtos.reporting;

import eapli.framework.visitor.Visitor;
import eapli.shopfloor.reporting.produtos.dto.ProdutosSemFicha;

/**
 * Printer - Consultar Produtos Sem Ficha
 *
 *João Pimentel 
 */
public class ProdutosSemFichaPrinter implements Visitor<ProdutosSemFicha> {

    @Override
    public void visit(final ProdutosSemFicha visitee) {
        System.out.printf("%-15s%-30s", visitee.codigo, visitee.descricao);
    }

}
