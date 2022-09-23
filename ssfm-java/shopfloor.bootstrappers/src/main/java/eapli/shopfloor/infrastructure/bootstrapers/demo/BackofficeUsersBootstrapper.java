/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates and open the template
 * in the editor.
 */
package eapli.shopfloor.infrastructure.bootstrapers.demo;

import java.util.HashSet;
import java.util.Set;

import eapli.shopfloor.infrastructure.bootstrapers.TestDataConstants;
import eapli.shopfloor.infrastructure.bootstrapers.UsersBootstrapperBase;
import eapli.shopfloor.usermanagement.domain.BaseRoles;
import eapli.framework.actions.Action;
import eapli.framework.infrastructure.authz.domain.model.Role;

/**
 *Paulo Gandra Sousa
 */
public class BackofficeUsersBootstrapper extends UsersBootstrapperBase implements Action {

    @SuppressWarnings("squid:S2068")
    private static final String PASSWORD1 = "Password1";

    @Override
    public boolean execute() {

        return true;
    }


}
