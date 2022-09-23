/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.shopfloor.SCM.repositories;

import java.util.Calendar;

import eapli.framework.domain.repositories.DomainRepository;
import eapli.shopfloor.SCM.domain.Mensagem;
import eapli.shopfloor.general.CodigoAlfaCurto;
import eapli.shopfloor.gestaochaofabrica.domain.LinhaProducao;
import eapli.shopfloor.gestaochaofabrica.domain.Maquina;

/**
 *
 *.se.1181483
 */
public interface MensagemRepository extends DomainRepository<Long, Mensagem>{

	Iterable<Mensagem> mensagensDeLinhaProducao(LinhaProducao linhaProducao, Calendar inicioIntervalo, Calendar fimIntervalo);

	CodigoAlfaCurto ordemProducaoMensagem(LinhaProducao linhaProducao, Calendar timestampGeracao);

	Mensagem finalAtivUltimaMaqProcessada(Maquina maquina, CodigoAlfaCurto codOrdem);

	Iterable<Mensagem> mensagensDeLinhaProducao(LinhaProducao linhaProducao, Calendar fimIntervalo);

}
