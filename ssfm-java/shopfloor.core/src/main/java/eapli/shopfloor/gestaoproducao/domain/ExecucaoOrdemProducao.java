/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.shopfloor.gestaoproducao.domain;

import eapli.framework.domain.model.AggregateRoot;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Version;

/**
 *
 *.se.1181483
 */
@Entity
public class ExecucaoOrdemProducao implements AggregateRoot<Long>{
    
    @Id
    @GeneratedValue
    private Long id;
    @Version
    private Long version;
    
    
    @ElementCollection
    private final Set<ProducaoInExecOrdemProd> producoes = new HashSet<>();
    
    @OneToMany(cascade = CascadeType.ALL)
    private final Set<Atividade> atividades = new HashSet<>();
    
    @OneToMany(cascade = CascadeType.ALL)
    private final Set<Movimento> movimentos = new HashSet<>();
    
    public ExecucaoOrdemProducao(final Set<Producao> producoes){
        adicionarProducoes(producoes);
    }
    
    public ExecucaoOrdemProducao(){
    	// permite criar Execução sem ter ainda produções, atividades ou movimentos
    }
    
    private void adicionarProducoes(Set<Producao> producoes) {
        for (final Producao producao : producoes) {
            adicionarUmaProducao(producao);
        }
    }
    
//    private void adicionarAtividades(Set<Atividade> atividades){
//        for (final Atividade atividade : atividades) {
//            adicionarUmaAtividade(atividade);
//        }
//    }
//    
//    private void adicionarMovimentos(Set<Movimento> movimentos){
//        for (final Movimento movimento : movimentos) {
//        	adicionarUmMovimento(movimento);
//        }
//    }
    
    public void adicionarUmaProducao(Producao producao){
        ProducaoInExecOrdemProd prodInExecOrdProd = new ProducaoInExecOrdemProd(producao);
        producoes.add(prodInExecOrdProd);
    }
    
    public void adicionarUmaAtividade(Atividade atividade) {
        atividades.add(atividade);
    }
    
    public void adicionarUmMovimento(Movimento movimento) {
        movimentos.add(movimento);
    }
    
    public Set<Movimento> movimentos(){
    	return this.movimentos;
    }
    
    public Set<Atividade> atividades(){
    	return this.atividades;
    }
    
    @Override
    public boolean sameAs(Object other) {
        if (!(other instanceof ExecucaoOrdemProducao)) {
            return false;
        }
        final ExecucaoOrdemProducao that = (ExecucaoOrdemProducao) other;
        if (this == that) {
            return true;
        }
        return identity().equals(that.identity());
    }

    @Override
    public Long identity() {
        return this.id;
    }

    

    
}
