/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.shopfloor.gestaoproducao.application;

import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import eapli.shopfloor.gestaoproducao.domain.Unidade;
import eapli.shopfloor.usermanagement.domain.BaseRoles;
import java.util.Arrays;

/**
 *
 *.se.1181483
 */
public class ListUnidadesService {
    
    private final AuthorizationService authz = AuthzRegistry.authorizationService();
    
    public Iterable<Unidade> allUnidades() {
        authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.GESTOR_PRODUCAO, BaseRoles.POWER_USER);
        Iterable<Unidade> iterable = Arrays.asList(Unidade.values());
        return iterable;
    }
}
