/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.shopfloor.app.backoffice.console.presentation.ordensproducao;

import eapli.framework.domain.repositories.IntegrityViolationException;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.util.Console;
import eapli.shopfloor.gestaoproducao.application.IntroduzirOrdemProducaoController;
import java.util.Calendar;

/**
 *
 *.se.1181483
 */
public class IntroduzirOrdemProducaoUI extends AbstractUI {

    private final IntroduzirOrdemProducaoController controller = new IntroduzirOrdemProducaoController();
    private static final int TERMINAR_UC = 1;

    @Override
    protected boolean doShow() {

        int opcaoLoop;
        String codFabricoProd, idOrdem;

        do {
            codFabricoProd = Console.readLine("Produto - Código Único de Fabrico:");
        } while (!this.controller.adicionarProduto(codFabricoProd));
        do {
            idOrdem = Console.readLine("ID Ordem de Produção:");
        } while (!this.controller.adicionarIdOrdem(idOrdem));
        final Calendar dataEmissao = Console.readCalendar("Data de Emissão:");
        this.controller.adicionarDataEmissao(dataEmissao);
        Calendar dataPrevistaExec = null;
        do {
            dataPrevistaExec = Console.readCalendar("Data Prevista de Execução:");
        } while (!this.controller.adicionarDataPrevistaExecucao(dataPrevistaExec));
        final int quantidade = Console.readInteger("Quantidade de Produto:");
        this.controller.adicionarQuantidade(quantidade);
        do {
            String idEncomenda = Console.readLine("ID Encomenda:");
            this.controller.adicionarEncomenda(idEncomenda);
            opcaoLoop = Console.readInteger("Pressione 1 para terminar ou 2 para adicionar nova Encomenda:");
        } while (opcaoLoop != TERMINAR_UC);

        try {
            controller.introduzirOrdemProducao();
            System.out.println("Ordem de Produção corretamente inserida.");

        } catch (@SuppressWarnings("unused") final IntegrityViolationException ex) {
            System.out.println("Já existe uma ordem de produção com esse código.");
        }
        return false;
    }

    @Override
    public String headline() {
        return "Introduzir Nova Ordem de Produção";
    }

}
