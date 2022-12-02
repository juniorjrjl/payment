package com.ead.payment.mapper;

import com.ead.payment.dto.UserEventDTO;
import com.ead.payment.model.UserModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface UserMapper {

    @Mapping(target = "paymentStatus", ignore = true)
    @Mapping(target = "paymentExpirationDate", ignore = true)
    @Mapping(target = "firstPaymentDate", ignore = true)
    @Mapping(target = "lastPaymentDate", ignore = true)
    @Mapping(target = "payments", ignore = true)
    UserModel toModel(final UserEventDTO event);

}
