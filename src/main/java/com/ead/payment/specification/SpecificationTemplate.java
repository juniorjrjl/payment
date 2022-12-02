package com.ead.payment.specification;

import com.ead.payment.model.PaymentModel;
import com.ead.payment.model.PaymentModel_;
import com.ead.payment.model.UserModel;
import com.ead.payment.model.UserModel_;
import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.data.jpa.domain.Specification;

import java.util.UUID;

public class SpecificationTemplate {

    @And({
            @Spec(path = PaymentModel_.PAYMENT_CONTROL, spec = Equal.class),
            @Spec(path = PaymentModel_.VALUE_PAID, spec = Equal.class),
            @Spec(path = PaymentModel_.LAST_DIGIT_CREDIT_CARD, spec = Equal.class),
            @Spec(path = PaymentModel_.MESSAGE, spec = Equal.class),
    })
    public interface PaymentSpec extends Specification<PaymentModel> { }

    public static Specification<PaymentModel> paymentUserId(final UUID userId){
        return (root, query, criteriaBuilder) -> {
            query.distinct(true);
            var user = query.from(UserModel.class);
            var userPayments = user.get(UserModel_.payments);
            return criteriaBuilder.and(criteriaBuilder.equal(user.get(UserModel_.id), userId), criteriaBuilder.isMember(root, userPayments));
        };
    }

}
