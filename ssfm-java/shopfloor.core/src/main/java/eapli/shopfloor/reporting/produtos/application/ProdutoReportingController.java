package eapli.shopfloor.reporting.produtos.application;

import eapli.framework.application.UseCaseController;
import eapli.shopfloor.infrastructure.persistence.PersistenceContext;
import eapli.shopfloor.reporting.produtos.dto.ProdutosSemFicha;
import eapli.shopfloor.reporting.produtos.repositories.ProdutoReportingRepository;

/**
 * Controller - Consultar Produtos Sem Ficha
 *
 *João Pimentel 
 */
@UseCaseController
public class ProdutoReportingController {

    private final ProdutoReportingRepository repo = PersistenceContext.repositories().produtoReporting();

    /**
     *
     * @return produtos sem ficha produção
     */
    public Iterable<ProdutosSemFicha> reportProdutosSemFicha() {
        return repo.produtosSemFicha();
    }
}
