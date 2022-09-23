/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.shopfloor.gestaoproducao.application;

import eapli.shopfloor.gestaoproducao.domain.Produto;
import eapli.shopfloor.gestaoproducao.domain.CategoriaProduto;
import eapli.shopfloor.infrastructure.persistence.PersistenceContext;
import eapli.shopfloor.usermanagement.domain.BaseRoles;
import eapli.framework.application.UseCaseController;
import eapli.framework.general.domain.model.Designation;
import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import eapli.shopfloor.general.CodigoAlfaCurto;
import eapli.shopfloor.general.CodigoAlfaLongo;
import eapli.shopfloor.general.Descricao;
import eapli.shopfloor.general.DescricaoBreve;
import eapli.shopfloor.gestaoproducao.domain.Unidade;
import eapli.shopfloor.gestaoproducao.repositories.ProdutoRepository;

/**
 *
 *.se.1181483
 */
@UseCaseController
public class AdicionarProdutoController {
    
    private final ProdutoRepository produtoRepository = PersistenceContext.repositories().produtos();
    private final AuthorizationService authorizationService = AuthzRegistry.authorizationService();
    private final ListUnidadesService unidadesService = new ListUnidadesService();
    
    public Produto adicionarProduto(String codigoFabrico, String codigoComercial, String descricaoBreve, String descricaoCompleta,
            String unidadeProduto, String codigoCategoria, String descricaoCategoria){
        authorizationService.ensureAuthenticatedUserHasAnyOf(BaseRoles.POWER_USER,
                BaseRoles.GESTOR_PRODUCAO);
        
        final Produto newProduto = new Produto(CodigoAlfaCurto.valueOf(codigoFabrico),
        CodigoAlfaLongo.valueOf(codigoComercial), DescricaoBreve.valueOf(descricaoBreve),
                Descricao.valueOf(descricaoCompleta), Unidade.valueOf(unidadeProduto),
        new CategoriaProduto(CodigoAlfaCurto.valueOf(codigoCategoria), DescricaoBreve.valueOf(descricaoCategoria)));
        
        return this.produtoRepository.save(newProduto);
}
    
    public Iterable<Unidade> listarUnidades(){
        return this.unidadesService.allUnidades();
    }
    
}