package com.ead.payment.service;

import com.ead.payment.model.UserModel;

import java.util.UUID;

public interface CreditCardService {


    UserModel save(final UserModel model);

    void delete(final UUID id);
}
