package eapli.shopfloor.gestaochaofabrica.repositories;

import eapli.framework.domain.repositories.DomainRepository;
import eapli.shopfloor.general.CodigoAlfaCurto;
import eapli.shopfloor.gestaochaofabrica.domain.Deposito;

public interface DepositoRepository extends DomainRepository<CodigoAlfaCurto, Deposito> {

	Deposito containsDeposito(CodigoAlfaCurto codDeposito);
}
