package eapli.shopfloor.general;

import eapli.framework.domain.model.ValueObject;
import eapli.framework.general.domain.model.Designation;
import eapli.framework.util.HashCoder;
import eapli.framework.util.StringMixin;
import eapli.framework.validations.StringPredicates;
import javax.persistence.Access;
import javax.persistence.AccessType;

import javax.persistence.Embeddable;
import javax.persistence.Transient;

/**
 * Conceito de código genérico (alfanumérico longo)
 *
 *.se.1181483
 */
@Embeddable
@Access(AccessType.FIELD)
public class CodigoAlfaLongo implements ValueObject, Comparable<CodigoAlfaLongo>, StringMixin {

    private static final long serialVersionUID = 1L;

    private final String codigo;
    @Transient
    private int tamanho = 15;

    protected CodigoAlfaLongo(final String codigo) {
        if (StringPredicates.isNullOrEmpty(codigo)) {
            throw new IllegalArgumentException("Código não pode ser nulo ou vazio");
        }
        if (codigo.length() > tamanho) {
            throw new IllegalArgumentException("Código não pode ter dimensão superior a 15 caracteres");
        }
        if (!(codigo.matches("[A-zÀ-ú0-9\\-#\\.\\(\\)\\/%&\\s]+"))) {
            throw new IllegalArgumentException("Código deve ser alfanumérico");
        }
        this.codigo = codigo;
    }

    public CodigoAlfaLongo() {
        this.codigo = null;
    }

    public static CodigoAlfaLongo valueOf(final String codigo) {
        return new CodigoAlfaLongo(codigo);
    }

    public int compareTo(CodigoAlfaLongo o) {
        return this.codigo.compareTo(o.codigo);
    }

    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        } else if (!(o instanceof CodigoAlfaLongo)) {
            return false;
        } else {
            CodigoAlfaLongo other = (CodigoAlfaLongo) o;
            return this.codigo.equals(other.codigo);
        }
    }

    public int hashCode() {
        return (new HashCoder()).with(this.codigo).code();
    }

    @Override
    public String toString() {
        return this.codigo;
    }

    public String codigo() { return codigo;  }

}