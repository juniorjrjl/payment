package com.ead.payment.controller;

import com.ead.payment.dto.PaymentRequestDTO;
import com.ead.payment.model.PaymentModel;
import com.ead.payment.model.PaymentModel_;
import com.ead.payment.service.PaymentService;
import com.ead.payment.service.UserService;
import com.ead.payment.specification.SpecificationTemplate;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.UUID;

import static org.springframework.data.domain.Sort.Direction.DESC;
import static org.springframework.http.HttpStatus.ACCEPTED;
import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;

@RequestMapping("/users/{userId}/payments")
@RestController
@AllArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)

public class PaymentController {

    private final PaymentService paymentService;
    private final UserService userService;

    @PreAuthorize("hasRole('USER')")
    @PostMapping
    public ResponseEntity<Object> requestPayment(@PathVariable final UUID userId, @RequestBody @Valid final PaymentRequestDTO request){
        var userOptional = userService.findById(userId);
        if(userOptional.isEmpty()){
            return ResponseEntity.status(NOT_FOUND).body("User not found.");
        }
        var user = userOptional.get();
        var paymentOptional = paymentService.findLastPaymentByUser(user);
        if(paymentOptional.isPresent()){
            var payment = paymentOptional.get();
            if (payment.isRequested()){
                return ResponseEntity.status(CONFLICT).body("Payment already requested.");
            }
            if (payment.isEffected()){
                return ResponseEntity.status(CONFLICT).body("Payment already made.");
            }
        }
        return ResponseEntity.status(ACCEPTED).body(paymentService.requestPayment(request, userOptional.get()));
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping
    public ResponseEntity<Page<PaymentModel>> getAll(@PathVariable final UUID userId,
                                                     final SpecificationTemplate.PaymentSpec spec,
                                                     @PageableDefault(sort = PaymentModel_.ID, direction = DESC) final Pageable pageable){
        return ResponseEntity.status(OK).body(paymentService.findAllByUser(SpecificationTemplate.paymentUserId(userId).and(spec), pageable));

    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/{paymentId}")
    public ResponseEntity<Object> findOne(@PathVariable final UUID userId, @PathVariable final UUID paymentId){
        var paymentOptional = paymentService.findLastPaymentByUser(userId, paymentId);
        if (paymentOptional.isEmpty()){
            return ResponseEntity.status(NOT_FOUND).body("Payment not found for this user");
        }
        return ResponseEntity.status(OK).body(paymentOptional.get());
    }

}
