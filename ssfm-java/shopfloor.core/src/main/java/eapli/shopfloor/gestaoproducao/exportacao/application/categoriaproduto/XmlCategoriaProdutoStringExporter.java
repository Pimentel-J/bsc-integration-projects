package eapli.shopfloor.gestaoproducao.exportacao.application.categoriaproduto;

import eapli.shopfloor.general.exportacao.ElementStringExporter;
import eapli.shopfloor.gestaoproducao.domain.CategoriaProduto;
import eapli.shopfloor.gestaoproducao.domain.Produto;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Classe de implementação da Strategy XML String export
 *
 *João Pimentel 
 */
public class XmlCategoriaProdutoStringExporter implements ElementStringExporter<CategoriaProduto> {

    private final String HEADER = "<CategoriasProduto>%n";
    private final String ENDING = "</CategoriasProduto>%n";

    @Override
    public String header() { return HEADER; }

    @Override
    public Collection<String> element(CategoriaProduto e) {
        Collection<String> categoriasProduto = new ArrayList<>();

        categoriasProduto.add("<categoriaProduto>%n");
        categoriasProduto.add(String.format("<codigoCatProduto>%s</codigoCatProduto>%n",
                e.CodigoCategoria().codigo()));
        categoriasProduto.add(String.format("<descricaoBreve>%s</descricaoBreve>%n",
                e.DescricaoCategoria()));
        categoriasProduto.add("</categoriaProduto>%n");

        return categoriasProduto;
    }

    @Override
    public String ending() { return ENDING; }
}
