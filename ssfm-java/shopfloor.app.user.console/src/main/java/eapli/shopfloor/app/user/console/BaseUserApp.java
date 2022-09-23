package eapli.shopfloor.app.user.console;

import eapli.shopfloor.app.user.console.presentation.FrontMenu;
import eapli.shopfloor.infrastructure.persistence.PersistenceContext;
import eapli.shopfloor.usermanagement.domain.BasePasswordPolicy;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import eapli.framework.infrastructure.authz.domain.model.PlainTextEncoder;

/**
 * Base User App.
 */
@SuppressWarnings("squid:S106")
public final class BaseUserApp {

    /**
     * Empty constructor is private to avoid instantiation of this class.
     */
    private BaseUserApp() {
    }

    public static void main(final String[] args) {
        System.out.println("=====================================");
        System.out.println("Base User App");
        System.out.println("(C) 2016 - 2019");
        System.out.println("=====================================");

        AuthzRegistry.configure(PersistenceContext.repositories().users(),
                new BasePasswordPolicy(), new PlainTextEncoder());

        new FrontMenu().show();

        // exiting the application, closing all threads
        System.exit(0);
    }
}
