package eapli.shopfloor.app.backoffice.console.presentation.ordensproducao.reporting;

import eapli.framework.visitor.Visitor;
import eapli.shopfloor.reporting.ordensproducao.dto.OrdensProducao;

import java.text.SimpleDateFormat;

/**
 * Printer - Consultar Ordens de Produção
 *
 *João Pimentel 
 */
public class OrdensProducaoPrinter implements Visitor<OrdensProducao> {

    @Override
    public void visit(final OrdensProducao visitee) {
        String dataEmissao = new SimpleDateFormat("dd-MM-yyyy").format(visitee.dataEmissao.getTime());
        String dataPrevistaExec = new SimpleDateFormat("dd-MM-yyyy").format(visitee.dataPrevistaExec.getTime());
        System.out.printf("%-15s%-15s%-20s%-15s%-15s%-15s%-15s%-12s%-35s", visitee.codigo, dataEmissao, dataPrevistaExec,
                visitee.produto.descricaoBreveProduto(), visitee.produto.identity(),
                String.format("%.0f", visitee.quantidade), visitee.linhaProducao.identity(),
                visitee.execucaoProducao.identity(), visitee.estado);
    }
}
