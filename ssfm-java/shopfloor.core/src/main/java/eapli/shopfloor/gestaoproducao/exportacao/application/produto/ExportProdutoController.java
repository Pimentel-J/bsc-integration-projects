package eapli.shopfloor.gestaoproducao.exportacao.application.produto;

import eapli.shopfloor.general.FileFormats;
import eapli.shopfloor.general.exportacao.ElementExporter;
import eapli.shopfloor.gestaoproducao.application.ListMateriaisService;
import eapli.shopfloor.gestaoproducao.application.ListProdutosService;
import eapli.shopfloor.gestaoproducao.domain.Material;
import eapli.shopfloor.gestaoproducao.domain.Produto;

import java.io.IOException;

/**
 *João Pimentel 
 */
public class ExportProdutoController {

    private final ListProdutosService listSvc = new ListProdutosService();
    private final ProdutoExporterFactory factory = new ProdutoExporterFactory();
    private final ProdutoExporterService exportSvc = new ProdutoExporterService();

    public void export(String filename, FileFormats format) throws IOException {
        final ElementExporter<Produto> exporter = factory.build(format);

        final Iterable<Produto> produtos = listSvc.allProdutos();
        exportSvc.export(produtos, filename, exporter);
    }

}
