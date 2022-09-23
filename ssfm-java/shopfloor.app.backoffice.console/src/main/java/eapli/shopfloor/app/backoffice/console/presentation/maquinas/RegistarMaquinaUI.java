package eapli.shopfloor.app.backoffice.console.presentation.maquinas;

import eapli.framework.domain.repositories.ConcurrencyException;
import eapli.framework.domain.repositories.IntegrityViolationException;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.presentation.console.ListWidget;
import eapli.framework.presentation.console.SelectWidget;
import eapli.framework.util.Console;
import eapli.shopfloor.gestaochaofabrica.application.RegistarMaquinaController;
import eapli.shopfloor.gestaochaofabrica.domain.LinhaProducao;
import eapli.shopfloor.gestaochaofabrica.domain.Maquina;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Calendar;

/**
 *Diogo
 */
public class RegistarMaquinaUI extends AbstractUI {

    private final RegistarMaquinaController controller = new RegistarMaquinaController();

    @Override
    protected boolean doShow() {

        final Iterable<LinhaProducao> listLinhasProducao = controller.linhasProducao();

        if (listLinhasProducao.iterator().hasNext()) {

            String idMaquina = Console.readLine("Identificador único da máquina:");
            while (controller.containsMaquina(idMaquina)) {
                System.out.println("Já existe uma máquina com este identificador");
                idMaquina = Console.readLine("Identificador único da máquina:");
            }

            final String numSerie = Console.readLine("Número de série da máquina:");
            final String descricao = Console.readLine("Descrição:");
            final Calendar dataInstalacao = Console.readCalendar("Data de instalação (dd-mm-yyyy):");
            final String marca = Console.readLine("Marca:");
            final String modelo = Console.readLine("Modelo:");
            final LinhaProducao linhaProducao = selectLinhaProducao(listLinhasProducao);

            try {
                final Integer sequencia = selectSequenciaLinhaProducao(linhaProducao);
                controller.updateSequenciaMaquinasLinhaProducao(linhaProducao, sequencia);
                controller.registarMaquina(idMaquina, numSerie, descricao, dataInstalacao, marca, modelo,
                        linhaProducao, sequencia);
                System.out.println("Máquina inserida com sucesso");
            } catch (ConcurrencyException | IntegrityViolationException ex) {
                if (ex instanceof ConcurrencyException) {
                    System.out.println(
                            "WARNING: That entity has already been changed or deleted since you last " + "read it");
                } else {
                    System.out.println("Não existem linhas de produção");
                }
            }
        } else {
            System.out.println("Não existem linhas de produção");
        }
        return false;
    }

    private LinhaProducao selectLinhaProducao(Iterable<LinhaProducao> linhasProducao) {
        final SelectWidget<LinhaProducao> selectorLinhaProducao = new SelectWidget<>(
                "Selecione uma Linha de " + "Produção", linhasProducao, new LinhaProducaoPrinter());
        selectorLinhaProducao.show();
        return selectorLinhaProducao.selectedElement();
    }

    private Integer selectSequenciaLinhaProducao(LinhaProducao linha) {
        int sequencia = 1;

        Iterable<Maquina> maquinas = controller.procuraPorLinhaProducao(linha);
        Integer ultimaPosicaoSequencia = controller.ultimaPosicaoOcupadaPorLinhaProducao(linha);

        if (ultimaPosicaoSequencia != -1) {
            final ListWidget<Maquina> listarSequencia = new ListWidget<>(
                    "Selecione uma posição na seguinte sequência de máquinas\n" +
                            String.format("%-9s%-15s", "Posição", "Código"), maquinas, new SequentialPrinter());
            listarSequencia.show();
            sequencia = Console.readInteger("Nova Posição:");
            sequencia = Math.min(sequencia, ultimaPosicaoSequencia + 1);

            return sequencia;
        } else {
            System.out.println("Linha de Produção sem máquinas - máquina inserida na posição 1");
        }
        return sequencia;
    }

    @Override
    public String headline() {
        return "Adicionar uma Máquina";
    }

}
