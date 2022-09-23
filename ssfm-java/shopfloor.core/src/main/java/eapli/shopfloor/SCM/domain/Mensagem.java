/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.shopfloor.SCM.domain;

import eapli.framework.domain.model.AggregateRoot;
import eapli.shopfloor.general.CodigoAlfaCurto;
import eapli.shopfloor.general.Descricao;
import eapli.shopfloor.gestaochaofabrica.domain.Maquina;
import java.util.Calendar;

import javax.persistence.*;

/**
 *
 *.se.1181483
 */
@Entity
public class Mensagem implements AggregateRoot<Long> {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long id;
    @Version
    private Long version;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_mensagem", nullable = false)
    private TipoMensagem tipoMensagem; //obrigatorio
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar timestampGeracao; //obrigatorio
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar timestampEnvio; //obrigatorio
    @Enumerated(EnumType.STRING)
    @Column(name = "estado_mensagem", nullable = false)
    private EstadoMensagem estadoMensagem; //obrigatorio
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "maquina")
    private Maquina maquina; // obrigatorio
    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "codigo", column = @Column(name = "produto_msg"))
    })
    private CodigoAlfaCurto produtoMsg; //opcional
    @AttributeOverrides({
        @AttributeOverride(name = "quantidade", column = @Column(name = "quantidade_produto_msg"))
    })
    private float quantidadeMsg; //opcional
    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "codigo", column = @Column(name = "lote_msg"))
    })
    private CodigoAlfaCurto loteMsg; //opcional
    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "descricao", column = @Column(name = "msg_erro"))
    })
    private Descricao erroMsg; //opcional
    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "codigo", column = @Column(name = "ordem_prod_msg"))
    })
    private CodigoAlfaCurto ordemProdMsg; //opcional
    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "codigo", column = @Column(name = "deposito_msg"))
    })
    private CodigoAlfaCurto depositoMsg; //opcional
    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "codigo", column = @Column(name = "linha_producao_msg"))
    })
    private CodigoAlfaCurto linhaProducaoMsg; //opcional

    public Mensagem() {
        // for ORM
    }

    public Mensagem(TipoMensagem tipoMensagem, Calendar timestampGeracao, Calendar timestampEnvio, Maquina maquina,
            CodigoAlfaCurto produtoMsg, float quantidadeMsg, CodigoAlfaCurto loteMsg, Descricao erroMsg,
            CodigoAlfaCurto ordemProdMsg, CodigoAlfaCurto depositoMsg) {
        this.tipoMensagem = tipoMensagem;
        this.timestampGeracao = timestampGeracao;
        this.timestampEnvio = timestampEnvio;
        this.maquina = maquina;
        this.produtoMsg = produtoMsg;
        this.quantidadeMsg = quantidadeMsg;
        this.loteMsg = loteMsg;
        this.erroMsg = erroMsg;
        this.ordemProdMsg = ordemProdMsg;
        this.depositoMsg = depositoMsg;
        this.estadoMensagem = EstadoMensagem.POR_TRATAR;
    }

    public TipoMensagem tipo() {
    	return this.tipoMensagem;
    }
    
    public EstadoMensagem estado() {
    	return this.estadoMensagem;
    }
    
    public CodigoAlfaCurto produto() {
    	return this.produtoMsg;
    }
    
    public CodigoAlfaCurto deposito() {
    	return this.depositoMsg;
    }
    
    public Maquina maquina() {
    	return this.maquina;
    }
    
    public CodigoAlfaCurto ordemProducao() {
    	return this.ordemProdMsg;
    }
    
    public float quantidade() {
    	return this.quantidadeMsg;
    }
    
    public void inserirOrdemProducao(CodigoAlfaCurto codOrdem) {
    	this.ordemProdMsg = codOrdem;
    }
    
    public void inserirLinhaProducao(CodigoAlfaCurto idLinha) {
    	this.linhaProducaoMsg = idLinha;
    }
    
    public Calendar timestampGeracao() {
    	return this.timestampGeracao;
    }
    
    public CodigoAlfaCurto lote() {
    	return this.loteMsg;
    }
    
    public CodigoAlfaCurto linhaProducao() {
    	return this.linhaProducaoMsg;
    }
    
    public Descricao erro() {
    	return this.erroMsg;
    }
    
    public void marcarComoEnriquecida() {
    	this.estadoMensagem = EstadoMensagem.ENRIQUECIDA;
    }
    
    public void marcarComoProcessada() {
    	this.estadoMensagem = EstadoMensagem.PROCESSADA;
    }
    

    @Override
    public boolean sameAs(Object other) {
        if (!(other instanceof Mensagem)) {
            return false;
        }
        final Mensagem that = (Mensagem) other;
        if (this == that){
            return true;
        }
        return identity().equals(that.identity());
    }

    @Override
    public Long identity() {
        return this.id;
    }

   
}
