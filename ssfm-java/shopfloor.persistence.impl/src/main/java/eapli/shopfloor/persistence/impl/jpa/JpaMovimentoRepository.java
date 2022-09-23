package eapli.shopfloor.persistence.impl.jpa;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import eapli.shopfloor.gestaoproducao.domain.Movimento;
import eapli.shopfloor.gestaoproducao.domain.OrdemProducao;
import eapli.shopfloor.gestaoproducao.domain.TipoMovimento;
import eapli.shopfloor.gestaoproducao.repositories.MovimentoRepository;

public class JpaMovimentoRepository extends BaseJpaRepositoryBase<Movimento, Long, Long>
        implements MovimentoRepository {

    public JpaMovimentoRepository() {
        super("id");
    }
    
    @Override
	public List<Object[]> consumosDeOrdemProducao(OrdemProducao ordemProducao) {
		try{
	        final TypedQuery<Object[]> q = createQuery(
	            "SELECT sum(qtd.quantidade), p.unidadeProduto, codP.codigo, codD.codigo FROM Movimento m "
	            + "join m.quantidade qtd "
	            + "join m.produto p "
	            + "join m.deposito d "
	            + "join p.codigoFabrico codP "
	            + "join d.codigo codD "
	            + "WHERE m in :movExec and m.tipoMovimento = :tipo "
	            + "group by p.unidadeProduto, codP.codigo, codD.codigo", Object[].class);
	        q.setParameter("movExec", ordemProducao.execucao().movimentos());
	        q.setParameter("tipo", TipoMovimento.CONSUMO);
	        return q.getResultList();
	        } catch(NoResultException nre){
	            return null;
	        }
	}
	
	@Override
	public List<Object[]> consumoPorProdutoDeOrdemProducao(OrdemProducao ordemProducao) {
		try{
	        final TypedQuery<Object[]> q = createQuery(
	            "SELECT sum(qtd.quantidade), codP.codigo FROM Movimento m "
	            + "join m.quantidade qtd "
	            + "join m.produto p "
	            + "join p.codigoFabrico codP "
	            + "WHERE m in :movExec and m.tipoMovimento = :tipo "
	            + "group by codP.codigo", Object[].class);
	        q.setParameter("movExec", ordemProducao.execucao().movimentos());
	        q.setParameter("tipo", TipoMovimento.CONSUMO);
	        return q.getResultList();
	        } catch(NoResultException nre) {
	            return null;
	        }
	}
	
	@Override
	public List<Object[]> estornosDeOrdemProducao(OrdemProducao ordemProducao) {
		try{
	        final TypedQuery<Object[]> q = createQuery(
	            "SELECT sum(qtd.quantidade), p.unidadeProduto, codP.codigo, codD.codigo FROM Movimento m "
	            + "join m.quantidade qtd "
	            + "join m.produto p "
	            + "join m.deposito d "
	            + "join p.codigoFabrico codP "
	            + "join d.codigo codD "
	            + "WHERE m in :movExec and m.tipoMovimento = :tipo "
	            + "group by p.unidadeProduto, codP.codigo, codD.codigo", Object[].class);
	        q.setParameter("movExec", ordemProducao.execucao().movimentos());
	        q.setParameter("tipo", TipoMovimento.ESTORNO);
	        return q.getResultList();
	        } catch(NoResultException nre){
	            return null;
	        }
	}
	
	@Override
	public List<Object[]> entregasDeOrdemProducao(OrdemProducao ordemProducao) {
		try{
	        final TypedQuery<Object[]> q = createQuery(
	            "SELECT qtd.quantidade, lot.codigo, codD.codigo FROM Movimento m, ExecucaoOrdemProducao e "
	            + "join m.quantidade qtd "
	            + "join m.produto p "
	            + "join m.deposito d "
	            + "join p.codigoFabrico codP "
	            + "join d.codigo codD "
	            + "join e.producoes prds "
	            + "join prds.producao prod "
	            + "join prod.quantidade qtdP "
	            + "join prod.lote lot "
	            + "WHERE m in :movExec and m.tipoMovimento = :tipo and e = :execucao and qtd.quantidade = qtdP.quantidade "
	            + "order by lot.codigo"
	            , Object[].class);
	        q.setParameter("movExec", ordemProducao.execucao().movimentos());
	        q.setParameter("execucao", ordemProducao.execucao());
	        q.setParameter("tipo", TipoMovimento.ENTREGA_PRODUCAO);
	        return q.getResultList();
	        } catch(NoResultException nre){
	            return null;
	        }
	}
	
	@Override
	public List<Object[]> entregasTotaisDepositoDeOrdemProducao(OrdemProducao ordemProducao) {
		try{
	        final TypedQuery<Object[]> q = createQuery(
	            "SELECT sum(qtd.quantidade), codD.codigo FROM Movimento m, ExecucaoOrdemProducao e "
	            + "join m.quantidade qtd "
	            + "join m.produto p "
	            + "join m.deposito d "
	            + "join p.codigoFabrico codP "
	            + "join d.codigo codD "
	            + "join e.producoes prds "
	            + "join prds.producao prod "
	            + "join prod.quantidade qtdP "
	            + "WHERE m in :movExec and m.tipoMovimento = :tipo and e = :execucao and qtd.quantidade = qtdP.quantidade "
	            + "group by codD.codigo order by codD.codigo"
	            , Object[].class);
	        q.setParameter("movExec", ordemProducao.execucao().movimentos());
	        q.setParameter("execucao", ordemProducao.execucao());
	        q.setParameter("tipo", TipoMovimento.ENTREGA_PRODUCAO);
	        return q.getResultList();
	        } catch(NoResultException nre){
	            return null;
	        }
	}

}
