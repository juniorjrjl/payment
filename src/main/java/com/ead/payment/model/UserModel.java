package com.ead.payment.model;

import com.ead.payment.enumeration.PaymentStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Fetch;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.FetchType.LAZY;
import static org.hibernate.annotations.FetchMode.SUBSELECT;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table(name = "TB_USERS")
@Getter
@Setter
@ToString
public class UserModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private UUID id;
    @Column(nullable = false, unique = true, length = 50)
    private String email;
    @Column(nullable = false, length = 150)
    private String fullName;
    @Column(nullable = false)
    private String userStatus;
    @Column(nullable = false)
    private String userType;
    @Column(length = 20)
    private String cpf;
    @Column(length = 20)
    private String phoneNumber;
    @Column(nullable = false)
    @Enumerated(STRING)
    private PaymentStatus paymentStatus;
    @Column
    private OffsetDateTime paymentExpirationDate;
    @Column
    private OffsetDateTime firstPaymentDate;
    @Column
    private OffsetDateTime lastPaymentDate;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ToString.Exclude
    @OneToMany(mappedBy = "user", fetch = LAZY)
    @Fetch(SUBSELECT)
    private Set<PaymentModel> payments;

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final UserModel userModel = (UserModel) o;
        return id.equals(userModel.id) && email.equals(userModel.email) &&
                fullName.equals(userModel.fullName) && userStatus.equals(userModel.userStatus) &&
                userType.equals(userModel.userType) && Objects.equals(cpf, userModel.cpf) &&
                Objects.equals(phoneNumber, userModel.phoneNumber) && paymentStatus.equals(userModel.paymentStatus) &&
                Objects.equals(paymentExpirationDate, userModel.paymentExpirationDate) &&
                Objects.equals(firstPaymentDate, userModel.firstPaymentDate) &&
                Objects.equals(lastPaymentDate, userModel.lastPaymentDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, fullName, userStatus, userType, cpf, phoneNumber, paymentStatus,
                paymentExpirationDate, firstPaymentDate, lastPaymentDate);
    }
}
