package com.ead.payment.mapper;

import com.ead.payment.dto.UserEventDTO;
import com.ead.payment.model.UserModel;
import org.mapstruct.Mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface UserMapper {

    UserModel toModel(final UserEventDTO event);

}
