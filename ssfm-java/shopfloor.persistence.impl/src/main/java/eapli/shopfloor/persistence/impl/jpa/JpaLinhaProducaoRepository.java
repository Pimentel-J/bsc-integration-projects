package eapli.shopfloor.persistence.impl.jpa;


import javax.persistence.TypedQuery;

import eapli.shopfloor.general.CodigoAlfaCurto;
import eapli.shopfloor.gestaochaofabrica.domain.EstadoProcessamentoLinhaProducao;
import eapli.shopfloor.gestaochaofabrica.domain.LinhaProducao;
import eapli.shopfloor.gestaochaofabrica.repositories.LinhaProducaoRepository;


public class JpaLinhaProducaoRepository extends BaseJpaRepositoryBase<LinhaProducao, CodigoAlfaCurto, CodigoAlfaCurto>
        implements LinhaProducaoRepository{

    public JpaLinhaProducaoRepository() {
        super("idLinhaProducao");
    }
    
    @Override
	public Iterable<LinhaProducao> linhasProducaoComProcessamentoAtivo() {
		TypedQuery<LinhaProducao> query = null; 
		try {
			query = entityManager().createQuery(
	                "SELECT l FROM LinhaProducao l WHERE l.estadoProcessamento = :ativo ",
	                LinhaProducao.class);
		} catch(Exception e) {
			System.out.println(e);
		}
        query.setParameter("ativo", EstadoProcessamentoLinhaProducao.ATIVO);
        return query.getResultList();
	}

}
