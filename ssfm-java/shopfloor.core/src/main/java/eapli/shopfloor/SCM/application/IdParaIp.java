/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.shopfloor.SCM.application;

import eapli.shopfloor.general.NumeroSerie;
import java.net.InetAddress;
import javax.persistence.*;

/**
 *
 *.se.1181483
 */
@Entity
public class IdParaIp {
    
    
    private static final long serialVersionUID = 1L;


    @EmbeddedId
    @AttributeOverrides({
        @AttributeOverride(name ="numero",column=@Column(name="id_interno"))
    })
    private NumeroSerie idInterno;

    @Version
    private Long version;
    
    @Column(name="endereco_ip_maquina")
    private InetAddress endereco;

    protected IdParaIp() {
        //for ORM
    }

    public IdParaIp(NumeroSerie idInterno, InetAddress endereco) {
        
        this.idInterno = idInterno;
        this.endereco = endereco;
    }

    public NumeroSerie idInterno() {
        return idInterno;
    }

    public InetAddress enderecoIp() {
        return endereco;
    }
    
    
    
}
