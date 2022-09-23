package eapli.shopfloor.gestaoproducao.domain;

import eapli.framework.domain.model.AggregateRoot;
import eapli.framework.domain.model.DomainEntities;
import eapli.framework.validations.Preconditions;
import eapli.shopfloor.general.CodigoAlfaLongo;
import eapli.shopfloor.general.Descricao;

import javax.persistence.*;

/**
 * Material
 *
 *João Pimentel 
 */
@Entity
public class Material implements AggregateRoot<CodigoAlfaLongo> {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    @AttributeOverride(name="codigo", column=@Column(name = "codigo_material", unique = true, nullable = false))
    private CodigoAlfaLongo codigoMaterial;
    @Version
    private Long version;
    @AttributeOverride(name="descricao", column=@Column(name = "descricao_material", nullable = false))
    private Descricao descricaoMaterial;
    @Embedded
    @Column(unique = true, nullable = false)
    private FichaTecnicaMaterial fichaTecnicaMaterial;
    @ManyToOne
    private CategoriaMaterial categoriaMaterial;

    protected Material() {
        // for ORM
    }

    /**
     * Construtor de Material
     *
     * @param codigo               alfanuméricos (15 caracteres máximo)
     * @param descricao
     * @param fichaTecnicaMaterial nome e path
     * @param categoria
     */
    public Material(final CodigoAlfaLongo codigo, final Descricao descricao,
                    FichaTecnicaMaterial fichaTecnicaMaterial, CategoriaMaterial categoria) {
        Preconditions.noneNull(codigo, descricao, fichaTecnicaMaterial, categoria,
                "Os campos têm de ser todos preenchidos");

        this.codigoMaterial = codigo;
        this.descricaoMaterial = descricao;
        this.fichaTecnicaMaterial = fichaTecnicaMaterial;
        this.categoriaMaterial = categoria;
    }

    @Override
    public CodigoAlfaLongo identity() { return this.codigoMaterial; }

    public Descricao descricaoMaterial() {
        return this.descricaoMaterial;
    }

    public CategoriaMaterial categoriaMaterial() { return this.categoriaMaterial; }

    public FichaTecnicaMaterial fichaTecnicaMaterial() {
        return this.fichaTecnicaMaterial;
    }

    @Override
    public boolean sameAs(final Object o) {
        return DomainEntities.areEqual(this, o);
    }

    @Override
    public int hashCode() {
        return DomainEntities.hashCode(this);
    }

    @Override
    public String toString() {
        return "Código material: " + codigoMaterial + "\nDescrição material: " + descricaoMaterial;
    }
}
