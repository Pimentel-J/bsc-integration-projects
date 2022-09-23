package eapli.shopfloor.gestaoproducao.exportacao.application.categoriamaterial;

import eapli.shopfloor.general.exportacao.ElementStringExporter;
import eapli.shopfloor.gestaoproducao.domain.CategoriaMaterial;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Classe de implementação da Strategy XML String export
 *
 *João Pimentel 
 */
public class XmlCategoriaMaterialStringExporter implements ElementStringExporter<CategoriaMaterial> {

    private final String HEADER = "<CategoriasMaterial>%n";
    private final String ENDING = "</CategoriasMaterial>%n";

    @Override
    public String header() { return HEADER; }

    @Override
    public Collection<String> element(CategoriaMaterial e) {
        Collection<String> categorias = new ArrayList<>();

        categorias.add("<categoriaMaterial>%n");
        categorias.add(String.format("<codigoCatMaterial>%s</codigoCatMaterial>%n", e.identity().codigo()));
        categorias.add(String.format("<descricao>%s</descricao>%n", e.descricao()));
        categorias.add("</categoriaMaterial>%n");

        return categorias;
    }

    @Override
    public String ending() { return ENDING; }
}
