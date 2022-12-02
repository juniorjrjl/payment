package com.ead.payment.mapper;

import com.ead.payment.dto.PaymentRequestDTO;
import com.ead.payment.dto.UserEventDTO;
import com.ead.payment.model.CreditCardModel;
import com.ead.payment.model.UserModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface CreditCardMapper {

    @Mapping(target = "id", ignore = true)
    CreditCardModel toModel(final PaymentRequestDTO request, final UserModel user);

}
