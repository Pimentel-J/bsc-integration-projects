package eapli.shopfloor.gestaoproducao.application;

import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import eapli.shopfloor.gestaoproducao.domain.FichaProducao;
import eapli.shopfloor.gestaoproducao.domain.FichaProducaoBuilder;
import eapli.shopfloor.gestaoproducao.domain.MateriaPrima;
import eapli.shopfloor.gestaoproducao.domain.Material;
import eapli.shopfloor.gestaoproducao.domain.Produto;
import eapli.shopfloor.gestaoproducao.domain.QtdStandardFichaProducao;
import eapli.shopfloor.gestaoproducao.domain.QuantidadeMateriaPrima;
import eapli.shopfloor.gestaoproducao.repositories.ProdutoRepository;
import eapli.shopfloor.infrastructure.persistence.PersistenceContext;
import eapli.shopfloor.usermanagement.domain.BaseRoles;

public class RegistarFichaProducaoController {
	
	private final AuthorizationService authz = AuthzRegistry.authorizationService();
	private final ListProdutosService listProdutosService = new ListProdutosService();
	private final ListMateriaisService listMateriaisService = new ListMateriaisService();
	private final FichaProducaoBuilder fpBuilder = new FichaProducaoBuilder();
	private final ProdutoRepository produtoRepository = PersistenceContext.repositories().produtos();
	private Produto produtoReferencia;
	
	public void registarFichaProducao() {
		authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.GESTOR_PRODUCAO, BaseRoles.POWER_USER);
		FichaProducao fichaProducao = fpBuilder.build();
		
		this.produtoReferencia.associarFichaProducao(fichaProducao);

		this.produtoRepository.save(produtoReferencia);
	}
	
	public Iterable<Produto> listarProdutos() {
        return this.listProdutosService.allProdutos();
    }
	
	public Iterable<Material> listarMateriais() {
        return this.listMateriaisService.allMateriais();
    }
	
	public void associarAProduto(final Produto produto) {
		this.produtoReferencia = produto;
	}
	
	public void adicionarQtdStandard(final int quantidade) {
		fpBuilder.withQuantidadeStandard(new QtdStandardFichaProducao(quantidade, produtoReferencia.unidade()));
	}
	
	public void adicionarMateriaPrima(final Produto produto, final double quantidade) {
		MateriaPrima materiaPrima = new MateriaPrima(produto, new QuantidadeMateriaPrima((float)quantidade, produtoReferencia.unidade()));
		fpBuilder.withMateriaPrima(materiaPrima);
	}
	
	public void adicionarMateriaPrima(final Material material, final double quantidade) {
		MateriaPrima materiaPrima = new MateriaPrima(material, new QuantidadeMateriaPrima((float)quantidade, produtoReferencia.unidade()));
		fpBuilder.withMateriaPrima(materiaPrima);
	}
	
}
