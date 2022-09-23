/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.shopfloor.gestaoproducao.repositories;

import java.util.List;

import eapli.framework.domain.repositories.DomainRepository;
import eapli.shopfloor.general.CodigoAlfaCurto;
import eapli.shopfloor.gestaoproducao.domain.Produto;

/**
 *
 *.se.1181483
 */
public interface ProdutoRepository extends DomainRepository<CodigoAlfaCurto, Produto> {

    public Produto containsProduto(CodigoAlfaCurto codFabrico);

	List<Object[]> consumosTeoricos(Produto produto, float quantidade);

}
