package eapli.shopfloor.gestaochaofabrica.exportacao.application.deposito;

import eapli.shopfloor.general.exportacao.ElementStringExporter;
import eapli.shopfloor.gestaochaofabrica.domain.Deposito;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Classe de implementação da Strategy XML String export
 *
 *João Pimentel 
 */
public class XmlDepositoStringExporter implements ElementStringExporter<Deposito> {

    private final String HEADER = "<Depositos>%n";
    private final String ENDING = "</Depositos>%n";

    @Override
    public String header() { return HEADER; }

    @Override
    public Collection<String> element(Deposito e) {
        Collection<String> depositos = new ArrayList<>();

        depositos.add("<deposito>%n");
        depositos.add(String.format("<codigo>%s</codigo>%n", e.identity()));
        depositos.add(String.format("<descricao>%s</descricao>%n", e.descricao()));
        depositos.add("</deposito>%n");

        return depositos;
    }

    @Override
    public String ending() { return ENDING; }
}
