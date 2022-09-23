package eapli.shopfloor.persistence.impl.jpa;

import eapli.shopfloor.gestaoproducao.domain.Encomenda;
import eapli.shopfloor.gestaoproducao.domain.EncomendaInOrdemProducao;
import eapli.shopfloor.gestaoproducao.domain.EstadoOrdemProducao;
import eapli.shopfloor.reporting.ordensproducao.dto.OrdensProducao;
import eapli.shopfloor.reporting.ordensproducao.repositories.OrdemProducaoReportingRepository;

import javax.persistence.TypedQuery;

/**
 *João Pimentel 
 */
public class JpaOrdemProducaoReportingRepository extends BaseJpaReportingRepositoryBase
        implements OrdemProducaoReportingRepository {

    private static final String TODAS_ORDENS_PRODUCAO = "SELECT new eapli.shopfloor.reporting.ordensproducao.dto." +
            "OrdensProducao(op.idOrdem, op.dataEmissao) FROM OrdemProducao op";
    // ORDENS PRODUÇÃO POR ESTADO
    private static final String ORDENS_PRODUCAO_ESTADO = "SELECT new eapli.shopfloor.reporting.ordensproducao.dto." +
            "OrdensProducao(op.idOrdem, op.dataEmissao, op.dataPrevistaExec, op.produto, op.quantidade, " +
            "op.linhaProducao, op.execucaoProducao, op.estadoOrdem) FROM OrdemProducao op WHERE op.estadoOrdem = :status";
    private static final String ORDENS_PRODUCAO_PENDENTE = "SELECT new eapli.shopfloor.reporting.ordensproducao.dto." +
            "OrdensProducao(op.idOrdem, op.dataEmissao, op.dataPrevistaExec, op.produto, op.quantidade, " +
            "op.estadoOrdem) FROM OrdemProducao op WHERE op.estadoOrdem = :status";
    // ORDENS PRODUÇÃO POR ENCOMENDA
    private static final String ORDENS_PRODUCAO_ENCOMENDA = "SELECT new eapli.shopfloor.reporting.ordensproducao.dto." +
            "OrdensProducao(op.idOrdem, op.dataEmissao, op.dataPrevistaExec, op.produto, op.quantidade, " +
            "op.estadoOrdem) FROM OrdemProducao op INNER JOIN op.encomendas e WHERE e.encomenda = :encomenda";

    JpaOrdemProducaoReportingRepository() { super(); }

    @Override
    public Iterable<OrdensProducao> reportOrdensProducao() {
        final TypedQuery<OrdensProducao> query = entityManager().createQuery(TODAS_ORDENS_PRODUCAO,
                OrdensProducao.class);
        return query.getResultList();
    }

    @Override
    public Iterable<OrdensProducao> reportOrdensProducao(EstadoOrdemProducao estado) {
        final TypedQuery<OrdensProducao> query =
                entityManager().createQuery(estado.equals(EstadoOrdemProducao.PENDENTE) ?
                        ORDENS_PRODUCAO_PENDENTE : ORDENS_PRODUCAO_ESTADO, OrdensProducao.class);
        query.setParameter("status", estado);
        return query.getResultList();
    }

    @Override
    public Iterable<OrdensProducao> reportOrdensProducao(EncomendaInOrdemProducao encomenda) {
        final TypedQuery<OrdensProducao> query =
                entityManager().createQuery(ORDENS_PRODUCAO_ENCOMENDA, OrdensProducao.class);
        query.setParameter("encomenda", encomenda.encomenda());
        return query.getResultList();
    }
}
