package eapli.shopfloor.app.backoffice.console.presentation.depositos;

import eapli.shopfloor.gestaochaofabrica.application.RegistarDepositoController;
import eapli.framework.domain.repositories.IntegrityViolationException;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.util.Console;

public class RegistarDepositoUI extends AbstractUI {

    private final RegistarDepositoController controller = new RegistarDepositoController();

    @Override
    protected boolean doShow() {
        try {
            final String codigo = Console.readLine("Código:");
            final String descricao = Console.readLine("Descrição:");

            controller.registarDeposito(codigo, descricao);
        } catch (@SuppressWarnings("unused") final IntegrityViolationException ex) {
            System.out.println("Já existe um depósito com esse código/descrição.");
        }
        return false;
    }

    @Override
    public String headline() {
        return "Registar novo Depósito";
    }

}
