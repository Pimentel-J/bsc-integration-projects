package eapli.shopfloor.gestaoproducao.exportacao.application.categoriaproduto;

import eapli.shopfloor.gestaoproducao.domain.CategoriaProduto;
import eapli.shopfloor.gestaoproducao.domain.Produto;
import eapli.shopfloor.gestaoproducao.repositories.ProdutoRepository;
import eapli.shopfloor.infrastructure.persistence.PersistenceContext;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

/**
 * Serviço com oum template do método para exportar o objeto Categoria Produto
 *
 *João Pimentel 
 */
public class CategoriaProdutoExporterService {

    private final ProdutoRepository repository = PersistenceContext.repositories().produtos();

    public Collection<String> exportString() {
        Collection<String> report = new ArrayList<>();
        XmlCategoriaProdutoStringExporter exporter = new XmlCategoriaProdutoStringExporter();
        Iterable<Produto> produtos = repository.findAll();

        if (!produtos.iterator().hasNext()) {
            System.out.println("Não existem produtos");
            System.exit(0);
        }

        report.add(exporter.header());

        for (final Produto produto : produtos) {
            if (produto.categoriaProduto() != null) {
                report.add(produto.categoriaProduto().CodigoCategoria().codigo());
                report.add(produto.categoriaProduto().DescricaoCategoria().descricaoBreve());
            }
        }

        report.add(exporter.ending());

        return report;
    }
}
