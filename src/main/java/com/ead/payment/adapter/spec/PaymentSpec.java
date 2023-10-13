package com.ead.payment.adapter.spec;

import com.ead.payment.adapter.dto.PaymentFilterDTO;
import com.ead.payment.adapter.outbound.persistence.entity.PaymentEntity;
import com.ead.payment.adapter.outbound.persistence.entity.PaymentEntity_;
import com.ead.payment.adapter.outbound.persistence.entity.UserEntity;
import com.ead.payment.adapter.outbound.persistence.entity.UserEntity_;
import jakarta.persistence.criteria.Predicate;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PaymentSpec {

    public static Specification<PaymentEntity> findAllByUserFilter(final PaymentFilterDTO filter){
        return (root, query, criteriaBuilder) ->{
            List<Predicate> predicates = new ArrayList<>();
            if (Objects.nonNull(filter.control())){
                predicates.add(criteriaBuilder.equal(root.get(PaymentEntity_.control), filter.control()));
            }
            if (Objects.nonNull(filter.valuePaid())){
                predicates.add(criteriaBuilder.equal(root.get(PaymentEntity_.valuePaid), filter.valuePaid()));
            }
            if (StringUtils.isNotBlank(filter.lastDigitCreditCard())){
                predicates.add(criteriaBuilder.equal(root.get(PaymentEntity_.lastDigitCreditCard), filter.lastDigitCreditCard()));
            }
            if (StringUtils.isNotBlank(filter.message())){
                predicates.add(criteriaBuilder.equal(root.get(PaymentEntity_.message), filter.message()));
            }
            if (Objects.nonNull(filter.userId())){
                query.distinct(true);
                var user = query.from(UserEntity.class);
                var userPayments = user.get(UserEntity_.payments);
                predicates.add(criteriaBuilder.and(criteriaBuilder.equal(user.get(UserEntity_.id), filter.userId()), criteriaBuilder.isMember(root, userPayments)));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

}
