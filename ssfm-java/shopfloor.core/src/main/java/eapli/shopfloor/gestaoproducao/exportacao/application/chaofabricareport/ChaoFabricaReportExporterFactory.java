package eapli.shopfloor.gestaoproducao.exportacao.application.chaofabricareport;

import eapli.shopfloor.general.FileFormats;
import eapli.shopfloor.general.exportacao.ElementExporter;
import eapli.shopfloor.gestaochaofabrica.exportacao.application.deposito.DepositoExporterService;
import eapli.shopfloor.gestaochaofabrica.exportacao.application.linhaProducao.LinhaProducaoExporterService;
import eapli.shopfloor.gestaochaofabrica.exportacao.application.maquina.MaquinaExporterService;
import eapli.shopfloor.gestaoproducao.exportacao.application.categoriamaterial.CategoriaMaterialExporterService;
import eapli.shopfloor.gestaoproducao.exportacao.application.categoriaproduto.CategoriaProdutoExporterService;
import eapli.shopfloor.gestaoproducao.exportacao.application.material.MaterialExporterService;
import eapli.shopfloor.gestaoproducao.exportacao.application.ordemProducao.OrdemProducaoExporterService;
import eapli.shopfloor.gestaoproducao.exportacao.application.produto.ProdutoExporterService;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Classe Factory de criação do objeto apropriado para cada formato de ficheiro e criação da informação a exportar
 *
 *João Pimentel 
 */
public class ChaoFabricaReportExporterFactory {

    private final DepositoExporterService depositoSvc = new DepositoExporterService();
    private final MaquinaExporterService maquinaSvc = new MaquinaExporterService();
    private final LinhaProducaoExporterService linhaSvc = new LinhaProducaoExporterService();
    private final MaterialExporterService materialSvc = new MaterialExporterService();
    private final CategoriaMaterialExporterService categoriaMaterialSvc = new CategoriaMaterialExporterService();
    private final ProdutoExporterService produtoSvc = new ProdutoExporterService();
    private final OrdemProducaoExporterService ordensSvc = new OrdemProducaoExporterService();

    public ElementExporter<String> build(FileFormats format) {
        switch (format) {
            case XML:
                return new XmlChaoFabricaReportExporter();
        }
        throw new IllegalStateException("Unknown format");
    }

    public Iterable<String> allChaoFabricaInfo() {
        Collection<String> chaoFabricaInfo = new ArrayList<>();

        chaoFabricaInfo.addAll(depositoSvc.exportString());
        chaoFabricaInfo.addAll(maquinaSvc.exportString());
        chaoFabricaInfo.addAll(linhaSvc.exportString());

        return chaoFabricaInfo;
    }

    public Iterable<String> allGestaoProducaoInfo() {
        Collection<String> gestaoProducaoInfo = new ArrayList<>();

        gestaoProducaoInfo.addAll(materialSvc.exportString());
        gestaoProducaoInfo.addAll(categoriaMaterialSvc.exportString());
        gestaoProducaoInfo.addAll(produtoSvc.exportString());
        gestaoProducaoInfo.addAll(ordensSvc.exportString());

        return gestaoProducaoInfo;
    }

}
