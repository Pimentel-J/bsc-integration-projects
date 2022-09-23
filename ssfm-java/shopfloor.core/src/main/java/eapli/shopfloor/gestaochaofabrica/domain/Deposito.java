/*
 * Copyright (c) 2013-2019 the original author or authors.
 *
 * MIT License
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package eapli.shopfloor.gestaochaofabrica.domain;


import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Version;

import eapli.framework.domain.model.AggregateRoot;
import eapli.framework.domain.model.DomainEntities;
import eapli.framework.validations.Preconditions;
import eapli.shopfloor.general.CodigoAlfaCurto;
import eapli.shopfloor.general.Descricao;

/**
 * Um depósito.
 *
 *1181455
 *
 */
@Entity
public class Deposito implements AggregateRoot<CodigoAlfaCurto> {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
    private CodigoAlfaCurto codigo;

    @Version
    private Long version;

    @Column(name = "descricao", unique = true, nullable = false)
    private Descricao descricao;

    protected Deposito() {
        // for ORM
    }

    public Deposito(final CodigoAlfaCurto codigo, final Descricao descricao) {
        Preconditions.noneNull(codigo, descricao);

        this.codigo = codigo;
        this.descricao = descricao;
    }

    @Override
    public boolean equals(final Object o) {
        return DomainEntities.areEqual(this, o);
    }

    @Override
    public int hashCode() {
        return DomainEntities.hashCode(this);
    }

    @Override
    public CodigoAlfaCurto identity() {
        return this.codigo;
    }

    public Descricao descricao() {
        return this.descricao;
    }

    @Override
    public boolean sameAs(final Object other) {
        if (!(other instanceof Deposito)) {
            return false;
        }

        final Deposito that = (Deposito) other;
        if (this == that) {
            return true;
        }

        return identity().equals(that.identity());
        // FIXME compare other fields
    }

    @Override
    public String toString() {
        return codigo + " - " + descricao;
    }
}
