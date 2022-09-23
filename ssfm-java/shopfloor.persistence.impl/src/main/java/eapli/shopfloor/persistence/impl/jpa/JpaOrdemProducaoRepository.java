/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.shopfloor.persistence.impl.jpa;

import eapli.shopfloor.general.CodigoAlfaCurto;
import eapli.shopfloor.gestaoproducao.domain.EncomendaInOrdemProducao;
import eapli.shopfloor.gestaoproducao.domain.OrdemProducao;
import eapli.shopfloor.gestaoproducao.repositories.OrdemProducaoRepository;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;


/**
 *.se.1181483
 */
public class JpaOrdemProducaoRepository extends BaseJpaRepositoryBase<OrdemProducao, CodigoAlfaCurto, CodigoAlfaCurto>
                                    implements OrdemProducaoRepository{

    private static final String ENCOMENDAS = "SELECT encomenda FROM OrdemProducao op INNER JOIN op.encomendas encomenda";
    
    public JpaOrdemProducaoRepository() {
        super("idOrdem");
    }
    
    @Override
    public OrdemProducao containsOrdemProducao(CodigoAlfaCurto id){
        try{
        final TypedQuery<OrdemProducao> q = createQuery(
            "SELECT op FROM OrdemProducao op WHERE op.idOrdem = :id", OrdemProducao.class);
        q.setParameter("id", id);
        return q.getSingleResult();
        } catch(NoResultException nre){
            return null;
        }
    }

    @Override
    public Iterable<EncomendaInOrdemProducao> allEncomendas() {
        final TypedQuery<EncomendaInOrdemProducao> query =
                entityManager().createQuery(ENCOMENDAS, EncomendaInOrdemProducao.class);
        return query.getResultList();
    }
    
}
