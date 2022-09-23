package eapli.shopfloor.gestaoproducao.domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.ManyToOne;

import eapli.framework.domain.model.ValueObject;

/**
 *pmagalhaes
 *
 */

@Embeddable
@Access(AccessType.FIELD)
public class MateriaPrima implements ValueObject{

	private static final long serialVersionUID = 1L;

	@ManyToOne
	private Produto produto;
	@ManyToOne
	private Material material;
	@Embedded
	private QuantidadeMateriaPrima quantidade;

	protected MateriaPrima () {
		// ORM
	}

	/**
	 * Construtor de Matéria-Prima, no caso de se referir a um Produto
	 * @param produto
	 * @param quantidade
	 */
	public MateriaPrima (final Produto produto, final QuantidadeMateriaPrima quantidade) {
		this.produto = produto;
		this.quantidade = quantidade;
	}

	/**
	 * Construtor de Matéria-Prima, no caso de se referir a um Material
	 * @param material
	 * @param quantidade
	 */
	public MateriaPrima (final Material material, final QuantidadeMateriaPrima quantidade) {
		// Construtor para quando a matéria-prima é um material
		this.material = material;
		this.quantidade = quantidade;
	}

	public Produto produto() {
		return produto;
	}

	public Material material() {
		return material;
	}

	public QuantidadeMateriaPrima quantidade() {
		return quantidade;
	}

}
