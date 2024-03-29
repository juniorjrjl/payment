package com.ead.payment.adapter.outbound.persistence.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Fetch;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.FetchType.LAZY;
import static org.hibernate.annotations.FetchMode.SUBSELECT;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table(name = "TB_USERS")
@Getter
@Setter
@ToString
public class UserEntity implements Serializable {

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
    private Set<PaymentEntity> payments;

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final UserEntity userEntity = (UserEntity) o;
        return id.equals(userEntity.id) && email.equals(userEntity.email) &&
                fullName.equals(userEntity.fullName) && userStatus.equals(userEntity.userStatus) &&
                userType.equals(userEntity.userType) && Objects.equals(cpf, userEntity.cpf) &&
                Objects.equals(phoneNumber, userEntity.phoneNumber) && paymentStatus.equals(userEntity.paymentStatus) &&
                Objects.equals(paymentExpirationDate, userEntity.paymentExpirationDate) &&
                Objects.equals(firstPaymentDate, userEntity.firstPaymentDate) &&
                Objects.equals(lastPaymentDate, userEntity.lastPaymentDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, fullName, userStatus, userType, cpf, phoneNumber, paymentStatus,
                paymentExpirationDate, firstPaymentDate, lastPaymentDate);
    }
}
