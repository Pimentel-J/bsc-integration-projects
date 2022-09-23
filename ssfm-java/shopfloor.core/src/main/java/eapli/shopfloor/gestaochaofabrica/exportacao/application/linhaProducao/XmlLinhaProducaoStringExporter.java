package eapli.shopfloor.gestaochaofabrica.exportacao.application.linhaProducao;

import eapli.shopfloor.general.exportacao.ElementStringExporter;
import eapli.shopfloor.gestaochaofabrica.domain.Deposito;
import eapli.shopfloor.gestaochaofabrica.domain.LinhaProducao;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Classe de implementação da Strategy XML String export
 *
 *João Pimentel 
 */
public class XmlLinhaProducaoStringExporter implements ElementStringExporter<LinhaProducao> {

    private final String HEADER = "<LinhasProducao>%n";
    private final String ENDING = "</LinhasProducao>%n";

    @Override
    public String header() { return HEADER; }
    
    @Override
    public Collection<String> element(LinhaProducao e) {
        Collection<String> linhas = new ArrayList<>();

        linhas.add("<linhaProducao>%n");
        linhas.add(String.format("<idLinhaProducao>%s</idLinhaProducao>%n", e.identity().codigo()));
        linhas.add("</linhaProducao>%n");

        return linhas;
    }

    @Override
    public String ending() { return ENDING; }
}
