package eapli.shopfloor.gestaoproducao.exportacao.application.material;

import eapli.shopfloor.general.exportacao.ElementExporter;
import eapli.shopfloor.gestaoproducao.domain.Material;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Classe de implementação da Strategy XML export
 *
 *João Pimentel 
 */
public class XmlMaterialExporter implements ElementExporter<Material> {

    private PrintWriter stream;

    @Override
    public void begin(String filename) throws IOException {
        stream = new PrintWriter(new FileWriter(filename));
        stream.println("<Materiais>");
    }

    @Override
    public void element(Material e) {
        stream.println("<material>");
        stream.printf("<codigo>%s</codigo>%n", e.identity());
        stream.printf("<descricao>%s</descricao>%n", e.descricaoMaterial());
        stream.println("<categoria>");
        stream.printf("<codigo>%s</codigo>%n", e.categoriaMaterial().identity());
        stream.printf("<descricao>%s</descricao>%n", e.categoriaMaterial().descricao());
        stream.println("</categoria>");
        stream.printf("<fichaTecnica nome=\"%s\" path=\"%s\"/>%n", e.fichaTecnicaMaterial().nome(),
                e.fichaTecnicaMaterial().path());
        stream.println("</material>");
    }

    @Override
    public void elementSeparator() {
        // nothing to do
    }

    @Override
    public void end() {
        stream.println("</Materiais>");
        stream.close();
    }

    @Override
    public void cleanup() {
        if (stream != null) {
            stream.close();
        }
    }
}
