package com.ead.payment.mapper;

import com.ead.payment.dto.PaymentRequestDTO;
import com.ead.payment.enumeration.PaymentControl;
import com.ead.payment.model.PaymentModel;
import com.ead.payment.model.UserModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.OffsetDateTime;

import static com.ead.payment.enumeration.PaymentControl.REQUESTED;
import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface PaymentMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "paymentControl", expression = "java(getRequested())")
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

}
