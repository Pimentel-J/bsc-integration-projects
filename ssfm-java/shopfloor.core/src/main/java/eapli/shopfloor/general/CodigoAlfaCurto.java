package eapli.shopfloor.general;

import eapli.framework.domain.model.ValueObject;
import eapli.framework.util.HashCoder;
import eapli.framework.util.StringMixin;
import eapli.framework.validations.StringPredicates;
import javax.persistence.Access;
import javax.persistence.AccessType;

import javax.persistence.Embeddable;
import javax.persistence.Transient;

/**
 * Conceito de código genérico (alfanumérico curto)
 *
 *.se.1181483
 */
@Embeddable
@Access(AccessType.FIELD)
public class CodigoAlfaCurto implements ValueObject, Comparable<CodigoAlfaCurto>, StringMixin {

    private static final long serialVersionUID = 1L;

    private final String codigo;
    @Transient
    private int tamanho = 10;

    protected CodigoAlfaCurto(final String codigo) {
        if (StringPredicates.isNullOrEmpty(codigo)) {
            throw new IllegalArgumentException("Código não pode ser nulo ou vazio");
        }
        if (codigo.length() > tamanho) {
            throw new IllegalArgumentException("Código não pode ter dimensão superior a 10 caracteres");
        }
        if (!(codigo.matches("[A-z0-9]+"))) {
            throw new IllegalArgumentException("Código deve ser alfanumérico");
        }
        this.codigo = codigo;
    }

    public CodigoAlfaCurto() {
        this.codigo = null;
    }

    public static CodigoAlfaCurto valueOf(final String codigo) {
        return new CodigoAlfaCurto(codigo);
    }

    public String codigo() {
        return codigo;
    }

    public int compareTo(CodigoAlfaCurto o) {
        return this.codigo.compareTo(o.codigo);
    }

    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        } else if (!(o instanceof CodigoAlfaCurto)) {
            return false;
        } else {
            CodigoAlfaCurto other = (CodigoAlfaCurto)o;
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

}