package eapli.shopfloor.persistence.impl.jpa;

import eapli.shopfloor.general.CodigoAlfaCurto;
import eapli.shopfloor.general.NumeroSerie;
import eapli.shopfloor.gestaochaofabrica.domain.LinhaProducao;
import eapli.shopfloor.gestaochaofabrica.domain.Maquina;
import eapli.shopfloor.gestaochaofabrica.repositories.MaquinaRepository;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

public class JpaMaquinaRepository extends BaseJpaRepositoryBase<Maquina, Long, CodigoAlfaCurto> implements MaquinaRepository {

    private static final String MAQUINA_BY_ID_PROTOCOLO = "SELECT m FROM Maquina m WHERE m.idProtocolo = :idInterno";
    private static final String MAQUINA_BY_LINHA_PRODUCAO = "SELECT m FROM Maquina m WHERE m.linhaProducao = :linha " +
            "ORDER BY m.sequencia";
    private static final String MAQUINA_MAX_SEQUENCIA_LINHA_PRODUCAO = "SELECT m FROM Maquina m WHERE m.linhaProducao" +
            " = :linha AND m.sequencia = (SELECT MAX(sequencia) FROM Maquina WHERE m.linhaProducao = :linha)";

    public JpaMaquinaRepository() {
        super("idMaquina");
    }

    @Override
    public Maquina containsMaquina(NumeroSerie idInterno) {
        try {
            final TypedQuery<Maquina> q = entityManager().createQuery(MAQUINA_BY_ID_PROTOCOLO, Maquina.class);
            q.setParameter("idInterno", idInterno);
            return q.getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }

    @Override
    public Iterable<Maquina> maquinasByLinhaProducao(LinhaProducao linhaProducao) {
        try {
            final TypedQuery<Maquina> q = entityManager().createQuery(MAQUINA_BY_LINHA_PRODUCAO, Maquina.class);
            q.setParameter("linha", linhaProducao);
            return q.getResultList();
        } catch (NoResultException nre) {
            return null;
        }
    }

    @Override
    public Maquina ultimaPosicaoOcupadaPorLinhaProducao(LinhaProducao linhaProducao) {
        try {
            final TypedQuery<Maquina> q = entityManager().createQuery(MAQUINA_MAX_SEQUENCIA_LINHA_PRODUCAO,
                    Maquina.class);
            q.setParameter("linha", linhaProducao);
            return q.getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }
}
