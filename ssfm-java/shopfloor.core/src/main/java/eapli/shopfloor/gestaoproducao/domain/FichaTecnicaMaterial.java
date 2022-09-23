package eapli.shopfloor.gestaoproducao.domain;

import eapli.framework.domain.model.ValueObject;
import eapli.framework.validations.Preconditions;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

/**
 * Ficha Técnica de Material
 *
 *João Pimentel 
 */
@Embeddable
public class FichaTecnicaMaterial implements ValueObject {

    private static final long serialVersionUID = 1L;

    @Column(name = "ficha", unique = true, nullable = false)
    private String nome;
    @Column(name = "ficha_path", unique = true, nullable = false)
    private String path;

    //    @Lob // guardar o conteúdo do ficheiro num campo da BD
    //    @Column(name = "pdf", columnDefinition = "BLOB") // Large Object - BLOB (Binary LOB) or CLOB (Character LOB)
    //    private byte[] pdf;

    protected FichaTecnicaMaterial() {
        //    for ORM
    }

    /**
     * Construtor Ficha Técnica Material
     *
     * @param nome
     * @param path
     */
    protected FichaTecnicaMaterial(final String nome, final String path) {
        Preconditions.noneNull(nome, path, "Todos os campos têm de ser preenchidos");
        Preconditions.nonEmpty(nome, "O nome não pode ser vazio");
        Preconditions.nonEmpty(path, "O path não pode ser vazio");
        this.nome = nome;
        this.path = nome;
    }

    public static FichaTecnicaMaterial valueOf(final String value, final String path) {
        return new FichaTecnicaMaterial(value, path);
    }

    public String nome() {
        return this.nome;
    }

    public String path() { return path; }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FichaTecnicaMaterial)) {
            return false;
        }

        final FichaTecnicaMaterial that = (FichaTecnicaMaterial) o;
        return nome.equals(that.nome) && path.equals(that.path);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nome, path);
    }

    @Override
    public String toString() {
        return getClass().getName() + ':' + this.nome;
    }

    //    public int compareTo(final FichaTecnicaMaterial o) {
    //        return nome.compareTo(o.nome);
    //    }

}
