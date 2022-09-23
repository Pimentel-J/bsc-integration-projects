package eapli.shopfloor.reporting.produtos.dto;

import eapli.framework.representations.dto.DTO;
import eapli.shopfloor.general.CodigoAlfaCurto;
import eapli.shopfloor.general.DescricaoBreve;

/**
 * DTO - Consultar Produtos Sem Ficha
 *
 *João Pimentel 
 */
@DTO
public class ProdutosSemFicha {

    public CodigoAlfaCurto codigo;
    public DescricaoBreve descricao;

    public ProdutosSemFicha(final CodigoAlfaCurto codigo, final DescricaoBreve descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

}
