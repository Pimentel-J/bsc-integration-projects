/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.shopfloor.gestaoproducao.domain;

import eapli.framework.domain.model.DomainFactory;
import eapli.framework.validations.Preconditions;
import eapli.shopfloor.general.CodigoAlfaCurto;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

/**
 *
 *.se.1181483
 */
public class OrdemProducaoBuilder implements DomainFactory<OrdemProducao>{
    
    private CodigoAlfaCurto idOrdem;
    private Calendar dataEmissao;
    private Calendar dataPrevistaExec;
    private Produto produto;
    private final Set<Encomenda> encomendas = new HashSet<>();
    private int quantidade;
    
    public OrdemProducaoBuilder comIdOrdem (CodigoAlfaCurto idOrdem){
        this.idOrdem = idOrdem;
        return this;
    }
    
    public OrdemProducaoBuilder comDataEmissao(Calendar dataEmissao){
        this.dataEmissao = dataEmissao;
        return this;
    }
    
    public OrdemProducaoBuilder comDataPrevistaExecucao(Calendar dataPrevExec){
        Preconditions.isPositive(dataPrevExec.compareTo(this.dataEmissao));
        this.dataPrevistaExec = dataPrevExec;
        return this;
    }
    
    public OrdemProducaoBuilder comProduto(Produto produto){
        this.produto = produto;
        return this;
    }
    
    public OrdemProducaoBuilder comEncomenda(Encomenda encomenda){
        this.encomendas.add(encomenda);
        return this;
    }
    
    public OrdemProducaoBuilder comQuantidade(int quantidade){
        this.quantidade = quantidade;
        return this;
    }

    @Override
    public OrdemProducao build() {
        return new OrdemProducao(this.idOrdem, this.dataEmissao, this.dataPrevistaExec
        , this.produto, this.encomendas, this.quantidade);
    }
    
}
