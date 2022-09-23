/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.shopfloor.gestaoproducao.domain;

import eapli.framework.domain.model.ValueObject;
import eapli.framework.validations.Preconditions;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;

/**
 *
 *.se.1181483
 */
@Embeddable
@Access(AccessType.FIELD)
public class EncomendaInOrdemProducao implements ValueObject {

    private static final long serialVersionUID = 1L;

    @Embedded
    private final Encomenda encomenda;
    
    public EncomendaInOrdemProducao() {
        //ORM
        encomenda = null;
    }

    public EncomendaInOrdemProducao(Encomenda encomenda) {
        Preconditions.nonNull(encomenda);
        this.encomenda = encomenda;
    }

    public Encomenda encomenda() {
        return encomenda;
    }

}
