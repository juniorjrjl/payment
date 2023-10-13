package com.ead.payment.core.port.service.query;

import com.ead.payment.core.domain.UserDomain;

import java.util.UUID;

public interface UserQueryServicePort {

    UserDomain findById(final UUID id);

}
