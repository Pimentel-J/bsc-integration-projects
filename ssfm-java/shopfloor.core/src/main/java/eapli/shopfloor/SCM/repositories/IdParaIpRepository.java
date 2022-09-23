/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.shopfloor.SCM.repositories;

import eapli.framework.infrastructure.repositories.IterableRepository;
import eapli.shopfloor.SCM.application.IdParaIp;
import eapli.shopfloor.general.NumeroSerie;
import java.net.InetAddress;

/**
 *
 *.se.1181483
 */
public interface IdParaIpRepository extends IterableRepository<IdParaIp, NumeroSerie>{
    
    public IdParaIp obterIdDeIp(InetAddress enderecoIp);
    
    public IdParaIp obterIpDeId(NumeroSerie numInterno);
    
}
