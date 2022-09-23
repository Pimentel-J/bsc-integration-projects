/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.shopfloor.gestaoproducao.domain;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import eapli.framework.domain.model.AggregateRoot;
import eapli.framework.validations.Preconditions;
import eapli.shopfloor.general.Descricao;
import eapli.shopfloor.gestaochaofabrica.domain.Maquina;


/**
 *
 *.se.1181483
 */

@Entity
public class Atividade implements AggregateRoot<Long>{

    
	private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue
    private Long id;
    @Version
    private Long version;
    
    @OneToOne
    @JoinColumn(name = "maquina")
    private Maquina maquina;
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar timestampInicio;
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar timestampFim;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoAtividade tipoAtividade; 
    @Column(nullable = true)
    private Descricao erro; 
    
    

    protected Atividade() {
        //for ORM
    }

    public Atividade(Maquina maquina, Calendar timestampInicio, Calendar timestampFim, TipoAtividade tipo) {
    	Preconditions.noneNull(maquina);
    	Preconditions.noneNull(timestampInicio);
    	Preconditions.noneNull(timestampFim);
    	Preconditions.noneNull(tipo);
        this.maquina = maquina;
        this.timestampInicio = timestampInicio;
        this.timestampFim = timestampFim;
        this.tipoAtividade = tipo;
    }
    
    public Atividade(Maquina maquina, Calendar timestampInicio, Calendar timestampFim, TipoAtividade tipo, Descricao erro) {
    	Preconditions.noneNull(maquina);
    	Preconditions.noneNull(timestampInicio);
    	Preconditions.noneNull(timestampFim);
    	Preconditions.noneNull(tipo);
    	Preconditions.noneNull(erro);
        this.maquina = maquina;
        this.timestampInicio = timestampInicio;
        this.timestampFim = timestampFim;
        this.tipoAtividade = tipo;
        this.erro = erro;
    }
    
    
    
    @Override
    public boolean sameAs(Object other) {
        if (!(other instanceof Atividade)) {
            return false;
        }
        final Atividade that = (Atividade) other;
        if (this == that) {
            return true;
        }
        return identity().equals(that.identity());
    }

    public Maquina maquina() {
        return maquina;
    }

    public Calendar timeStampInicio() {
        return timestampInicio;
    }

    public Calendar timeStampFim() {
        return timestampFim;
    }

    @Override
    public Long identity() {
        return this.id;
    }
    
}
