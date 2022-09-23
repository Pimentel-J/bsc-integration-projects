package eapli.shopfloor.persistence.impl.jpa;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import eapli.shopfloor.general.CodigoAlfaCurto;
import eapli.shopfloor.gestaochaofabrica.domain.Deposito;
import eapli.shopfloor.gestaochaofabrica.repositories.DepositoRepository;

public class JpaDepositoRepository extends BaseJpaRepositoryBase<Deposito, CodigoAlfaCurto, CodigoAlfaCurto>
        implements DepositoRepository {

    public JpaDepositoRepository() {
        super("codigo");
    }

	@Override
	public Deposito containsDeposito(CodigoAlfaCurto codDeposito) {
		try{
	        final TypedQuery<Deposito> q = createQuery(
	            "SELECT d FROM Deposito d WHERE d.codigo = :codDeposito", Deposito.class);
	        q.setParameter("codDeposito", codDeposito);
	        return q.getSingleResult();
	        } catch(NoResultException nre){
	            return null;
	        }
	}
}
