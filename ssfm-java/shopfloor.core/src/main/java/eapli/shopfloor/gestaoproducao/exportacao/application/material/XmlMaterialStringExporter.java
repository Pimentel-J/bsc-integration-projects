package eapli.shopfloor.gestaoproducao.exportacao.application.material;

import eapli.shopfloor.general.exportacao.ElementStringExporter;
import eapli.shopfloor.gestaoproducao.domain.Material;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Classe de implementação da Strategy XML String export
 *
 *João Pimentel 
 */
public class XmlMaterialStringExporter implements ElementStringExporter<Material> {

    private final String HEADER = "<Materiais>%n";
    private final String ENDING = "</Materiais>%n";

    @Override
    public String header() { return HEADER; }

    @Override
    public Collection<String> element(Material e) {
        Collection<String> materiais = new ArrayList<>();

        materiais.add("<material>%n");
        materiais.add(String.format("<codigoMaterial>%s</codigoMaterial>%n", e.identity()));
        materiais.add(String.format("<descricao>%s</descricao>%n", e.descricaoMaterial()));
        materiais.add(String.format("<codigoCatMaterial>%s</codigoCatMaterial>%n", e.categoriaMaterial().identity()));
        materiais.add(String.format("<fichaTecnica nome=\"%s\" path=\"%s\"/>%n", e.fichaTecnicaMaterial().nome(),
                e.fichaTecnicaMaterial().path()));
        materiais.add("</material>%n");

        return materiais;
    }

    @Override
    public String ending() { return ENDING; }
}
