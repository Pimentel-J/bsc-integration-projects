package eapli.shopfloor.persistence.impl.jpa;

import eapli.shopfloor.reporting.produtos.dto.ProdutosSemFicha;
import eapli.shopfloor.reporting.produtos.repositories.ProdutoReportingRepository;

import javax.persistence.TypedQuery;

/**
 *João Pimentel 
 */
public class JpaProdutoReportingRepository extends BaseJpaReportingRepositoryBase
        implements ProdutoReportingRepository {

    private static final String PRODUTOS_SEM_FICHA = "SELECT new eapli.shopfloor.reporting.produtos.dto." +
            "ProdutosSemFicha(p.codigoFabrico, p.descricaoBreve) FROM Produto p WHERE p.fichaProducao = null";

    JpaProdutoReportingRepository() { super(); }

    @Override
    public Iterable<ProdutosSemFicha> produtosSemFicha() {

        final TypedQuery<ProdutosSemFicha> query = entityManager().createQuery(PRODUTOS_SEM_FICHA,
                ProdutosSemFicha.class);

        return query.getResultList();
    }

}
