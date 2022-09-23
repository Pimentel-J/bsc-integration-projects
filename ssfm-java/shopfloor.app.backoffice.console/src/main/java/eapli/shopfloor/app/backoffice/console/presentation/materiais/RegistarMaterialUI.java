package eapli.shopfloor.app.backoffice.console.presentation.materiais;

import eapli.framework.domain.repositories.IntegrityViolationException;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.presentation.console.SelectWidget;
import eapli.framework.util.Console;
import eapli.shopfloor.gestaoproducao.application.RegisterMaterialController;
import eapli.shopfloor.gestaoproducao.domain.CategoriaMaterial;

/**
 *João Pimentel 
 */
public class RegistarMaterialUI extends AbstractUI {

    private final RegisterMaterialController controller = new RegisterMaterialController();

    @Override
    protected boolean doShow() {

        String codigo = Console.readLine("Código:");
        while (controller.containsMaterial(codigo)) {
            System.out.println("Já existe um material com este código");
            codigo = Console.readLine("Código:");
        }
        final String descricao = Console.readLine("Descrição:");
        final String nomeFicha = Console.readLine("Nome Ficha Técnica:");
        final String pathFicha = Console.readLine("Path Ficha Técnica:");
        final CategoriaMaterial categoria = selectCategoriaMaterial();

        try {
            controller.registerMaterial(codigo, descricao, nomeFicha, pathFicha, categoria);
            System.out.println("Material inserido com sucesso! :)");
        } catch (@SuppressWarnings("unused") final IntegrityViolationException ex) {
            System.out.println("Já existe um material com esse código/descrição.");
        }
        return false;
    }

    private CategoriaMaterial selectCategoriaMaterial() {
        System.out.println("Lista de Categorias - Selecione uma Categoria de Material");
        final Iterable<CategoriaMaterial> listCategoriasMaterial = controller.listCategoriasMaterial();
        final SelectWidget<CategoriaMaterial> selectorCategoriaMaterial = new SelectWidget<>(
                "Selecione uma Categoria de Material", listCategoriasMaterial, new CategoriaMaterialPrinter());
        selectorCategoriaMaterial.show();
        return selectorCategoriaMaterial.selectedElement();
    }

    @Override
    public String headline() {
        return "Adicionar um Material";
    }

}
