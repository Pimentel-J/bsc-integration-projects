package eapli.shopfloor.infrastructure.smoketests;

import eapli.framework.actions.Action;
import eapli.framework.validations.Invariants;
import eapli.shopfloor.general.CodigoAlfaCurto;
import eapli.shopfloor.general.CodigoAlfaLongo;
import eapli.shopfloor.general.Descricao;
import eapli.shopfloor.gestaoproducao.domain.CategoriaMaterial;
import eapli.shopfloor.gestaoproducao.domain.FichaTecnicaMaterial;
import eapli.shopfloor.gestaoproducao.domain.Material;
import eapli.shopfloor.gestaoproducao.repositories.MaterialRepository;
import eapli.shopfloor.infrastructure.persistence.PersistenceContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *João Pimentel 
 */
public class MaterialSmokeTester implements Action {
    private static final Logger LOGGER = LogManager.getLogger(MaterialSmokeTester.class);

    final MaterialRepository repo = PersistenceContext.repositories().materiais();

    private final FichaTecnicaMaterial f = FichaTecnicaMaterial.valueOf("f1", "f1 path");

    private final CodigoAlfaCurto codCatMat = CodigoAlfaCurto.valueOf("c1");
    private final Descricao descrCatMat = Descricao.valueOf("c1 desc");
    private final CategoriaMaterial c = new CategoriaMaterial(codCatMat, descrCatMat);

    @Override
    public boolean execute() {
        testMaterial();
        return true;
    }

    public void testMaterial() {
        // save
        repo.save(new Material(CodigoAlfaLongo.valueOf("one"), Descricao.valueOf("one desc"), f, c));
        repo.save(new Material(CodigoAlfaLongo.valueOf("two"), Descricao.valueOf("two desc"), f, c));
        LOGGER.info("»»» created material types");

        // findAll
        final Iterable<Material> matIt = repo.findAll();
        Invariants.nonNull(matIt);
        Invariants.nonNull(matIt.iterator());
        Invariants.ensure(matIt.iterator().hasNext());
        LOGGER.info("»»» find all materiais");

        // count
        final long n = repo.count();
        LOGGER.info("»»» # materiais = {}", n);

        // ofIdentity
        final Material mat1 = repo.ofIdentity(CodigoAlfaLongo.valueOf("one")).orElseThrow(IllegalStateException::new);
        final Material mat2 = repo.ofIdentity(CodigoAlfaLongo.valueOf("two")).orElseThrow(IllegalStateException::new);
        LOGGER.info("»»» found materiais of identity");

        // containsOfIdentity
        final boolean hasId = repo.containsOfIdentity(mat1.identity());
        Invariants.ensure(hasId);
        LOGGER.info("»»» contains materiais types of identity");

        // contains
        final boolean has = repo.contains(mat1);
        Invariants.ensure(has);
        LOGGER.info("»»» contains material type");

        // size
        final long n2 = repo.size();
        Invariants.ensure(n2 == n);
        LOGGER.info("»»» # dish types = {}", n2);
    }
}
