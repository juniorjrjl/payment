package com.ead.payment.zzzz.mapper;

import com.ead.payment.zzzz.dto.PaymentRequestDTO;
import com.ead.payment.zzzz.model.CreditCardModel;
import com.ead.payment.zzzz.model.UserModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface CreditCardMapper {

    @Mapping(target = "id", ignore = true)
    CreditCardModel toModel(final PaymentRequestDTO request, final UserModel user);

}
