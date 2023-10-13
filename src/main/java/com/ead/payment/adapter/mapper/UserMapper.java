package com.ead.payment.adapter.mapper;

import com.ead.payment.adapter.outbound.persistence.entity.PaymentEntity;
import com.ead.payment.adapter.outbound.persistence.entity.UserEntity;
import com.ead.payment.core.domain.UserDomain;
import com.ead.payment.adapter.dto.UserEventDTO;
import com.ead.payment.adapter.outbound.persistence.entity.PaymentStatus;
import org.apache.commons.collections4.CollectionUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.time.OffsetDateTime;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.ead.payment.adapter.outbound.persistence.entity.PaymentStatus.PAYING;
import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public abstract class UserMapper {

    @Mapping(target = "paymentStatus", ignore = true)
    @Mapping(target = "paymentExpirationDate", ignore = true)
    @Mapping(target = "firstPaymentDate", ignore = true)
    @Mapping(target = "lastPaymentDate", ignore = true)
    @Mapping(target = "paymentsIds", ignore = true)
    public abstract UserDomain toDomain(final UserEventDTO event);

    @Mapping(target = "payments", expression = "java(getIdsInUserDomain(domain.paymentsIds()))")
    public abstract UserEntity toEntity(final UserDomain domain);

    protected Set<PaymentEntity> getIdsInUserDomain(final Set<UUID> ids){
        return CollectionUtils.isNotEmpty(ids) ?
                ids.stream().map(id ->{
                    var payment = new PaymentEntity();
                    payment.setId(id);
                    return payment;
                }).collect(Collectors.toSet()) :
                null;
    }

    @Mapping(target = "paymentsIds", expression = "java(getPaymentEntityIds(entity.getPayments()))")
    public abstract UserDomain toDomain(final UserEntity entity);

    protected Set<UUID> getPaymentEntityIds(final Set<PaymentEntity> entities){
        return CollectionUtils.isNotEmpty(entities)?
                entities.stream().map(PaymentEntity::getId).collect(Collectors.toSet()) :
                null;
    }

    public UserDomain toPaymentEffected(final UserDomain domain){
        var domainBuilder = domain.toBuilder();
        if (domain.isFirstPayment()){
            domainBuilder.firstPaymentDate(OffsetDateTime.now()).build();
        }
        return setPaymentInfo(domainBuilder, PAYING);
    }

    @Mapping(target = "lastPaymentDate", expression = "java(currentDateTime())")
    @Mapping(target = "paymentExpirationDate", expression = "java(currentDateTime().plusDays(30))")
    @Mapping(target = "cpf", ignore = true)
    @Mapping(target = "email", ignore = true)
    @Mapping(target = "firstPaymentDate", ignore = true)
    @Mapping(target = "fullName", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "paymentStatus", ignore = true)
    @Mapping(target = "paymentsIds", ignore = true)
    @Mapping(target = "phoneNumber", ignore = true)
    @Mapping(target = "userStatus", ignore = true)
    @Mapping(target = "userType", ignore = true)
    public abstract UserDomain setPaymentInfo(@MappingTarget final UserDomain.UserDomainBuilder domainBuilder, final PaymentStatus paymentStatus);

    protected OffsetDateTime currentDateTime(){
        return OffsetDateTime.now();
    }

}
