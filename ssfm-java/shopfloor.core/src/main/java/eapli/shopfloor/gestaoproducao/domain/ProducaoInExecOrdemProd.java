/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.shopfloor.gestaoproducao.domain;

import eapli.framework.domain.model.ValueObject;
import eapli.framework.validations.Preconditions;
import javax.persistence.*;

/**
 *
 *.se.1181483
 */
@Embeddable
@Access(AccessType.FIELD)
public class ProducaoInExecOrdemProd implements ValueObject {

    private static final long serialVersionUID = 1L;

    @Embedded
    private final Producao producao;

    public ProducaoInExecOrdemProd() {
        // ORM
        producao = null;
    }

    public ProducaoInExecOrdemProd(Producao producao) {
        Preconditions.nonNull(producao);
        this.producao = producao;
    }

}
