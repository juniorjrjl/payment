package com.ead.payment.service;

import com.ead.payment.model.UserModel;

import java.util.Optional;
import java.util.UUID;

public interface UserService {


    UserModel save(final UserModel model);

    void delete(final UUID id);

    Optional<UserModel> findById(final UUID id);
}
