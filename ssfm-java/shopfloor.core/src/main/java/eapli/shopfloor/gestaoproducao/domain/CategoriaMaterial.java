package eapli.shopfloor.gestaoproducao.domain;

import eapli.framework.domain.model.AggregateRoot;
import eapli.framework.domain.model.DomainEntities;
import eapli.framework.validations.Preconditions;
import eapli.shopfloor.general.CodigoAlfaCurto;
import eapli.shopfloor.general.Descricao;

import javax.persistence.*;

/**
 * Categoria de Material
 *
 *João Pimentel 
 */
@Entity
public class CategoriaMaterial implements AggregateRoot<CodigoAlfaCurto> {

    private static final long serialVersionUID = 1L;

    @Version
    private Long version;

    @EmbeddedId
    @Column(unique = true, nullable = false)
    private CodigoAlfaCurto codigoCategoriaMaterial;
    private Descricao descricaoCategoriaMaterial;

    protected CategoriaMaterial() {
        // for ORM
    }

    /**
     * Construtor Categoria do Material
     *
     * @param codigo    alfanumérico com 10 caracteres no máximo
     * @param descricao
     */
    public CategoriaMaterial(final CodigoAlfaCurto codigo, final Descricao descricao) {
        Preconditions.noneNull(codigo, descricao, "Os campos têm de ser todos preenchidos");
        this.codigoCategoriaMaterial = codigo;
        this.descricaoCategoriaMaterial = descricao;
    }

    @Override
    public CodigoAlfaCurto identity() { return this.codigoCategoriaMaterial; }

    public Descricao descricao() { return this.descricaoCategoriaMaterial; }

    @Override
    public boolean sameAs(final Object o) { return DomainEntities.areEqual(this, o); }

    @Override
    public int hashCode() {
        return DomainEntities.hashCode(this);
    }

    @Override
    public String toString() {
        return "Código/descrição categoria material: " + codigoCategoriaMaterial + " - " + descricaoCategoriaMaterial;
    }
}
