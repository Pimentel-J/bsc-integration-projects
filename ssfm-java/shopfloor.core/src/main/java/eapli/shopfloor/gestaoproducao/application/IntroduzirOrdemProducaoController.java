/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.shopfloor.gestaoproducao.application;

import eapli.shopfloor.gestaoproducao.domain.Produto;
import eapli.shopfloor.infrastructure.persistence.PersistenceContext;
import eapli.shopfloor.usermanagement.domain.BaseRoles;
import eapli.framework.application.UseCaseController;
import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import eapli.shopfloor.general.CodigoAlfaCurto;
import eapli.shopfloor.general.CodigoAlfaLongo;
import eapli.shopfloor.gestaoproducao.domain.Encomenda;
import eapli.shopfloor.gestaoproducao.domain.OrdemProducao;
import eapli.shopfloor.gestaoproducao.domain.OrdemProducaoBuilder;
import eapli.shopfloor.gestaoproducao.repositories.OrdemProducaoRepository;
import eapli.shopfloor.gestaoproducao.repositories.ProdutoRepository;
import java.util.Calendar;

/**
 *
 *.se.1181483
 */
@UseCaseController
public class IntroduzirOrdemProducaoController {

    private final OrdemProducaoRepository ordemProducaoRepository = PersistenceContext.repositories().ordensProducao();
    private final AuthorizationService authorizationService = AuthzRegistry.authorizationService();
    private final ProdutoRepository produtoRepository = PersistenceContext.repositories().produtos();
    private final OrdemProducaoBuilder opBuilder = new OrdemProducaoBuilder();

    public OrdemProducao introduzirOrdemProducao() {
        authorizationService.ensureAuthenticatedUserHasAnyOf(BaseRoles.POWER_USER,
                BaseRoles.GESTOR_PRODUCAO);

        OrdemProducao newOrdemProducao = opBuilder.build();
//        produto.associarOrdemProducao(ordemProducao);
        return this.ordemProducaoRepository.save(newOrdemProducao);
    }

    public boolean adicionarIdOrdem(String idOrdem) {
        if (!existeIdOrdem(CodigoAlfaCurto.valueOf(idOrdem))) {
            opBuilder.comIdOrdem(CodigoAlfaCurto.valueOf(idOrdem));
            return true;
        }
        System.out.println("Já existe uma Ordem de Produção com esse id.");
        return false;
    }

    public void adicionarDataEmissao(Calendar dataEmissao) {
        opBuilder.comDataEmissao(dataEmissao);
    }

    public boolean adicionarDataPrevistaExecucao(Calendar dataPrevista) {
        try {
            opBuilder.comDataPrevistaExecucao(dataPrevista);
        } catch (IllegalArgumentException iae) {
            System.out.println("Data Prevista de Execução deve ser superior à Data de Emissão");
            return false;
        }
        return true;
    }

    public void adicionarQuantidade(int quantidade) {
        opBuilder.comQuantidade(quantidade);
    }

    public boolean adicionarProduto(String codFabricoProd) {
        if (existeProduto(CodigoAlfaCurto.valueOf(codFabricoProd))) {
            final Produto produto = devolveProdutoPorCodigo(CodigoAlfaCurto.valueOf(codFabricoProd));
            opBuilder.comProduto(produto);
            return true;
        }
        System.out.println("Produto não encontrado em Sistema.");
        return false;
    }

    public void adicionarEncomenda(String idEncomenda) {
        opBuilder.comEncomenda(new Encomenda(CodigoAlfaLongo.valueOf(idEncomenda)));
    }

    public boolean existeProduto(CodigoAlfaCurto codFabrico) {
        return produtoRepository.containsProduto(codFabrico) != null;
    }

    public Produto devolveProdutoPorCodigo(CodigoAlfaCurto codFabrico) {
        return produtoRepository.containsProduto(codFabrico);
    }

    public boolean existeIdOrdem(CodigoAlfaCurto idOrdem) {
        return ordemProducaoRepository.containsOrdemProducao(idOrdem) != null;
    }

}
