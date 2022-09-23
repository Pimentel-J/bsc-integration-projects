package eapli.shopfloor.SPM.domain;

import java.util.Calendar;
import java.util.Locale;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;


import eapli.framework.domain.model.AggregateRoot;
import eapli.framework.validations.Preconditions;
import eapli.shopfloor.SCM.domain.Mensagem;
import eapli.shopfloor.general.Descricao;
import eapli.shopfloor.gestaochaofabrica.domain.LinhaProducao;

/**
 *1181455
 */
@Entity
public class NotificacaoErroProcessamento implements AggregateRoot<Long> {

	private static final long serialVersionUID = 1L;

    // ORM primary key
    @Id
    @GeneratedValue
    private Long id;

    @Version
    private Long version;
    
    @Enumerated(EnumType.STRING)
    private TipoNotificacaoErroProcessamento tipo;
    
    @Enumerated(EnumType.STRING)
    private EstadoNotificacaoErroProcessamento estado;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar timestampGeracao;
    
    @ManyToOne
    private Mensagem mensagem;
    
    @ManyToOne
    private LinhaProducao linhaProducao;
    
    private Descricao descricao;
    
    
    protected NotificacaoErroProcessamento() {
		// ORM
	}
    
    public NotificacaoErroProcessamento(TipoNotificacaoErroProcessamento tipo, Mensagem mensagem, LinhaProducao linhaProducao, String descricao) {
    	Preconditions.nonNull(tipo, "O tipo não pode ser nulo");
    	Preconditions.nonNull(mensagem, "A mensagem não pode ser nula");
    	Preconditions.nonNull(linhaProducao, "A linha de producao não pode ser nula");
    	Preconditions.nonEmpty(descricao, "A descrição não pode ser vazia");
    	this.tipo = tipo;
    	this.estado = EstadoNotificacaoErroProcessamento.ATIVA;	// estado default
    	this.timestampGeracao = Calendar.getInstance(new Locale("pt"));
    	this.mensagem = mensagem;
    	this.linhaProducao = linhaProducao;
    	this.descricao = Descricao.valueOf(descricao);
    }

    public void arquivarNotificacaoErroProcess() {
        this.estado = EstadoNotificacaoErroProcessamento.ARQUIVADA;
    }
    public TipoNotificacaoErroProcessamento tipo() { return tipo; }

    public EstadoNotificacaoErroProcessamento estado() { return estado; }

    public Calendar timestampGeracao() { return timestampGeracao; }

    public Mensagem mensagem() { return mensagem; }

    public LinhaProducao linhaProducao() { return linhaProducao; }

    public Descricao descricao() { return descricao; }

	@Override
	public boolean sameAs(Object other) {
		if (!(other instanceof NotificacaoErroProcessamento)) {
            return false;
        }
        final NotificacaoErroProcessamento that = (NotificacaoErroProcessamento) other;
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
