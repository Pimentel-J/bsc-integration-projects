package eapli.shopfloor.gestaoproducao.exportacao.application.ordemProducao;

import eapli.shopfloor.general.exportacao.ElementStringExporter;
import eapli.shopfloor.gestaoproducao.domain.OrdemProducao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Classe de implementação da Strategy XML String export
 *
 *João Pimentel 
 */
public class XmlOrdemProducaoStringExporter implements ElementStringExporter<OrdemProducao> {

    private final String HEADER = "<OrdensProducao>%n";
    private final String ENDING = "</OrdensProducao>%n";

    @Override
    public String header() { return HEADER; }

    @Override
    public Collection<String> element(OrdemProducao e) {
        Collection<String> ordens = new ArrayList<>();
        String dataEmissao = new SimpleDateFormat("dd-MM-yyyy").format(e.dataEmissao().getTime());
        String dataExec = new SimpleDateFormat("dd-MM-yyyy").format(e.dataPrevistaExec().getTime());

        ordens.add("<ordemProducao>%n");
        ordens.add(String.format("<idOrdem>%s</idOrdem>%n", e.identity().codigo()));
        ordens.add(String.format("<dataEmissao>%s</dataEmissao>%n", dataEmissao));
        ordens.add(String.format("<dataPrevistaExec>%s</dataPrevistaExec>%n", dataExec));
        ordens.add(String.format("<codigoFabProduto>%s</codigoFabProduto>%n", e.produto().identity().codigo()));
        ordens.add(String.format("<quantidade>%s</quantidade>%n", e.quantidade()));
        ordens.add(String.format("<estadoOrdem>%s</estadoOrdem>%n", e.estado()));
        if (e.linhaProducao() != null) {
            ordens.add(String.format("<idLinhaProducao>%s</idLinhaProducao>%n", e.linhaProducao().identity().codigo()));
        }
        ordens.add("</ordemProducao>%n");

        return ordens;
    }

    @Override
    public String ending() { return ENDING; }
}
