package com.ead.payment.adapter.outbound.persistence.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Objects;
import java.util.UUID;

import static com.ead.payment.adapter.outbound.persistence.entity.PaymentControl.EFFECTED;
import static com.ead.payment.adapter.outbound.persistence.entity.PaymentControl.REQUESTED;
import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.AUTO;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table(name = "TB_PAYMENTS")
@Getter
@Setter
@ToString
public class PaymentEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = AUTO)
    private UUID id;
    @Column(nullable = false)
    @Enumerated(STRING)
    private PaymentControl control;
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
        return control.equals(REQUESTED);
    }

    public boolean isEffected(){
        return control.equals(EFFECTED) && expirationDate.isAfter(OffsetDateTime.now());
    }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ToString.Exclude
    @ManyToOne(optional = false, fetch = LAZY)
    private UserEntity user;

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final PaymentEntity that = (PaymentEntity) o;
        return recurrence == that.recurrence && id.equals(that.id) && control == that.control &&
                requestDate.equals(that.requestDate) && Objects.equals(completionDate, that.completionDate) &&
                expirationDate.equals(that.expirationDate) && lastDigitCreditCard.equals(that.lastDigitCreditCard) &&
                valuePaid.equals(that.valuePaid) && message.equals(that.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, control, requestDate, completionDate, expirationDate, lastDigitCreditCard,
                valuePaid, message, recurrence);
    }
}
