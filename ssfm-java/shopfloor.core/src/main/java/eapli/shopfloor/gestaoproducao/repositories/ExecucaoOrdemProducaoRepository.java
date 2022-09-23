/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.shopfloor.gestaoproducao.repositories;

import java.util.List;

import eapli.framework.domain.repositories.DomainRepository;
import eapli.shopfloor.gestaoproducao.domain.ExecucaoOrdemProducao;
import eapli.shopfloor.gestaoproducao.domain.OrdemProducao;
import eapli.shopfloor.gestaoproducao.domain.Producao;


/**
 *
 *.se.1181483
 */
public interface ExecucaoOrdemProducaoRepository extends DomainRepository<Long, ExecucaoOrdemProducao>{

	List<Object[]> producoesDeOrdemProducao(OrdemProducao ordemProducao);

	Double producaoTotalDeOrdemProducao(OrdemProducao ordemProducao);

	Double desvioProducaoDeOrdemProducao(OrdemProducao ordemProducao);



}
