package eapli.shopfloor.app.backoffice.console.presentation.materiais;

import eapli.framework.domain.repositories.IntegrityViolationException;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.util.Console;
import eapli.shopfloor.gestaoproducao.application.RegisterCategoriaMaterialController;

/**
 *João Pimentel 
 */
public class RegistarCategoriaMaterialUI extends AbstractUI {

    private final RegisterCategoriaMaterialController controller = new RegisterCategoriaMaterialController();

    @Override
    protected boolean doShow() {

        String codigo = Console.readLine("Código:");
        while (controller.containsCategoriaMaterial(codigo)) {
            System.out.println("Já existe uma categoria com este código");
            codigo = Console.readLine("Código:");
        }
        final String descricao = Console.readLine("Descrição:");

        try {
            controller.registerCategoriaMaterial(codigo, descricao);
            System.out.println("Categoria de Material inserida com sucesso! :)");
        } catch (@SuppressWarnings("unused") final IntegrityViolationException ex) {
            System.out.println("Já existe uma categoria com esse código.");
        }
        return false;
    }

    @Override
    public String headline() {
        return "Definir nova Categoria Material";
    }

}
