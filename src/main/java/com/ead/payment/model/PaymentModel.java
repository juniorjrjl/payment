package com.ead.payment.model;

import com.ead.payment.enumeration.PaymentControl;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Objects;
import java.util.UUID;

import static com.ead.payment.enumeration.PaymentControl.EFFECTED;
import static com.ead.payment.enumeration.PaymentControl.REQUESTED;
import static javax.persistence.EnumType.STRING;
import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.AUTO;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table(name = "TB_PAYMENTS")
@Getter
@Setter
@ToString
public class PaymentModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = AUTO)
    private UUID id;
    @Column(nullable = false)
    @Enumerated(STRING)
    private PaymentControl paymentControl;
    @Column(nullable = false)
    private OffsetDateTime requestDate;
    @Column
    private OffsetDateTime completionDate;
    @Column(nullable = false)
    private OffsetDateTime expirationDate;
    @Column(nullable = false, length = 4)
    private String lastDigitCreditCard;
    @Column(nullable = false)
    private BigDecimal valuePaid;
    @Column(length = 150)
    private String message;
    @Column(nullable = false)
    private boolean recurrence;

    public boolean isRequested(){
        return paymentControl.equals(REQUESTED);
    }

    public boolean isEffected(){
        return paymentControl.equals(EFFECTED) && expirationDate.isAfter(OffsetDateTime.now());
    }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ToString.Exclude
    @ManyToOne(optional = false, fetch = LAZY)
    private UserModel user;

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final PaymentModel that = (PaymentModel) o;
        return recurrence == that.recurrence && id.equals(that.id) && paymentControl == that.paymentControl &&
                requestDate.equals(that.requestDate) && Objects.equals(completionDate, that.completionDate) &&
                expirationDate.equals(that.expirationDate) && lastDigitCreditCard.equals(that.lastDigitCreditCard) &&
                valuePaid.equals(that.valuePaid) && message.equals(that.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, paymentControl, requestDate, completionDate, expirationDate, lastDigitCreditCard,
                valuePaid, message, recurrence);
    }
}
