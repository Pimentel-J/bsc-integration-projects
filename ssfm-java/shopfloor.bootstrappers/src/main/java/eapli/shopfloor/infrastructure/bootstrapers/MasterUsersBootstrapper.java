/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates and open the template
 * in the editor.
 */
package eapli.shopfloor.infrastructure.bootstrapers;

import java.util.HashSet;
import java.util.Set;

import eapli.shopfloor.usermanagement.domain.BaseRoles;
import eapli.framework.actions.Action;
import eapli.framework.infrastructure.authz.domain.model.Role;

/**
 *Paulo Gandra Sousa
 */
public class MasterUsersBootstrapper extends UsersBootstrapperBase implements Action {

    @Override
    public boolean execute() {
        registerAdmin("admin", TestDataConstants.PASSWORD1, "Jane", "Doe Admin",
                "jane.doe@email.local");
        registerGestorChaoFabrica("gcf", TestDataConstants.PASSWORD1, "Antonio", "Fagundes",
                "af@email.local");
        registerGestorProducao("gp", TestDataConstants.PASSWORD1, "Jack", "Black",
                "jb@email.local");
        registerSPM("spm", TestDataConstants.PASSWORD1, "Aurelio", "Soares",
                "as@email.local");
        return true;
    }

    /**
     *
     */
    private void registerAdmin(final String username, final String password, final String firstName,
            final String lastName, final String email) {
        final Set<Role> roles = new HashSet<>();
        roles.add(BaseRoles.ADMIN);

        registerUser(username, password, firstName, lastName, email, roles);
    }

    private void registerGestorChaoFabrica(final String username, final String password, final String firstName,
            final String lastName, final String email) {
		final Set<Role> roles = new HashSet<>();
		roles.add(BaseRoles.GESTOR_CHAO_FABRICA);
		
		registerUser(username, password, firstName, lastName, email, roles);
	}
	
	private void registerGestorProducao(final String username, final String password, final String firstName,
	                     final String lastName, final String email) {
		final Set<Role> roles = new HashSet<>();
		roles.add(BaseRoles.GESTOR_PRODUCAO);
		
		registerUser(username, password, firstName, lastName, email, roles);
	}
	
	private void registerSPM(final String username, final String password, final String firstName,
			final String lastName, final String email) {
		final Set<Role> roles = new HashSet<>();
		roles.add(BaseRoles.SPM);
		
		registerUser(username, password, firstName, lastName, email, roles);
	}
    
}
