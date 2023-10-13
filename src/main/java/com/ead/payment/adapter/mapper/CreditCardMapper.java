package com.ead.payment.adapter.mapper;

import com.ead.payment.adapter.outbound.persistence.entity.CreditCardEntity;
import com.ead.payment.adapter.outbound.persistence.entity.UserEntity;
import com.ead.payment.core.domain.CreditCardDomain;
import com.ead.payment.core.domain.PaymentRequestPaymentDomain;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.UUID;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public abstract class CreditCardMapper {

    @Mapping(target = "id", ignore = true)
    public abstract CreditCardDomain toDomain(final PaymentRequestPaymentDomain request, final UUID userId);

    @Mapping(target = "userId", source = "user.id")
    public abstract CreditCardDomain toDomain(final CreditCardEntity entity);

    @Mapping(target = "user", expression = "java(toUserEntity(domain.userId()))")
    public abstract CreditCardEntity toDomain(final CreditCardDomain domain);

    @Mapping(target = "cpf", ignore = true)
    @Mapping(target = "email", ignore = true)
    @Mapping(target = "firstPaymentDate", ignore = true)
    @Mapping(target = "fullName", ignore = true)
    @Mapping(target = "lastPaymentDate", ignore = true)
    @Mapping(target = "paymentExpirationDate", ignore = true)
    @Mapping(target = "paymentStatus", ignore = true)
    @Mapping(target = "payments", ignore = true)
    @Mapping(target = "phoneNumber", ignore = true)
    @Mapping(target = "userStatus", ignore = true)
    @Mapping(target = "userType", ignore = true)
    protected abstract UserEntity toUserEntity(final UUID id);

}
