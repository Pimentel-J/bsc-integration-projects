package eapli.shopfloor.persistence.impl.jpa;

import eapli.shopfloor.general.CodigoAlfaCurto;
import eapli.shopfloor.gestaoproducao.domain.CategoriaMaterial;
import eapli.shopfloor.gestaoproducao.repositories.CategoriaMaterialRepository;

/**
 *  T - the entity type that we want to build a repository for
 *  K - the primary key type of the table
 *  I - the type of the business identity of the entity
 *
 *João Pimentel 
 */
public class JpaCategoriaMaterialRepository extends BaseJpaRepositoryBase<CategoriaMaterial, CodigoAlfaCurto,
        CodigoAlfaCurto> implements CategoriaMaterialRepository {

    JpaCategoriaMaterialRepository() { super("codigoCategoriaMaterial"); }


}
