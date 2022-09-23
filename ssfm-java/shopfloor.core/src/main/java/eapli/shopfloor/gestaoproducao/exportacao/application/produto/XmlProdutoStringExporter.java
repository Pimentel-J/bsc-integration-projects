package eapli.shopfloor.gestaoproducao.exportacao.application.produto;

import eapli.shopfloor.general.exportacao.ElementStringExporter;
import eapli.shopfloor.gestaoproducao.domain.MateriaPrima;
import eapli.shopfloor.gestaoproducao.domain.Produto;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Classe de implementação da Strategy XML String export
 *
 *João Pimentel 
 */
public class XmlProdutoStringExporter implements ElementStringExporter<Produto> {

    private final String HEADER = "<Produtos>%n";
    private final String ENDING = "</Produtos>%n";

    @Override
    public String header() { return HEADER; }

    @Override
    public Collection<String> element(Produto e) {
        Collection<String> produtos = new ArrayList<>();
        List<MateriaPrima> mps = new ArrayList<>();

        produtos.add("<produto>%n");
        produtos.add(String.format("<codigoFabricoProduto>%s</codigoFabricoProduto>%n", e.identity().codigo()));
        produtos.add(String.format("<codigoComercial>%s</codigoComercial>%n", e.codigoComercial().codigo()));
        produtos.add(String.format("<descricaoBreve>%s</descricaoBreve>%n", e.descricaoBreveProduto()));
        produtos.add(String.format("<descricaoCompleta>%s</descricaoCompleta>%n", e.descricaoCompleta()));
        produtos.add(String.format("<unidadeProduto>%s</unidadeProduto>%n", e.unidade()));
        produtos.add(String.format("<codigoCatProduto>%s</codigoCatProduto>%n",
                e.categoriaProduto().CodigoCategoria().codigo()));
        if (e.fichaProducao() != null) {
            produtos.add("<fichaProducao>%n");
            produtos.add("<quantidadeStandard>%n");
            produtos.add(String.format("<quantidade>%s</quantidade>%n",
                    e.fichaProducao().quantidadeStandard().quantidade()));
            produtos.add(String.format("<unidade>%s</unidade>%n", e.fichaProducao().quantidadeStandard().unidade()));
            produtos.add("</quantidadeStandard>%n");

            for (MateriaPrima materiaPrima : mps) {
                produtos.add("<materiaPrima>%n");

                if (materiaPrima.produto() != null) {
                    produtos.add(String.format("<codigoFabricoProduto>%s</codigoFabricoProduto>%n",
                            materiaPrima.produto().identity().codigo()));
                    produtos.add("<quantidade>%n");
                    produtos.add(String.format("<quantidade>%s</quantidade>%n", materiaPrima.quantidade()));
                    produtos.add(String.format("<unidade>%s</unidade>%n", materiaPrima.produto().unidade()));
                } else {
                    produtos.add(String.format("<codigoMaterial>%s</codigoMaterial>%n",
                            materiaPrima.material().identity().codigo()));
                    produtos.add("<quantidade>%n");
                    produtos.add(String.format("<quantidade>%s</quantidade>%n", materiaPrima.quantidade()));
                }

                produtos.add("</quantidade>%n");
                produtos.add("</materiaPrima>%n");
            }
            produtos.add("</fichaProducao>%n");
        }
        produtos.add("</produto>%n");

        return produtos;
    }

    @Override
    public String ending() { return ENDING; }
}
