/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.shopfloor.gestaoproducao.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Version;

import eapli.framework.domain.model.AggregateRoot;
import eapli.shopfloor.gestaochaofabrica.domain.Deposito;
import eapli.shopfloor.gestaochaofabrica.domain.Maquina;

/**
 *
 *.se.1181483
 */

@Entity
public class Movimento implements AggregateRoot<Long> {

    @Id
    @GeneratedValue
    private Long id;
    @Version
    private Long version;

    @Column(nullable = false)
    private QtdMovimento quantidade;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoMovimento tipoMovimento; 
    
    @ManyToOne
    private Deposito deposito;

    @ManyToOne
    private Material material;

    @ManyToOne
    private Produto produto;

    @ManyToOne
    private Maquina maquina;
    
    public Movimento() {
        //for ORM
    }

    /**
     * Movimento de material entre máquina e depósito
     * 
     * @param tipoMovimento
     * @param maquina
     * @param quantidade
     * @param deposito
     * @param material
     */
    public Movimento(TipoMovimento tipoMovimento, Maquina maquina, QtdMovimento quantidade, Deposito deposito, Material material) {
        this.tipoMovimento = tipoMovimento;
    	this.maquina = maquina;
        this.quantidade = quantidade;
        this.deposito = deposito;
        this.material = material;
        
    }
    
    /**
     * Movimento de produto entre máquina e depósito
     * 
     * @param tipoMovimento
     * @param maquina
     * @param quantidade
     * @param deposito
     * @param produto
     */
    public Movimento(TipoMovimento tipoMovimento, Maquina maquina, QtdMovimento quantidade, Deposito deposito, Produto produto) {
        this.tipoMovimento = tipoMovimento;
    	this.maquina = maquina;
        this.quantidade = quantidade;
        this.deposito = deposito;
        this.produto = produto;
        
    }

    public QtdMovimento quantidade() {
        return quantidade;
    }

    public TipoMovimento tipoMovimento() {
        return tipoMovimento;
    }

    public Deposito deposito() {
        return deposito;
    }

    public Material material() {
        return material;
    }

    public Maquina maquina() {
        return maquina;
    }

    @Override
    public boolean sameAs(Object other) {
        if (!(other instanceof Movimento)) {
            return false;
        }
        final Movimento that = (Movimento) other;
        if (this == that) {
            return true;
        }
        return identity().equals(that.identity());
    }

    @Override
    public Long identity() {
        return this.id;
    }
}
