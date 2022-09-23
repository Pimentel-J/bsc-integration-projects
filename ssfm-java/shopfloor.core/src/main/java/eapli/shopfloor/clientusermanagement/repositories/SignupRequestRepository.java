package eapli.shopfloor.clientusermanagement.repositories;

import eapli.shopfloor.clientusermanagement.domain.SignupRequest;
import eapli.framework.domain.repositories.DomainRepository;
import eapli.framework.infrastructure.authz.domain.model.Username;

/**
 *
 *Jorge Santos ajs@isep.ipp.pt 02/04/2016
 */
public interface SignupRequestRepository extends DomainRepository<Username, SignupRequest> {

    Iterable<SignupRequest> pendingSignupRequests();
}
