/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.shopfloor.persistence.impl.jpa;

import java.util.Calendar;

import javax.persistence.NoResultException;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;

import eapli.shopfloor.SCM.domain.EstadoMensagem;
import eapli.shopfloor.SCM.domain.Mensagem;
import eapli.shopfloor.SCM.domain.TipoMensagem;
import eapli.shopfloor.SCM.repositories.MensagemRepository;
import eapli.shopfloor.general.CodigoAlfaCurto;
import eapli.shopfloor.gestaochaofabrica.domain.LinhaProducao;
import eapli.shopfloor.gestaochaofabrica.domain.Maquina;

/**
 *
 *.se.1181483
 */
public class JpaMensagemRepository extends BaseJpaRepositoryBase<Mensagem, Long, Long>
                                    implements MensagemRepository{
    
    public JpaMensagemRepository() {
        super("id");
    }

	@Override
	public Iterable<Mensagem> mensagensDeLinhaProducao(LinhaProducao linhaProducao, Calendar inicioIntervalo, Calendar fimIntervalo) {
		TypedQuery<Mensagem> query = null; 
		try {
			query = entityManager().createQuery(
	                "SELECT msg FROM Mensagem msg inner join msg.maquina maq "
	                + "WHERE maq.linhaProducao = :linha and msg.timestampGeracao between :inicio and :fim "
	                + "and msg.estadoMensagem != :processada "
	                + "order by msg.timestampGeracao",
	                Mensagem.class);
		} catch(Exception e) {
			System.out.println(e);
		}
        query.setParameter("linha", linhaProducao);
        query.setParameter("inicio", inicioIntervalo, TemporalType.TIMESTAMP);
        query.setParameter("fim", fimIntervalo, TemporalType.TIMESTAMP);
        query.setParameter("processada", EstadoMensagem.PROCESSADA);
        return query.getResultList();
	}
	
	@Override
	public Iterable<Mensagem> mensagensDeLinhaProducao(LinhaProducao linhaProducao, Calendar fimIntervalo) {
		TypedQuery<Mensagem> query = null; 
		try {
			query = entityManager().createQuery(
	                "SELECT msg FROM Mensagem msg inner join msg.maquina maq "
	                + "WHERE maq.linhaProducao = :linha and msg.timestampGeracao <= :fim "
	                + "and msg.estadoMensagem != :processada "
	                + "order by msg.timestampGeracao",
	                Mensagem.class);
		} catch(Exception e) {
			System.out.println(e);
		}
        query.setParameter("linha", linhaProducao);
        query.setParameter("fim", fimIntervalo, TemporalType.TIMESTAMP);
        query.setParameter("processada", EstadoMensagem.PROCESSADA);
        return query.getResultList();
	}
	
	@Override
	public CodigoAlfaCurto ordemProducaoMensagem(LinhaProducao linhaProducao, Calendar timestampGeracao) {
		TypedQuery<CodigoAlfaCurto> query = null; 
		try {
			query = entityManager().createQuery(
	                "SELECT msg.ordemProdMsg FROM Mensagem msg inner join msg.maquina maq "
	                + "WHERE maq.linhaProducao = :linha and msg.timestampGeracao <= :tsGeracao and msg.tipoMensagem = :tipoMsg "
	                + "and maq.sequencia = 1 "
	                + "order by msg.timestampGeracao desc",
	                CodigoAlfaCurto.class);
	        query.setParameter("linha", linhaProducao);
	        query.setParameter("tsGeracao", timestampGeracao, TemporalType.TIMESTAMP);
	        query.setParameter("tipoMsg", TipoMensagem.S0);
	        return query.setMaxResults(1).getResultList().get(0);
        } catch(Exception e) {
			return null;
		}
	}
	
	@Override
	public Mensagem finalAtivUltimaMaqProcessada(Maquina maquina, CodigoAlfaCurto codOrdem) {
		TypedQuery<Mensagem> query = null; 
		try {
			query = entityManager().createQuery(
	                "SELECT msg FROM Mensagem msg inner join msg.maquina maq "
	                + "WHERE maq = :maquina and msg.ordemProdMsg = :codOrdem and msg.tipoMensagem = :tipoMsg "
	                + "and msg.estadoMensagem = :estadoMsg",
	                Mensagem.class);
		
        query.setParameter("maquina", maquina);
        query.setParameter("codOrdem", codOrdem);
        query.setParameter("tipoMsg", TipoMensagem.S9);
        query.setParameter("estadoMsg", EstadoMensagem.PROCESSADA);
        return query.getSingleResult();
        
		} catch(NoResultException e) {
			return null;
		}
	}
    
}
