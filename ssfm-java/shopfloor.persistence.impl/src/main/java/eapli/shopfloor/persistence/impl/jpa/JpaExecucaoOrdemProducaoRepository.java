/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.shopfloor.persistence.impl.jpa;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import eapli.shopfloor.gestaoproducao.domain.ExecucaoOrdemProducao;
import eapli.shopfloor.gestaoproducao.domain.OrdemProducao;
import eapli.shopfloor.gestaoproducao.domain.Producao;
import eapli.shopfloor.gestaoproducao.repositories.ExecucaoOrdemProducaoRepository;


/**
 *.se.1181483
 */
public class JpaExecucaoOrdemProducaoRepository extends BaseJpaRepositoryBase<ExecucaoOrdemProducao, Long, Long>
                                    implements ExecucaoOrdemProducaoRepository{
    
    public JpaExecucaoOrdemProducaoRepository() {
        super("idOrdem");
    }
    
    @Override
	public List<Object[]> producoesDeOrdemProducao(OrdemProducao ordemProducao) {
		try{
	        final TypedQuery<Object[]> q = createQuery(
	            "SELECT qtd.quantidade, lot.codigo FROM ExecucaoOrdemProducao e "
	            + "join e.producoes prds "
	            + "join prds.producao prod "
	            + "join prod.quantidade qtd "
	            + "join prod.lote lot WHERE e = :execucao", Object[].class);
	        q.setParameter("execucao", ordemProducao.execucao());
	        return q.getResultList();
	        } catch(NoResultException nre){
	            return null;
	        }
	}
    @Override
	public Double producaoTotalDeOrdemProducao(OrdemProducao ordemProducao) {
		try{
	        final TypedQuery<Double> q = createQuery(
	            "SELECT sum(qtd.quantidade) FROM ExecucaoOrdemProducao e "
	            + "join e.producoes prds join prds.producao prod join prod.quantidade qtd WHERE e = :execucao", Double.class);
	        q.setParameter("execucao", ordemProducao.execucao());
	        return q.getSingleResult();
	        } catch(NoResultException nre){
	            return null;
	        }
	}
    @Override
	public Double desvioProducaoDeOrdemProducao(OrdemProducao ordemProducao) {
        return producaoTotalDeOrdemProducao(ordemProducao)-ordemProducao.quantidade();
	}
    
}
