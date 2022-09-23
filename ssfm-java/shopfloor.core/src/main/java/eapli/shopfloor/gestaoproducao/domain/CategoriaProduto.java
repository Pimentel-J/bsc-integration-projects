/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.shopfloor.gestaoproducao.domain;

import eapli.framework.domain.model.ValueObject;
import eapli.framework.validations.Preconditions;
import eapli.shopfloor.general.CodigoAlfaCurto;
import eapli.shopfloor.general.DescricaoBreve;
import javax.persistence.Embeddable;

/**
 *
 *.se.1181483
 */
@Embeddable
public class CategoriaProduto implements ValueObject {

    private static final long serialVersionUID = 1L;

    private final CodigoAlfaCurto codigoCategoria; //substituir pelo tipo novo (codigo alfanum 10 carac)
    private final DescricaoBreve descricaoCategoria; //substituir pelo tipo novo (dimensao max 30 carac)

    public CategoriaProduto(CodigoAlfaCurto codigoCategoria, DescricaoBreve descricaoCategoria) {
        Preconditions.noneNull(codigoCategoria, descricaoCategoria);

        this.codigoCategoria = codigoCategoria;
        this.descricaoCategoria = descricaoCategoria;
    }

    protected CategoriaProduto() {
        // for ORM
        codigoCategoria = null;
        descricaoCategoria = null;
    }

    public CodigoAlfaCurto CodigoCategoria() {
        return codigoCategoria;
    }

    public DescricaoBreve DescricaoCategoria() {
        return descricaoCategoria;
    }
}
