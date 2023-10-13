package com.ead.payment.adapter.outbound.publisher;

import com.ead.payment.adapter.mapper.PaymentMapper;
import com.ead.payment.core.domain.PaymentEventDomain;
import com.ead.payment.core.port.publisher.PaymentEventPublisherPort;
import com.ead.payment.adapter.dto.PaymentEventDTO;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class PaymentEventPublisherPortImpl implements PaymentEventPublisherPort {

    private final RabbitTemplate rabbitTemplate;

    private final PaymentMapper paymentMapper;

    private final String exchangePaymentEvent;

    public PaymentEventPublisherPortImpl(final RabbitTemplate rabbitTemplate,
                                         final PaymentMapper paymentMapper,
                                         @Value("${ead.broker.exchange.payment-event-exchange.name}")
                                         final String exchangePaymentEvent) {
        this.rabbitTemplate = rabbitTemplate;
        this.paymentMapper = paymentMapper;
        this.exchangePaymentEvent = exchangePaymentEvent;
    }

    public void publishPaymentEvent(final PaymentEventDomain domain){
        rabbitTemplate.convertAndSend(exchangePaymentEvent, "", paymentMapper.toEvent(domain));
    }
}
