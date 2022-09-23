package eapli.shopfloor.reporting.produtos.repositories;

import eapli.framework.domain.repositories.ReportingRepository;
import eapli.shopfloor.reporting.produtos.dto.ProdutosSemFicha;

/**
 * Repository - Consultar Produtos Sem Ficha
 *
 *João Pimentel 
 */
@ReportingRepository
public interface ProdutoReportingRepository {

    Iterable<ProdutosSemFicha> produtosSemFicha();

}
