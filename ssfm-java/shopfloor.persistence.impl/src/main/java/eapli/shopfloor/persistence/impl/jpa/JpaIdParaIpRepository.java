/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.shopfloor.persistence.impl.jpa;

import eapli.shopfloor.SCM.application.IdParaIp;
import eapli.shopfloor.SCM.repositories.IdParaIpRepository;
import eapli.shopfloor.general.NumeroSerie;
import java.net.InetAddress;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

/**
 *.se.1181483
 */
public class JpaIdParaIpRepository extends BaseJpaRepositoryBase<IdParaIp, NumeroSerie, NumeroSerie>
        implements IdParaIpRepository {

    private static final String ID_POR_IP = "SELECT new eapli.shopfloor.SCM.application."
            + "IdParaIp(idip.idInterno, idip.endereco) FROM IdParaIp idip WHERE idip.endereco = :ip";
    
    private static final String IP_POR_ID = "SELECT new eapli.shopfloor.SCM.application."
            + "IdParaIp(idip.idInterno, idip.endereco) FROM IdParaIp idip WHERE idip.idInterno = :id";

    public JpaIdParaIpRepository() {
        super("idInterno");
    }

    @Override
    public IdParaIp obterIdDeIp(InetAddress enderecoIp) {
        try {
            final TypedQuery<IdParaIp> query = entityManager().createQuery(ID_POR_IP,
                    IdParaIp.class);
            query.setParameter("ip", enderecoIp);
            return query.getSingleResult();
        } catch (NoResultException nre) {
            return null;   
        }
    }
    
    @Override
    public IdParaIp obterIpDeId(NumeroSerie numInterno) {
        try {
            final TypedQuery<IdParaIp> query = entityManager().createQuery(IP_POR_ID,
                    IdParaIp.class);
            query.setParameter("id", numInterno);
            return query.getSingleResult();
        } catch (NoResultException nre) {
            return null;   
        }
    }

}
