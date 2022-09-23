package eapli.shopfloor.general;

import eapli.framework.domain.model.ValueObject;
import eapli.framework.general.domain.model.Description;
import eapli.framework.util.HashCoder;
import eapli.framework.util.StringMixin;
import eapli.framework.validations.StringPredicates;

import javax.persistence.Embeddable;
import javax.persistence.Transient;

/**
 * Conceito de descrição breve genérico
 *
 *.se.1181483
 */
@Embeddable
public class DescricaoBreve implements ValueObject, Comparable<DescricaoBreve>, StringMixin {

    private static final long serialVersionUID = 1L;

    private String descricaoBreve;
    @Transient
    private int tamanho = 30;

    protected DescricaoBreve(final String descricaoBreve) {
        if (StringPredicates.isNullOrEmpty(descricaoBreve)) {
            throw new IllegalArgumentException("Descrição não pode ser nulo ou vazio");
        }
        if (descricaoBreve.length() > tamanho) {
            throw new IllegalArgumentException("Descrição não pode ter dimensão superior a 30 caracteres");
        }
        if (!(descricaoBreve.matches("[A-zÀ-ú0-9\\-#\\.\\(\\)\\/%&\\s]+"))) {
            throw new IllegalArgumentException("Descrição deve ser alfanumérico");
        }
        this.descricaoBreve = descricaoBreve;
    }

    protected DescricaoBreve() {
        this.descricaoBreve = null;
    }

    public static DescricaoBreve valueOf(final String descricaoBreve) {
        return new DescricaoBreve(descricaoBreve);
    }

    public String descricaoBreve() {
        return descricaoBreve;
    }

    public int compareTo(DescricaoBreve o) {
        return this.descricaoBreve.compareTo(o.descricaoBreve());
    }

    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        } else if (!(o instanceof DescricaoBreve)) {
            return false;
        } else {
            DescricaoBreve other = (DescricaoBreve) o;
            return this.descricaoBreve.equals(other.descricaoBreve);
        }
    }

    public int hashCode() {
        return (new HashCoder()).with(this.descricaoBreve).code();
    }

    @Override
    public String toString() {
        return this.descricaoBreve;
    }
    
}
