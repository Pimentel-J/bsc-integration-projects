/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.shopfloor.gestaoproducao.importacao.application;

import eapli.framework.general.domain.model.Designation;
import eapli.framework.util.TemplateMethod;
import eapli.shopfloor.general.CodigoAlfaCurto;
import eapli.shopfloor.general.CodigoAlfaLongo;
import eapli.shopfloor.general.Descricao;
import eapli.shopfloor.general.DescricaoBreve;
import eapli.shopfloor.gestaoproducao.domain.CategoriaProduto;
import eapli.shopfloor.gestaoproducao.domain.Produto;
import eapli.shopfloor.gestaoproducao.domain.Unidade;
import eapli.shopfloor.gestaoproducao.exportacao.application.ExportFileFormat;
import eapli.shopfloor.gestaoproducao.exportacao.application.ExportadorErrosProdutosService;
import eapli.shopfloor.gestaoproducao.repositories.ProdutoRepository;
import eapli.shopfloor.infrastructure.persistence.PersistenceContext;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import static java.util.Objects.isNull;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 *.se.1181483
 */
public class ImportadorProdutosService {

    private static final Logger logger = LogManager.getLogger(ImportadorProdutosService.class);
    private final ProdutoRepository produtoRepository = PersistenceContext.repositories().produtos();
    private final ExportadorErrosProdutosService exportErros = new ExportadorErrosProdutosService();

    private static final int OPCAO_ALTERAR_DADOS = 1;
    private static final int OPCAO_MANTER_DADOS = 2;
    private static final String PREFIXO_FICHEIRO_ERROS = "Erros_";

    @TemplateMethod
    public void importar(String nomeFicheiro, ImportadorProdutos importador, int opcao)
            throws IOException {
        try {
            importador.begin(nomeFicheiro);
            String[] linha;
            String codFabrico, codComercial, descBreve, descCompleta, unidade, categoria, linhaExport;
            List<String> exportacao = new ArrayList<>();

            do {
                linhaExport = importador.element();
                //System.out.println(linhaExport);
                    
                    if (!isNull(linhaExport)) {
                    
                
                    linha = linhaExport.split(importador.elementSeparator());

                    codFabrico = linha[0];
                    codComercial = linha[1];
                    descBreve = linha[2];
                    descCompleta = linha[3];
                    unidade = linha[4];
                    categoria = linha[5];
                        //System.out.println(linha[0]+linha[1]+linha[2]+linha[3]+linha[4]+linha[5]);
                    try {
                        final Produto newProduto = new Produto(CodigoAlfaCurto.valueOf(codFabrico),
                                CodigoAlfaLongo.valueOf(codComercial), DescricaoBreve.valueOf(descBreve),
                                Descricao.valueOf(descCompleta), Unidade.valueOf(unidade),
                                new CategoriaProduto(CodigoAlfaCurto.valueOf(categoria), DescricaoBreve.valueOf(categoria)));
                        if (opcao == OPCAO_ALTERAR_DADOS && this.produtoRepository.containsOfIdentity(newProduto.identity())){
                            Produto oldProduto = this.produtoRepository.ofIdentity(newProduto.identity()).get();
                            oldProduto.alterarCodigoComercial(CodigoAlfaLongo.valueOf(codComercial));
                            oldProduto.alterarDescricaoBreve(DescricaoBreve.valueOf(descBreve));
                            oldProduto.alterarDescricaoCompleta(Descricao.valueOf(descCompleta));
                            oldProduto.alterarUnidadeProduto(Unidade.valueOf(unidade));
                            oldProduto.alterarCategoriaProduto(categoria);
                            this.produtoRepository.save(oldProduto);
                        }
                        else{
                            this.produtoRepository.save(newProduto);
                        }
                    } catch (final IllegalArgumentException iae) {
                        logger.warn("Linha com erros encontrada, não será importada", iae);
                        System.out.println(iae);
                        exportacao.add(linhaExport);
                    }
                    }
            } while (linhaExport != null);

            System.out.printf("Foram encontradas %d linhas com erros%n", exportacao.size());

            if (!exportacao.isEmpty()) {
                exportErros.export(exportacao, PREFIXO_FICHEIRO_ERROS+nomeFicheiro+".csv", ExportFileFormat.CSV);
            }
        } catch (final IOException e) {
            logger.error("Problema a importar Produtos", e);
            throw e;
        } finally {
            importador.cleanup();
        }
    }
}
