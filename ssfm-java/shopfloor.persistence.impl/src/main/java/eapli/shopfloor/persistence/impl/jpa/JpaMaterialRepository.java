package eapli.shopfloor.persistence.impl.jpa;

import eapli.shopfloor.general.CodigoAlfaLongo;
import eapli.shopfloor.gestaoproducao.domain.Material;
import eapli.shopfloor.gestaoproducao.repositories.MaterialRepository;

/**
 *  T - the entity type that we want to build a repository for
 *  K - the primary key type of the table
 *  I - the type of the business identity of the entity
 *
 *João Pimentel 
 */
public class JpaMaterialRepository extends BaseJpaRepositoryBase<Material, Long, CodigoAlfaLongo> implements MaterialRepository {

    JpaMaterialRepository() { super("codigoMaterial"); }

}
