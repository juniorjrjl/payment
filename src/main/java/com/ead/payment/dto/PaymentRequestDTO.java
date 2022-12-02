package com.ead.payment.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

public record PaymentRequestDTO(@NotNull
                                @DecimalMin(value = "0.0", inclusive = false)
                                @Digits(integer = 5, fraction = 2)
                                @JsonProperty("valuePaid")
                                BigDecimal valuePaid,
                                @NotBlank
                                @JsonProperty("holderFullName")
                                String holderFullName,
                                @NotBlank
                                @CPF
                                @JsonProperty("holderCpf")
                                String holderCpf,
                                @NotBlank
                                @Size(min = 16, max = 20)
                                @JsonProperty("creditCardNumber")
                                String creditCardNumber,
                                @NotBlank
                                @Size(min = 4, max = 10)
                                @JsonProperty("expirationDate")
                                String expirationDate,
                                @NotBlank
                                @Size(min = 3, max = 3)
                                @JsonProperty("cvvCode")
                                String cvvCode) {

    @Builder(toBuilder = true)
    public PaymentRequestDTO{

    }

}
