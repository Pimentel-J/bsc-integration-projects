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


import java.util.Calendar;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import eapli.framework.domain.model.AggregateRoot;
import eapli.framework.domain.model.DomainEntities;
import eapli.shopfloor.general.CodigoAlfaCurto;

/**
 * Linha de Produção
 *
 *Diogo
 */

@Entity
public class LinhaProducao implements AggregateRoot<CodigoAlfaCurto> {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long pk;

    @Version
    private Long version;

    @AttributeOverride(name = "codigo", column = @Column(name = "id_linhaproducao",unique =true))
    private CodigoAlfaCurto idLinhaProducao;

    @Enumerated(EnumType.STRING)
    private EstadoProcessamentoLinhaProducao estadoProcessamento;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar ultimaExecucaoProcessamento;
    
    protected LinhaProducao() {
        // construtor vazio para ORM
    }

    public LinhaProducao(final CodigoAlfaCurto idLinhaProducao) {
        this.idLinhaProducao = idLinhaProducao;
        this.estadoProcessamento = EstadoProcessamentoLinhaProducao.ATIVO;
    }

    @Override
    public CodigoAlfaCurto identity() {
        return this.idLinhaProducao;
    }

    @Override
    public boolean hasIdentity(final CodigoAlfaCurto id) {
        return id.equals(this.idLinhaProducao);
    }

    @Override
    public boolean sameAs(final Object o) {  return DomainEntities.areEqual(this, o); }

    @Override
    public int hashCode() {
        return DomainEntities.hashCode(this);
    }

    @Override
    public String toString() {
        return "LinhaProducao{" + "idLinhaProducao=" + idLinhaProducao + ", version=" + version + '}';
    }
}
