package eapli.shopfloor.app.backoffice.console.presentation.produtos;

import eapli.framework.visitor.Visitor;
import eapli.shopfloor.gestaoproducao.domain.Produto;

public class ProdutoPrinter implements Visitor<Produto>{

	@Override
	public void visit(Produto visitee) {
		System.out.printf("%-5s", visitee.identity());
	}
	
}
