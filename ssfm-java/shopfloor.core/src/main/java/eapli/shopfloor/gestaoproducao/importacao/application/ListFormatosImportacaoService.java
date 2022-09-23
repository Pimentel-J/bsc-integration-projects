/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.shopfloor.gestaoproducao.importacao.application;

import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import eapli.shopfloor.usermanagement.domain.BaseRoles;
import java.util.Arrays;

/**
 *
 *.se.1181483
 */
public class ListFormatosImportacaoService {
    private final AuthorizationService authz = AuthzRegistry.authorizationService();
    
    public Iterable<ImportFileFormat> allFormatos(){
        authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.GESTOR_PRODUCAO, BaseRoles.POWER_USER);
        Iterable<ImportFileFormat> iterable = Arrays.asList(ImportFileFormat.values());
        return iterable;
    }
}
