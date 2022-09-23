package eapli.shopfloor.persistence.impl.jpa;

import java.util.Calendar;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import eapli.shopfloor.gestaoproducao.domain.Atividade;
import eapli.shopfloor.gestaoproducao.domain.OrdemProducao;
import eapli.shopfloor.gestaoproducao.domain.Produto;
import eapli.shopfloor.gestaoproducao.domain.TipoAtividade;
import eapli.shopfloor.gestaoproducao.repositories.AtividadeRepository;

public class JpaAtividadeRepository extends BaseJpaRepositoryBase<Atividade, Long, Long>
        implements AtividadeRepository {

    public JpaAtividadeRepository() {
        super("id");
    }
    
    @Override
	public Calendar inicioDeOrdemProducao(OrdemProducao ordemProducao) {
		try{
	        final TypedQuery<Calendar> q = createQuery(
	            "SELECT min(a.timestampInicio) FROM Atividade a "
	            + "WHERE a in :ativExec ", Calendar.class);
	        q.setParameter("ativExec", ordemProducao.execucao().atividades());
	        return q.getSingleResult();
	        } catch(NoResultException nre) {
	            return null;
	        }
	}
    
    @Override
	public Calendar fimDeOrdemProducao(OrdemProducao ordemProducao) {
		try{
	        final TypedQuery<Calendar> q = createQuery(
	            "SELECT max(a.timestampFim) FROM Atividade a "
	            + "WHERE a in :ativExec ", Calendar.class);
	        q.setParameter("ativExec", ordemProducao.execucao().atividades());
	        return q.getSingleResult();
	        } catch(NoResultException nre) {
	            return null;
	        }
	}
    
    @Override
	public List<Object[]> inicioFimMaquinasDeOrdemProducao(OrdemProducao ordemProducao) {
		try{
	        final TypedQuery<Object[]> q = createQuery(
        		"SELECT codMaq.codigo, min(a.timestampInicio), max(a.timestampFim) FROM Atividade a "
        	            + "join a.maquina maq "
        	            + "join maq.idMaquina codMaq "
        	            + "WHERE a in :ativExec "
        	            + "group by codMaq.codigo order by min(a.timestampInicio)", Object[].class);
	        q.setParameter("ativExec", ordemProducao.execucao().atividades());
	        return q.getResultList();
	        } catch(NoResultException nre){
	            return null;
	        }
	}
	
	@Override
	public List<Object[]> inicioFimParagemMaquinasDeOrdemProducao(OrdemProducao ordemProducao) {
		try{
	        final TypedQuery<Object[]> q = createQuery(
        		"SELECT codMaq.codigo, a.timestampInicio, a.timestampFim, a.erro FROM Atividade a "
        	            + "join a.maquina maq "
        	            + "join maq.idMaquina codMaq "
        	            + "WHERE a in :ativExec and a.tipoAtividade = :tipo "
        	            + "order by a.timestampInicio", Object[].class);
	        q.setParameter("ativExec", ordemProducao.execucao().atividades());
	        q.setParameter("tipo", TipoAtividade.PARAGEM);
	        return q.getResultList();
	        } catch(NoResultException nre){
	            return null;
	        }
	}
	

}
