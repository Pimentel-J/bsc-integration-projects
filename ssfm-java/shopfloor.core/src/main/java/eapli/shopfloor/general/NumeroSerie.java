package eapli.shopfloor.general;

import eapli.framework.domain.model.ValueObject;
import eapli.framework.util.HashCoder;

import javax.persistence.Embeddable;

/**
 * Conceito de Número de série (inteiro)
 *
 *Diogo
 */
@Embeddable
public class NumeroSerie implements ValueObject, Comparable<NumeroSerie> {

    private static final long serialVersionUID = 1L;

    private final Integer numero;

    protected NumeroSerie(final Integer numero) {
        if (numero < 0 && numero > 65535) {
            throw new IllegalArgumentException("Número de série deve ser inteiro, maior que zero e menor que 65535");
        }
        this.numero = numero;
    }

    protected NumeroSerie() {
        //for ORM
        this.numero = null;
    }

    public Integer numero() {
        return this.numero;
    }
    
    

    public static NumeroSerie valueOf(final String numero) { return new NumeroSerie(Integer.valueOf(numero)); }
    
    public static NumeroSerie valueOf(final Integer numero) { return new NumeroSerie(numero); }

    public int compareTo(NumeroSerie o) {
        return this.numero.compareTo(o.numero);
    }

    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        } else if (!(o instanceof NumeroSerie)) {
            return false;
        } else {
            NumeroSerie other = (NumeroSerie) o;
            return this.numero.equals(other.numero);
        }
    }

    public int hashCode() {
        return (new HashCoder()).with(this.numero).code();
    }

    @Override
    public String toString() {
        return "NumeroSerie{" + "numero=" + numero + '}';
    }
}