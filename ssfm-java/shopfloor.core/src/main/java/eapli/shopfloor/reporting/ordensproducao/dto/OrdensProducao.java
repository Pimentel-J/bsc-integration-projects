package eapli.shopfloor.reporting.ordensproducao.dto;

import eapli.framework.representations.dto.DTO;
import eapli.shopfloor.general.CodigoAlfaCurto;
import eapli.shopfloor.gestaochaofabrica.domain.LinhaProducao;
import eapli.shopfloor.gestaoproducao.domain.EstadoOrdemProducao;
import eapli.shopfloor.gestaoproducao.domain.ExecucaoOrdemProducao;
import eapli.shopfloor.gestaoproducao.domain.Produto;

import java.util.Calendar;

/**
 * DTO - Consultar Ordens de Produção
 *
 *João Pimentel 
 */
@DTO
public class OrdensProducao {

    public CodigoAlfaCurto codigo;
    public Calendar dataEmissao;
    public Calendar dataPrevistaExec;
    public Produto produto;
    public float quantidade;
    public LinhaProducao linhaProducao;
    public EstadoOrdemProducao estado;
    public ExecucaoOrdemProducao execucaoProducao;

    public OrdensProducao(final CodigoAlfaCurto codigo, final Calendar dataEmissao, final Calendar dataPrevistaExec,
                          final Produto produto, final float quantidade, final LinhaProducao linhaProducao,
                          final ExecucaoOrdemProducao execucaoProducao, final EstadoOrdemProducao estado) {
        this.codigo = codigo;
        this.dataEmissao = dataEmissao;
        this.dataPrevistaExec = dataPrevistaExec;
        this.produto = produto;
        this.quantidade = quantidade;
        this.linhaProducao = linhaProducao;
        this.execucaoProducao = execucaoProducao;
        this.estado = estado;
    }

    public OrdensProducao(final CodigoAlfaCurto codigo, final Calendar dataEmissao, final Calendar dataPrevistaExec,
                          final Produto produto, final float quantidade, final EstadoOrdemProducao estado) {
        this.codigo = codigo;
        this.dataEmissao = dataEmissao;
        this.dataPrevistaExec = dataPrevistaExec;
        this.produto = produto;
        this.quantidade = quantidade;
        this.estado = estado;
    }

    public OrdensProducao(final CodigoAlfaCurto codigo, final Calendar dataEmissao) {
        this.codigo = codigo;
        this.dataEmissao = dataEmissao;
    }
}
