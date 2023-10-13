package com.ead.payment.core.service.query;

import com.ead.payment.core.domain.UserDomain;
import com.ead.payment.core.exception.DomainNotFoundException;
import com.ead.payment.core.port.persistence.UserPersistencePort;
import com.ead.payment.core.port.service.query.UserQueryServicePort;
import lombok.AllArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
public class UserQueryServicePortImpl implements UserQueryServicePort {

    private final UserPersistencePort userPersistencePort;

    @Override
    public UserDomain findById(final UUID id) {
        return userPersistencePort.findById(id).orElseThrow(() -> new DomainNotFoundException("User not found"));
    }

}
