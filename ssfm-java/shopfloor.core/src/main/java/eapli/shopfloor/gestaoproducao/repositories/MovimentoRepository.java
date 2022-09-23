/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.shopfloor.gestaoproducao.repositories;

import java.util.List;

import eapli.framework.domain.repositories.DomainRepository;
import eapli.shopfloor.gestaoproducao.domain.Movimento;
import eapli.shopfloor.gestaoproducao.domain.OrdemProducao;

/**
 *
 *1181455
 */
public interface MovimentoRepository extends DomainRepository<Long, Movimento> {

	List<Object[]> consumosDeOrdemProducao(OrdemProducao ordemProducao);

	List<Object[]> estornosDeOrdemProducao(OrdemProducao ordemProducao);

	List<Object[]> entregasDeOrdemProducao(OrdemProducao ordemProducao);

	List<Object[]> entregasTotaisDepositoDeOrdemProducao(OrdemProducao ordemProducao);

	List<Object[]> consumoPorProdutoDeOrdemProducao(OrdemProducao ordemProducao);


}
