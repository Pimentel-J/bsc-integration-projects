/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates and open the template
 * in the editor.
 */
package eapli.shopfloor.app.backoffice.console.presentation.authz;

import eapli.framework.actions.Action;

/**
 *
 *losa
 */
public class ListUsersAction implements Action {

    @Override
    public boolean execute() {
        return new ListUsersUI().show();
    }
}
