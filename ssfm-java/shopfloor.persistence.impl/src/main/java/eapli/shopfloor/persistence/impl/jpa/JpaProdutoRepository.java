/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.shopfloor.persistence.impl.jpa;

import eapli.shopfloor.general.CodigoAlfaCurto;
import eapli.shopfloor.gestaoproducao.domain.OrdemProducao;
import eapli.shopfloor.gestaoproducao.domain.Produto;
import eapli.shopfloor.gestaoproducao.repositories.ProdutoRepository;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

/**
 *.se.1181483
 */
public class JpaProdutoRepository extends BaseJpaRepositoryBase<Produto, CodigoAlfaCurto, CodigoAlfaCurto>
                                    implements ProdutoRepository{
    
    public JpaProdutoRepository() {
        super("codigoFabrico");
    }
    
    @Override
    public Produto containsProduto(CodigoAlfaCurto codFabrico){
        try{
        final TypedQuery<Produto> q = createQuery(
            "SELECT p FROM Produto p WHERE p.codigoFabrico = :codFabrico", Produto.class);
        q.setParameter("codFabrico", codFabrico);
        return q.getSingleResult();
        } catch(NoResultException nre){
            return null;
        }
    }
    
    @Override
	public List<Object[]> consumosTeoricos(Produto produto, float quantidade) {
		try{
	        final TypedQuery<Object[]> q = createQuery(
        		"SELECT mpq.quantidade * :quantidade / qst.quantidade, mppc.codigo FROM Produto p "
        	            + "join p.fichaProducao fp "
        	            + "join fp.quantidadeStandard qst "
        	            + "join fp.materiasPrimas mps "
        	            + "join mps.materiaPrima mp "
        	            + "join mp.produto mpp "
        	            + "join mpp.codigoFabrico mppc "
        	            + "join mp.quantidade mpq "
        	            + "where p = :produto "
        	            + "group by mppc.codigo", Object[].class);
	        q.setParameter("produto", produto);
	        q.setParameter("quantidade", quantidade);
	        return q.getResultList();
	        } catch(NoResultException nre){
	            return null;
	        }
	}
}
