/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.shopfloor.gestaoproducao.domain;

import eapli.framework.domain.model.AggregateRoot;
import eapli.framework.validations.Preconditions;
import eapli.shopfloor.general.CodigoAlfaCurto;
import eapli.shopfloor.general.CodigoAlfaLongo;
import eapli.shopfloor.general.Descricao;
import eapli.shopfloor.general.DescricaoBreve;
import javax.persistence.*;

/**
 *  Um Produto
 *.se.1181483
 */

@Entity
public class Produto implements AggregateRoot<CodigoAlfaCurto>{


    private static final long serialVersionUID = 1L;


    @EmbeddedId
    @AttributeOverrides({
        @AttributeOverride(name ="codigo",column=@Column(name="codigo_fabrico"))
    })
    private CodigoAlfaCurto codigoFabrico;

    @Version
    private Long version;

    @AttributeOverrides({
        @AttributeOverride(name ="codigo",column=@Column(name = "codigo_comercial", nullable = false, length = 15))
    })
    private CodigoAlfaLongo codigoComercial;
    @AttributeOverrides({
        @AttributeOverride(name ="descricaoBreve",column=@Column(name = "descricao_breve", nullable = false, length = 30))
    })
    private DescricaoBreve descricaoBreve;
    @AttributeOverrides({
        @AttributeOverride(name ="descricao",column=@Column(name ="descricao_completa", nullable = false, length = 50))
    })
    private Descricao descricaoCompleta;
    @Enumerated(EnumType.STRING)
    @Column(name="unidade_produto", nullable = false)
    private Unidade unidadeProduto;
    @Embedded
    @Column(nullable = false)
    private CategoriaProduto categoriaProduto;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="fichaproducao")
    private FichaProducao fichaProducao;
    
    //@OneToOne(mappedBy="produto")
    //private OrdemProducao ordemProducao;

    public Produto(final CodigoAlfaCurto codigoFabrico,
            final CodigoAlfaLongo codigoComercial,
            final DescricaoBreve descricaoBreve,
            final Descricao descricaoCompleta,
            final Unidade unidadeProduto,
            final CategoriaProduto categoriaProduto) {
        Preconditions.noneNull(codigoFabrico, codigoComercial, descricaoBreve,
         descricaoCompleta, unidadeProduto, categoriaProduto);
        this.codigoFabrico = codigoFabrico;
        this.codigoComercial = codigoComercial;
        this.descricaoBreve = descricaoBreve;
        this.descricaoCompleta = descricaoCompleta;
        this.unidadeProduto = unidadeProduto;
        this.categoriaProduto = categoriaProduto;
    }

    protected Produto() {
        // for ORM only
    }

    public void associarFichaProducao(final FichaProducao fichaProducao) {
    	this.fichaProducao = fichaProducao;
    }

    public Unidade unidade() { return this.unidadeProduto; }

    public DescricaoBreve descricaoBreveProduto() { return this.descricaoBreve; }

    @Override
    public boolean sameAs(final Object other) {
        if (!(other instanceof Produto)) {
            return false;
        }
        final Produto that = (Produto) other;
        if (this == that){
            return true;
        }
        return identity().equals(that.identity());
    }

    @Override
    public CodigoAlfaCurto identity() {
        return this.codigoFabrico;
    }


    public void alterarCodigoComercial(CodigoAlfaLongo novoCodigoComercial) {
        this.codigoComercial = novoCodigoComercial;
    }

    public void alterarDescricaoBreve(DescricaoBreve novaDescricaoBreve) {
        this.descricaoBreve = novaDescricaoBreve;
    }

    public void alterarDescricaoCompleta(Descricao novaDescricaoCompleta) {
        this.descricaoCompleta = novaDescricaoCompleta;
    }

    public void alterarUnidadeProduto(Unidade novaUnidadeProduto) {
        this.unidadeProduto = novaUnidadeProduto;
    }

    public void alterarCategoriaProduto(String novaCategoriaProduto) {
        this.categoriaProduto = new CategoriaProduto(CodigoAlfaCurto.valueOf(novaCategoriaProduto), DescricaoBreve.valueOf(novaCategoriaProduto));
    }

    public CodigoAlfaLongo codigoComercial() {
        return codigoComercial;
    }

    public DescricaoBreve descricaoBreve() {
        return descricaoBreve;
    }

    public Descricao descricaoCompleta() {
        return descricaoCompleta;
    }

    public CategoriaProduto categoriaProduto() {
        return categoriaProduto;
    }

    public FichaProducao fichaProducao() { return fichaProducao; }
}
