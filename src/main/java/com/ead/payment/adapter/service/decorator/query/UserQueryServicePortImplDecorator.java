package com.ead.payment.adapter.service.decorator.query;

import com.ead.payment.core.domain.UserDomain;
import com.ead.payment.core.port.service.query.UserQueryServicePort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@AllArgsConstructor
public class UserQueryServicePortImplDecorator implements UserQueryServicePort {

    private final UserQueryServicePort userQueryServicePort;

    @Transactional
    @Override
    public UserDomain findById(final UUID id) {
        return userQueryServicePort.findById(id);
    }

}
