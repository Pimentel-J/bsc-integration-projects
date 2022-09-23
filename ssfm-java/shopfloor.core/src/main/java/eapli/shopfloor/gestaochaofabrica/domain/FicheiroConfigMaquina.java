package eapli.shopfloor.gestaochaofabrica.domain;

import eapli.framework.domain.model.ValueObject;
import eapli.framework.validations.Preconditions;

import javax.persistence.*;
import java.util.Objects;

/**
 * Classe representativa do conteúdo do ficheiro de configuração de máquina
 *
 *João Pimentel 
 */
@Embeddable
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"MAQUINA_IDMAQUINA", "NOME"})} )
public class FicheiroConfigMaquina implements ValueObject {

    private static final long serialVersionUID = 1L;

    @Column(name = "nome", nullable = false)
    private String nome;
    @Column(name = "conteudo", nullable = false)
    private String conteudo;

    protected FicheiroConfigMaquina() {
        //    for ORM
    }

    /**
     * Construtor Ficheiro Configuração Máquina
     *
     * @param nome
     * @param conteudo
     */
    public FicheiroConfigMaquina(final String nome, final String conteudo) {
        Preconditions.noneNull(nome, "Nome do ficheiro null");
        Preconditions.noneNull(conteudo, "Conteúdo do ficheiro null");
        Preconditions.nonEmpty(nome, "Nome do ficheiro vazio");
        Preconditions.nonEmpty(conteudo, "Conteúdo do ficheiro vazio");
        Preconditions.ensure(!conteudo.equalsIgnoreCase("[]"), "Conteúdo do ficheiro vazio");
        this.nome = nome;
        this.conteudo = conteudo;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FicheiroConfigMaquina)) {
            return false;
        }

        final FicheiroConfigMaquina that = (FicheiroConfigMaquina) o;
        return nome.equals(that.nome) && conteudo.equals(that.conteudo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nome, conteudo);
    }

    @Override
    public String toString() {
        return getClass().getName() + ':' + this.nome;
    }

}
