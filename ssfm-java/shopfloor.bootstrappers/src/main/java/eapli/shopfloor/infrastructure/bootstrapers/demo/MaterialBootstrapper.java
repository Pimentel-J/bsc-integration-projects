package eapli.shopfloor.infrastructure.bootstrapers.demo;

import eapli.framework.actions.Action;
import eapli.framework.domain.repositories.ConcurrencyException;
import eapli.framework.domain.repositories.IntegrityViolationException;
import eapli.shopfloor.general.CodigoAlfaCurto;
import eapli.shopfloor.gestaoproducao.application.RegisterMaterialController;
import eapli.shopfloor.gestaoproducao.domain.CategoriaMaterial;
import eapli.shopfloor.gestaoproducao.domain.FichaTecnicaMaterial;
import eapli.shopfloor.gestaoproducao.repositories.CategoriaMaterialRepository;
import eapli.shopfloor.infrastructure.persistence.PersistenceContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static eapli.shopfloor.infrastructure.bootstrapers.TestDataConstants.*;

/**
 *João Pimentel 
 */
public class MaterialBootstrapper implements Action {
    private static final Logger LOGGER = LogManager.getLogger(MaterialBootstrapper.class);

    private final CategoriaMaterialRepository catMatRepo = PersistenceContext.repositories().categoriasMaterial();

    private CategoriaMaterial getCategoriaMaterial(final String codigo) {
        return catMatRepo.ofIdentity(CodigoAlfaCurto.valueOf(codigo)) .orElseThrow(IllegalStateException::new);
    }

    @Override
    public boolean execute() {
        register(MAT_CODIGO_GRANITO, MAT_DESCRICAO_GRANITO, FICHA_GRANITO,
                getCategoriaMaterial(CATEGORIA_CODIGO_PEDRA));
        register(MAT_CODIGO_CARVALHO, MAT_DESCRICAO_CARVALHO, FICHA_CARVALHO,
                getCategoriaMaterial(CATEGORIA_CODIGO_MADEIRA));
        register(MAT_CODIGO_COBRE, MAT_DESCRICAO_COBRE, FICHA_COBRE,
                getCategoriaMaterial(CATEGORIA_CODIGO_COBRE));
        return true;
    }

    private void register(final String codigo, final String descricao,
                          final FichaTecnicaMaterial ficha, CategoriaMaterial categoria) {
        final RegisterMaterialController controlador = new RegisterMaterialController();

        try{
            controlador.registerMaterial(codigo, descricao, ficha.nome(),
                    ficha.path(), categoria);
        }catch (final IntegrityViolationException | ConcurrencyException ex){
            LOGGER.warn("Assuming {} already exists (activate trace log for details)", codigo);
            LOGGER.trace("Assuming existing record", ex);
        }
    }

}
