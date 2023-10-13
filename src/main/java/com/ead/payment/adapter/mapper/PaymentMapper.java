package com.ead.payment.adapter.mapper;

import com.ead.payment.adapter.dto.PaymentCommandDTO;
import com.ead.payment.adapter.dto.PaymentEventDTO;
import com.ead.payment.adapter.dto.PaymentFilterDTO;
import com.ead.payment.adapter.dto.PaymentFilterRequest;
import com.ead.payment.adapter.dto.PaymentRequestDTO;
import com.ead.payment.adapter.outbound.persistence.entity.PaymentControl;
import com.ead.payment.adapter.outbound.persistence.entity.PaymentEntity;
import com.ead.payment.core.domain.PaymentCommandDomain;
import com.ead.payment.core.domain.PaymentDomain;
import com.ead.payment.core.domain.PaymentEventDomain;
import com.ead.payment.core.domain.PaymentFilterDomain;
import com.ead.payment.core.domain.PaymentRequestPaymentDomain;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.OffsetDateTime;
import java.util.UUID;

import static com.ead.payment.adapter.outbound.persistence.entity.PaymentControl.REQUESTED;
import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public abstract class PaymentMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "control", expression = "java(getRequested())")
    @Mapping(target = "requestDate", expression = "java(getCurrentDateTime())")
    @Mapping(target = "expirationDate", expression = "java(getCurrentDateTime().plusDays(30))")
    @Mapping(target = "lastDigitCreditCard", expression = "java(request.creditCardNumber().substring(request.creditCardNumber().length() -4))")
    @Mapping(target = "message", ignore = true)
    @Mapping(target = "completionDate", ignore = true)
    @Mapping(target = "recurrence", ignore = true)
    public abstract PaymentDomain toDomain(final PaymentRequestPaymentDomain request, final UUID userId);

    protected PaymentControl getRequested(){
        return REQUESTED;
    }


    protected OffsetDateTime getCurrentDateTime(){
        return OffsetDateTime.now();
    }

    public abstract PaymentCommandDomain toCommand(final UUID userId, final UUID paymentId, final UUID cardId);

    @Mapping(target = "control", source = "domain.control")
    public abstract PaymentEventDomain toEvent(final PaymentDomain domain, final UUID userId);

    public abstract PaymentDomain toDomain(final PaymentEntity entity);

    @Mapping(target = "user", ignore = true)
    public abstract PaymentEntity toEntity(final PaymentDomain domain);

    public abstract PaymentFilterDTO toFilterDTO(final PaymentFilterDomain filterDomain);

    public abstract PaymentRequestPaymentDomain toRequestPayment(final PaymentRequestDTO request);

    public abstract PaymentFilterDomain toFilterDomain(final PaymentFilterRequest filterRequest, final UUID userId);

    public abstract PaymentCommandDTO toCommandDTO(final PaymentCommandDomain domain);

    public abstract PaymentEventDTO toEvent(final PaymentEventDomain domain);

}
