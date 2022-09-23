package eapli.shopfloor.app.backoffice.console.presentation.produtos;

import eapli.framework.visitor.Visitor;
import eapli.shopfloor.gestaoproducao.domain.Material;

public class MaterialPrinter implements Visitor<Material>{

	@Override
	public void visit(Material visitee) {
		System.out.printf("%-5s", visitee.identity());
	}

}
