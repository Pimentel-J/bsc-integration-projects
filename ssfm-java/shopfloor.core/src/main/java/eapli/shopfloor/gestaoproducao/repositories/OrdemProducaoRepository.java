/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.shopfloor.gestaoproducao.repositories;

import eapli.framework.domain.repositories.DomainRepository;
import eapli.shopfloor.general.CodigoAlfaCurto;
import eapli.shopfloor.gestaoproducao.domain.EncomendaInOrdemProducao;
import eapli.shopfloor.gestaoproducao.domain.OrdemProducao;

/**
 *
 *.se.1181483
 */
public interface OrdemProducaoRepository extends DomainRepository<CodigoAlfaCurto, OrdemProducao> {

    OrdemProducao containsOrdemProducao(CodigoAlfaCurto id);

    Iterable<EncomendaInOrdemProducao> allEncomendas();

}
