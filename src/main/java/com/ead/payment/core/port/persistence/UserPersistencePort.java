package com.ead.payment.core.port.persistence;

import com.ead.payment.core.domain.UserDomain;

import java.util.Optional;
import java.util.UUID;

public interface UserPersistencePort {

    UserDomain save(final UserDomain domain);

    void update(final UserDomain domain);

    void delete(final UUID id);

    Optional<UserDomain> findById(final UUID id);

}
