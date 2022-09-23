package eapli.shopfloor.general;

import eapli.framework.domain.model.ValueObject;
import eapli.framework.general.domain.model.Designation;
import eapli.framework.util.HashCoder;
import eapli.framework.util.StringMixin;
import eapli.framework.validations.StringPredicates;

import javax.persistence.Embeddable;
import javax.persistence.Transient;

/**
 * Conceito de descrição genérico
 *
 *.se.1181483
 */
@Embeddable
public class Descricao implements ValueObject, Comparable<Descricao>, StringMixin {

    private static final long serialVersionUID = 1L;

    private String descricao;
    @Transient
    private int tamanho = 50;

    protected Descricao(final String descricao) {
        if (StringPredicates.isNullOrEmpty(descricao)) {
            throw new IllegalArgumentException("Descrição não pode ser nulo ou vazio");
        }
        if (descricao.length() > tamanho) {
            throw new IllegalArgumentException("Descrição não pode ter dimensão superior a 50 caracteres");
        }
        if (!(descricao.matches("[A-zÀ-ú0-9\\-#\\.\\(\\)\\/%&\\s]+"))) {
            throw new IllegalArgumentException("Descrição deve ser alfanumérico");
        }
        this.descricao = descricao;
    }

    protected Descricao() {
        this.descricao = null;
    }

    public static Descricao valueOf(final String descricao) {
        return new Descricao(descricao);
    }

    public String descricao() {
        return descricao;
    }

    public int compareTo(Descricao o) {
        return this.descricao.compareTo(o.descricao());
    }

    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        } else if (!(o instanceof Descricao)) {
            return false;
        } else {
            Descricao other = (Descricao) o;
            return this.descricao.equals(other.descricao);
        }
    }

    public int hashCode() {
        return (new HashCoder()).with(this.descricao).code();
    }

    @Override
    public String toString() {
        return this.descricao;
    }

}
