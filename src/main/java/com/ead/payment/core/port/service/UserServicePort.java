package com.ead.payment.core.port.service;

import com.ead.payment.core.domain.UserDomain;

import java.util.UUID;

public interface UserServicePort {

    UserDomain save(final UserDomain domain);

    UserDomain update(final UserDomain domain);

    void delete(final UUID id);

}
