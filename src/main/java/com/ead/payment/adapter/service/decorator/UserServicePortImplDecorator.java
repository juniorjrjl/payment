package com.ead.payment.adapter.service.decorator;

import com.ead.payment.core.domain.UserDomain;
import com.ead.payment.core.port.service.UserServicePort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@AllArgsConstructor
public class UserServicePortImplDecorator implements UserServicePort {

    private final UserServicePort userServicePort;

    @Transactional
    @Override
    public UserDomain save(final UserDomain domain) {
        return userServicePort.save(domain);
    }

    @Transactional
    @Override
    public UserDomain update(final UserDomain domain) {
        return userServicePort.update(domain);
    }

    @Transactional
    @Override
    public void delete(final UUID id) {
        userServicePort.delete(id);
    }

}
