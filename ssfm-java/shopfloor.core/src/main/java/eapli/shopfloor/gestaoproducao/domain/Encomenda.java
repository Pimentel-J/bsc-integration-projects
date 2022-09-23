/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.shopfloor.gestaoproducao.domain;

import eapli.framework.domain.model.ValueObject;
import eapli.shopfloor.general.CodigoAlfaLongo;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;

/**
 *
 *.se.1181483
 */
@Embeddable
@Access(AccessType.FIELD)
public class Encomenda implements ValueObject{
    
    private static final long serialVersionUID = 1L;
    
    private CodigoAlfaLongo idEncomenda;
    
    protected Encomenda(){
        // for ORM
    }
    
    public Encomenda (final CodigoAlfaLongo idEncomenda){
        this.idEncomenda = idEncomenda;
    }

    public CodigoAlfaLongo idEncomenda() {
        return idEncomenda;
    }

}
