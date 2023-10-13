package com.ead.payment.zzzz.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

import static jakarta.persistence.GenerationType.AUTO;


@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table(name = "TB_CREDIT_CARDS")
@Getter
@Setter
@ToString
public class CreditCardModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = AUTO)
    private UUID id;
    @Column(nullable = false, length = 150)
    private String holderFullName;
    @Column(nullable = false, length = 20)
    private String holderCpf;
    @Column(nullable = false, length = 20)
    private String creditCardNumber;
    @Column(nullable = false, length = 10)
    private String expirationDate;
    @Column(nullable = false, length = 3)
    private String cvvCode;
    @OneToOne
    @JoinColumn(unique = true, nullable = false)
    @ToString.Exclude
    private UserModel user;

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final CreditCardModel that = (CreditCardModel) o;
        return id.equals(that.id) && holderFullName.equals(that.holderFullName) && holderCpf.equals(that.holderCpf) &&
                creditCardNumber.equals(that.creditCardNumber) && expirationDate.equals(that.expirationDate) &&
                cvvCode.equals(that.cvvCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, holderFullName, holderCpf, creditCardNumber, expirationDate, cvvCode);
    }
}
