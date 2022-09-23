package eapli.shopfloor.general.application;

import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import eapli.shopfloor.general.FileFormats;
import eapli.shopfloor.usermanagement.domain.BaseRoles;

import java.util.Arrays;

/**
 *João Pimentel 
 */
public class ListFileFormatService {

    private final AuthorizationService authz = AuthzRegistry.authorizationService();

    public Iterable<FileFormats> importConfigMaquinaFileFormats() {
        authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.GESTOR_CHAO_FABRICA, BaseRoles.GESTOR_PRODUCAO,
                BaseRoles.POWER_USER);

        Iterable<FileFormats> formatos = Arrays.asList(FileFormats.valueOf("TXT"));

        return formatos;
    }

    public Iterable<FileFormats> exportGestaoProducaoFileFormats() {
        authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.GESTOR_CHAO_FABRICA, BaseRoles.GESTOR_PRODUCAO,
                BaseRoles.POWER_USER);

        Iterable<FileFormats> formatos = Arrays.asList(FileFormats.valueOf("XML"));

        return formatos;
    }


}
