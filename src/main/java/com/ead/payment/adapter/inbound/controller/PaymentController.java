package com.ead.payment.adapter.inbound.controller;

import com.ead.payment.adapter.mapper.PaymentMapper;
import com.ead.payment.adapter.outbound.persistence.entity.PaymentEntity;
import com.ead.payment.adapter.outbound.persistence.entity.PaymentEntity_;
import com.ead.payment.core.domain.PageInfo;
import com.ead.payment.core.port.service.PaymentServicePort;
import com.ead.payment.core.port.service.query.PaymentQueryServicePort;
import com.ead.payment.adapter.dto.PaymentFilterRequest;
import com.ead.payment.adapter.dto.PaymentRequestDTO;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;
import java.util.stream.Collectors;

import static org.springframework.data.domain.Sort.Direction.DESC;
import static org.springframework.http.HttpStatus.CREATED;

@RequestMapping("/users/{userId}/payments")
@RestController
@AllArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)

public class PaymentController {

    private final PaymentServicePort paymentServicePort;
    private final PaymentQueryServicePort paymentQueryServicePort;
    private final PaymentMapper paymentMapper;

    @PreAuthorize("hasRole('USER')")
    @PostMapping
    @ResponseStatus(CREATED)
    public PaymentEntity requestPayment(@PathVariable final UUID userId, @RequestBody @Valid final PaymentRequestDTO request){
        var requestPaymentDomain = paymentMapper.toRequestPayment(request);
        var domain = paymentServicePort.requestPayment(requestPaymentDomain, userId);
        return paymentMapper.toEntity(domain);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping
    public Page<PaymentEntity> getAll(@PathVariable final UUID userId,
                                      final PaymentFilterRequest filter,
                                      @PageableDefault(sort = PaymentEntity_.ID, direction = DESC) final Pageable pageable){
        var filterDomain = paymentMapper.toFilterDomain(filter, userId);
        var payments = paymentQueryServicePort.findAllByUser(filterDomain, new PageInfo(pageable.getPageNumber(), pageable.getPageSize()));
        return new PageImpl<>(payments.stream().map(paymentMapper::toEntity).collect(Collectors.toList()), pageable, payments.size());
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/{paymentId}")
    public PaymentEntity findOne(@PathVariable final UUID userId, @PathVariable final UUID paymentId){
        var domain = paymentQueryServicePort.findLastPaymentByUser(userId, paymentId);
        return paymentMapper.toEntity(domain);
    }

}
