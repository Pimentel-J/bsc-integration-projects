package eapli.shopfloor.gestaochaofabrica.domain;

import eapli.framework.domain.model.AggregateRoot;
import eapli.framework.domain.model.DomainEntities;
import eapli.framework.general.domain.model.Description;
import eapli.framework.validations.Preconditions;
import eapli.shopfloor.general.CodigoAlfaCurto;
import eapli.shopfloor.general.Descricao;
import eapli.shopfloor.general.NumeroSerie;

import javax.persistence.*;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

/**
 *Diogo
 */
@Entity
public class Maquina implements AggregateRoot<CodigoAlfaCurto> {

    private static final long serialVersionUID = 1L;

    // ORM primary key
    @Id
    @GeneratedValue
    private Long pk;

    @Version
    private Long version;


    @AttributeOverride(name = "codigo", column = @Column(name = "id_maquina",unique = true, nullable = false))
    private CodigoAlfaCurto idMaquina;

    @AttributeOverride(name = "name", column = @Column(name = "num_serie", nullable = false))
    private NumeroSerie numSerie;

    @AttributeOverride(name = "descricao", column = @Column(name = "descricao_maquina", nullable = false))
    private Descricao descricao;

    @Temporal(TemporalType.DATE)
    private Calendar dataInstalacao;

    @AttributeOverride(name = "codigo", column = @Column(name = "marca", nullable = false))
    private Description marca;

    @AttributeOverride(name = "codigo", column = @Column(name = "modelo", nullable = false))
    private CodigoAlfaCurto modelo;

    @AttributeOverride(name = "numero", column = @Column(name = "id_Protocolo"))
    private NumeroSerie idProtocolo;

    @AttributeOverride(name = "numero", column = @Column(name = "sequencia"))
    private Integer sequencia;

    @ManyToOne
    private LinhaProducao linhaProducao;

    @ElementCollection
    @JoinTable(name = "FICHEIROS_CONFIG_MAQUINA", joinColumns = @JoinColumn(name = "MAQUINA_IDMAQUINA"))
    private Set<FicheiroConfigMaquina> ficheirosConfig;

    //CONSTRUTORES

    //construtor vazio para ORM
    protected Maquina() {
    }

    /**
     * Construtor Completo de Maquina
     *
     * @param idMaquina
     * @param numSerie
     * @param descricao
     * @param dataInstalacao
     * @param marca
     * @param modelo
     * @param linhaProducao
     */
    public Maquina(final CodigoAlfaCurto idMaquina, final NumeroSerie numSerie, final Descricao descricao,
                   final Calendar dataInstalacao, final Description marca, final CodigoAlfaCurto modelo,
                   final LinhaProducao linhaProducao, final Integer sequencia) {
        Preconditions.noneNull(idMaquina, numSerie, descricao, dataInstalacao, marca, modelo, linhaProducao,
                sequencia, "Todos os campos têm de ser preenchidos");
        Preconditions.nonNegative(sequencia);
        this.idMaquina = idMaquina;
        this.numSerie = numSerie;
        this.descricao = descricao;
        this.dataInstalacao = dataInstalacao;
        this.marca = marca;
        this.modelo = modelo;
        this.linhaProducao = linhaProducao;
        this.sequencia = sequencia;
        this.ficheirosConfig = new HashSet<>();
        this.idProtocolo = NumeroSerie.valueOf("0");
    }
    
    public Maquina(final CodigoAlfaCurto idMaquina, final NumeroSerie numSerie, final Descricao descricao,
                   final Calendar dataInstalacao, final Description marca, final CodigoAlfaCurto modelo,
                   final LinhaProducao linhaProducao, final Integer sequencia, final int idProtocolo) {
        Preconditions.noneNull(idMaquina, numSerie, descricao, dataInstalacao, marca, modelo, linhaProducao,
                sequencia, "Todos os campos têm de ser preenchidos");
        Preconditions.nonNegative(sequencia);
        this.idMaquina = idMaquina;
        this.numSerie = numSerie;
        this.descricao = descricao;
        this.dataInstalacao = dataInstalacao;
        this.marca = marca;
        this.modelo = modelo;
        this.linhaProducao = linhaProducao;
        this.sequencia = sequencia;
        this.ficheirosConfig = new HashSet<>();
        this.idProtocolo = NumeroSerie.valueOf(idProtocolo);
    }

    public boolean hasIdentity(final CodigoAlfaCurto id) {
        return id.equals(this.idMaquina);
    }


    @Override
    public CodigoAlfaCurto identity() {
        return this.idMaquina;
    }

    public NumeroSerie numSerie() {
        return numSerie;
    }

    public Descricao descricaoMaquina() {
        return this.descricao;
    }

    public Calendar dataInstalacaoMaquina() {
        return this.dataInstalacao;
    }

    public Description marcaMaquina() {
        return this.marca;
    }

    public LinhaProducao linhaProducaoMaquina() {
        return linhaProducao;
    }

    public CodigoAlfaCurto modeloMaquina() {
        return this.modelo;
    }

    public NumeroSerie idProtocoloMaquina() {
        return idProtocolo;
    }

    public Integer sequenciaMaquina() {
        return sequencia;
    }

    public void updateSequenciaMaquina(Integer newSequencia) { this.sequencia = newSequencia; }

    public NumeroSerie idProtocolo() {
        return this.idProtocolo;
    }
    
    public void associarIdProtocolo(NumeroSerie numIntScm){
        this.idProtocolo = numIntScm;
    }
    

    public void addFicheiros(FicheiroConfigMaquina ficheiro) {
        this.ficheirosConfig.add(ficheiro);
    }

    @Override
    public boolean sameAs(final Object other) {
        final Maquina maquina = (Maquina) other;
        return this.equals(maquina) && this.idMaquina.equals(maquina.idMaquina);
    }

    @Override
    public int hashCode() {
        return DomainEntities.hashCode(this);
    }

    @Override
    public boolean equals(final Object o) {
        return DomainEntities.areEqual(this, o);
    }

    @Override
    public String toString() {
        return "Maquina{" + "iDMaquina=" + idMaquina + ", version=" + version + ", numSerie='" + numSerie + '\'' +
                ", descricao='" + descricao + '\'' + ", dataInstalacao=" + dataInstalacao + ", marca='" + marca + '\'' +
                ", modelo='" + modelo + '\'' + '}';
    }
}

