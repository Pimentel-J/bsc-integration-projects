/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.shopfloor.gestaoproducao.domain;


import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;

import eapli.framework.domain.model.ValueObject;
import eapli.shopfloor.general.CodigoAlfaCurto;


/**
 *
 *.se.1181483
 */
@Embeddable
@Access(AccessType.FIELD)
public class Producao implements ValueObject{
    
    @Embedded
    private QtdMovimento quantidade;
    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "codigo", column = @Column(name = "lote", nullable = false))
    })
    private CodigoAlfaCurto lote;

    protected Producao() {
        //for ORM
    }
    
    public Producao(float quantidade, CodigoAlfaCurto lote) {
        if (quantidade < 0) {
            throw new IllegalArgumentException(
                    "A quantidade deve ser um número >= 0");
        }
        this.quantidade = QtdMovimento.valueOf(quantidade);
        this.lote = lote;
    }

    public float quantidade() {
    	return this.quantidade.quantidade();
    }
    
    public String lote() {
    	return this.lote.codigo();
    }
    
}
