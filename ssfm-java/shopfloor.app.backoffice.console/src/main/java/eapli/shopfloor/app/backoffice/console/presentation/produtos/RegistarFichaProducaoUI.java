package eapli.shopfloor.app.backoffice.console.presentation.produtos;

import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.presentation.console.SelectWidget;
import eapli.framework.util.Console;
import eapli.shopfloor.gestaoproducao.application.RegistarFichaProducaoController;
import eapli.shopfloor.gestaoproducao.domain.Material;
import eapli.shopfloor.gestaoproducao.domain.Produto;

public class RegistarFichaProducaoUI extends AbstractUI {

	private final RegistarFichaProducaoController theController = new RegistarFichaProducaoController();
	private static final int TERMINAR_UC = 1;
	private static final int OPCAO_LOOP_ADICIONAR_MP = 2;
	private static final int OPCAO_MP_MATERIAL = 1;
	private static final int OPCAO_MP_PRODUTO = 2;

    @Override
    protected boolean doShow() {
    	
    	// Listar produtos e pedir para selecionar o Produto ao qual pretende associar esta Ficha de Producao
    	
        final Iterable<Produto> produtos = this.theController.listarProdutos();
        final SelectWidget<Produto> selectorProdutos = new SelectWidget<>("Produtos:", produtos,
                new ProdutoPrinter());
        selectorProdutos.show();
        final Produto produtoReferencia = selectorProdutos.selectedElement();
        
        this.theController.associarAProduto(produtoReferencia);
        
        
        // Perguntar qual a quantidade standard de Produto
        
        final String unidade = produtoReferencia.unidade().name(); 
        final int quantidadeStandard = Console.readInteger("Quantidade standard (" + unidade + ")");
        
        this.theController.adicionarQtdStandard(quantidadeStandard);
        
        
        // Falta implementar: Verificar se o produto já tem Ficha de Produção e, se sim, perguntar se quer substituir
        
        
        // Adicionar uma ou mais matérias-primas à Ficha de Producao
        
        final Iterable<Material> materiais = this.theController.listarMateriais();
        final SelectWidget<Material> selectorMateriais = new SelectWidget<>("Materiais:", materiais,
                new MaterialPrinter());
        
        int opcaoLoop, opcaoMP;
        do {
        	
        	do {
        		opcaoMP = Console.readInteger("Adicionar Matéria-Prima \n Selecionar Material: 1; Produto: 2");
        	} while (!(opcaoMP == OPCAO_MP_MATERIAL || opcaoMP == OPCAO_MP_PRODUTO));
        	
        	if (opcaoMP == OPCAO_MP_MATERIAL) {
        		selectorMateriais.show();
        		Material materialSelecionado = selectorMateriais.selectedElement();
        		double quantidadeMP = Console.readDouble("Quantidade (" + unidade + ")");
        		this.theController.adicionarMateriaPrima(materialSelecionado, quantidadeMP);
        	} else if (opcaoMP == OPCAO_MP_PRODUTO) {
        		selectorProdutos.show();
        		Produto produtoSelecionado = selectorProdutos.selectedElement();
        		double quantidadeMP = Console.readDouble("Quantidade (" + unidade + ")");
        		this.theController.adicionarMateriaPrima(produtoSelecionado, quantidadeMP);
        	}
        	
        	do {
        		opcaoLoop = Console.readInteger("Pressione 1 para terminar ou 2 para adicionar nova Matéria-Prima:");
        	} while (!(opcaoLoop == TERMINAR_UC || opcaoLoop == OPCAO_LOOP_ADICIONAR_MP));
        	
        	
        } while (opcaoLoop == OPCAO_LOOP_ADICIONAR_MP);
        
        
        // Criação da Ficha de Produção
        
        try {
            this.theController.registarFichaProducao();
            System.out.printf("Ficha de Produção registada\n");
        } catch (Exception e) {
            System.out.println(e);
        }

        return false;
    }

    @Override
    public String headline() {
        return "Registar Ficha de Produção";
    }
	
}
