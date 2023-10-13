package com.ead.payment.core.service;

import com.ead.payment.core.domain.UserDomain;
import com.ead.payment.core.port.persistence.UserPersistencePort;
import com.ead.payment.core.port.service.UserServicePort;
import lombok.AllArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
public class UserServicePortImpl implements UserServicePort {

    private final UserPersistencePort userPersistencePort;

    @Override
    public UserDomain save(final UserDomain domain) {
        return userPersistencePort.save(domain);
    }

    @Override
    public UserDomain update(final UserDomain domain) {
        userPersistencePort.update(domain);
        return domain;
    }

    @Override
    public void delete(final UUID id) {
        userPersistencePort.delete(id);
    }

}
