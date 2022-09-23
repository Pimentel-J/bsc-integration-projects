/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.shopfloor.gestaoproducao.repositories;

import java.util.Calendar;
import java.util.List;

import eapli.framework.domain.repositories.DomainRepository;
import eapli.shopfloor.gestaoproducao.domain.Atividade;
import eapli.shopfloor.gestaoproducao.domain.OrdemProducao;

/**
 *
 *1181455
 */
public interface AtividadeRepository extends DomainRepository<Long, Atividade> {

	Calendar inicioDeOrdemProducao(OrdemProducao ordemProducao);

	Calendar fimDeOrdemProducao(OrdemProducao ordemProducao);

	List<Object[]> inicioFimMaquinasDeOrdemProducao(OrdemProducao ordemProducao);

	List<Object[]> inicioFimParagemMaquinasDeOrdemProducao(OrdemProducao ordemProducao);

}
