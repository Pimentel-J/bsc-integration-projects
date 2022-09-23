/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.shopfloor.gestaoproducao.domain;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import eapli.framework.domain.model.AggregateRoot;
import eapli.framework.validations.Preconditions;
import eapli.shopfloor.general.CodigoAlfaCurto;
import eapli.shopfloor.gestaochaofabrica.domain.LinhaProducao;

/**
 *
 *.se.1181483
 */
@Entity
public class OrdemProducao implements AggregateRoot<CodigoAlfaCurto> {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    @AttributeOverrides({
        @AttributeOverride(name = "codigo", column = @Column(name = "id_ordem", length = 10))
    })
    private CodigoAlfaCurto idOrdem;

    @Version
    private Long version;
    @Temporal(TemporalType.DATE)
    private Calendar dataEmissao;
    @Temporal(TemporalType.DATE)
    private Calendar dataPrevistaExec;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "produto")
    private Produto produto;
    @ElementCollection
    private final Set<EncomendaInOrdemProducao> encomendas = new HashSet<>();
    @AttributeOverrides({
        @AttributeOverride(name = "quantidade", column = @Column(name = "quantidade_produto", nullable = false))
    })
    private float quantidade;
    @Enumerated(EnumType.STRING)
    @Column(name = "estado_ordem", nullable = false)
    private EstadoOrdemProducao estadoOrdem;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "linha_producao")
    private LinhaProducao linhaProducao;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "execucaoproducao")
    private ExecucaoOrdemProducao execucaoProducao;

    protected OrdemProducao() {
        // for ORM
    }

    public OrdemProducao(CodigoAlfaCurto idOrdem, Calendar dataEmissao,
            Calendar dataPrevistaExec, Produto produto,
            Set<Encomenda> encomendas, int quantidade) {
        Preconditions.noneNull(idOrdem, dataEmissao, dataPrevistaExec, produto, quantidade);
        Preconditions.isPositive(quantidade);
        Preconditions.isPositive(dataPrevistaExec.compareTo(dataEmissao));
        this.idOrdem = idOrdem;
        this.dataEmissao = dataEmissao;
        this.dataPrevistaExec = dataPrevistaExec;
        this.produto = produto;
        this.quantidade = quantidade;
        this.estadoOrdem = EstadoOrdemProducao.PENDENTE;
        this.linhaProducao = null;
        this.execucaoProducao = null;
        adicionarEncomendas(encomendas);
    }

    private void adicionarEncomendas(Set<Encomenda> encomendas) {
        for (Encomenda encomenda : encomendas) {
            adicionarEncomenda(encomenda);
        }
    }
    
    private void adicionarEncomenda(Encomenda encomenda) {
        EncomendaInOrdemProducao encInOrdemProducao = new EncomendaInOrdemProducao(encomenda);
        encomendas.add(encInOrdemProducao);
    }
    
    @Override
    public boolean sameAs(Object other) {
        if (!(other instanceof OrdemProducao)) {
            return false;
        }
        final OrdemProducao that = (OrdemProducao) other;
        if (this == that) {
            return true;
        }
        return identity().equals(that.identity());
    }

    @Override
    public CodigoAlfaCurto identity() {
        return this.idOrdem;
    }

    public void concluirOrdem() {
        this.estadoOrdem = EstadoOrdemProducao.CONCLUIDA;
    }

    public void pararTemporariamenteOrdem() {
        this.estadoOrdem = EstadoOrdemProducao.EXECUCAO_PARADA_TEMPORARIAMENTE;
    }

    public void suspenderOrdem() {
        this.estadoOrdem = EstadoOrdemProducao.SUSPENSA;
    }

    public void iniciarExecucaoOrdem() {
        this.estadoOrdem = EstadoOrdemProducao.EM_EXECUCAO;
        this.execucaoProducao = new ExecucaoOrdemProducao();
    }

    public LinhaProducao linhaProducao() {
    	return this.linhaProducao;
    }
    
    public void associarLinhaProducao(LinhaProducao linhaProducao) {
    	this.linhaProducao = linhaProducao;
    }
    
    public EstadoOrdemProducao estado() {
    	return this.estadoOrdem;
    }
    
    public ExecucaoOrdemProducao execucao() {
    	return this.execucaoProducao;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Calendar dataEmissao() {
        return dataEmissao;
    }

    public Calendar dataPrevistaExec() {
        return dataPrevistaExec;
    }

    public Produto produto() {
        return produto;
    }

    public Set<EncomendaInOrdemProducao> getEncomendas() {
        return encomendas;
    }

    public float quantidade() {
        return quantidade;
    }
    
}
