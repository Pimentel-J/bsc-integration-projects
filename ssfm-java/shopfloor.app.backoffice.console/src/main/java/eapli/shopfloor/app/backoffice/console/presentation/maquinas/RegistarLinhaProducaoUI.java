package eapli.shopfloor.app.backoffice.console.presentation.maquinas;

import eapli.framework.domain.repositories.IntegrityViolationException;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.util.Console;
import eapli.shopfloor.gestaochaofabrica.application.RegistarLinhaProducaoController;


/**
 *Diogo
 */
public class RegistarLinhaProducaoUI extends AbstractUI {

    private final RegistarLinhaProducaoController controller = new RegistarLinhaProducaoController();

    @Override
    protected boolean doShow() {
        try {
            controller.listLinhasProducao();
            final String idLinhaProducao = Console.readLine("Identificador único da linha de produção:");
            controller.registarLinhaProducao(idLinhaProducao);
        } catch (@SuppressWarnings("unused") final IntegrityViolationException ex) {
            System.out.println("Já existe uma linha de produção com esse identificador único.");
        }
        return false;
    }


    @Override
    public String headline() {
        return "Adicionar uma Linha de Produção";
    }

}
