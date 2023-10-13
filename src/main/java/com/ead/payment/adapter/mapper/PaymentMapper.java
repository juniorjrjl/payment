package com.ead.payment.zzzz.mapper;

import com.ead.payment.zzzz.dto.PaymentCommandDTO;
import com.ead.payment.zzzz.dto.PaymentEventDTO;
import com.ead.payment.zzzz.dto.PaymentRequestDTO;
import com.ead.payment.zzzz.enumeration.PaymentControl;
import com.ead.payment.zzzz.model.PaymentModel;
import com.ead.payment.zzzz.model.UserModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.OffsetDateTime;
import java.util.UUID;

import static com.ead.payment.zzzz.enumeration.PaymentControl.REQUESTED;
import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface PaymentMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "control", expression = "java(getRequested())")
    @Mapping(target = "requestDate", expression = "java(getCurrentDateTime())")
    @Mapping(target = "expirationDate", expression = "java(getCurrentDateTime().plusDays(30))")
    @Mapping(target = "lastDigitCreditCard", expression = "java(request.creditCardNumber().substring(request.creditCardNumber().length() -4))")
    @Mapping(target = "message", ignore = true)
    @Mapping(target = "completionDate", ignore = true)
    @Mapping(target = "recurrence", ignore = true)
    PaymentModel toModel(final PaymentRequestDTO request, final UserModel user);

    default PaymentControl getRequested(){
        return REQUESTED;
    }


    default OffsetDateTime getCurrentDateTime(){
        return OffsetDateTime.now();
    }

    PaymentCommandDTO toCommand(final UUID userId, final UUID paymentId, final UUID cardId);

    @Mapping(target = "control", source = "control")
    @Mapping(target = "userId", source = "user.id")
    PaymentEventDTO toEvent(final PaymentModel model);

}
