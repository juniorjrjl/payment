package com.ead.payment.service.impl;

import com.ead.payment.model.UserModel;
import com.ead.payment.repository.UserRepository;
import com.ead.payment.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserModel save(final UserModel model) {
        return userRepository.save(model);
    }

    @Transactional
    @Override
    public void delete(final UUID id) {
        userRepository.deleteById(id);
    }

    @Override
    public Optional<UserModel> findById(final UUID id) {
        return userRepository.findById(id);
    }

}
