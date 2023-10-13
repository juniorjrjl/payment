package com.ead.payment.adapter.outbound.publisher;

import com.ead.payment.adapter.mapper.PaymentMapper;
import com.ead.payment.core.domain.PaymentCommandDomain;
import com.ead.payment.core.port.publisher.PaymentCommandPublisherPort;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class PaymentCommandPublisherPortImpl implements PaymentCommandPublisherPort {

    private final RabbitTemplate rabbitTemplate;

    private final PaymentMapper paymentMapper;

    private final String paymentCommandExchange;

    private final String paymentCommandKey;

    public PaymentCommandPublisherPortImpl(final RabbitTemplate rabbitTemplate,
                                           final PaymentMapper paymentMapper,
                                           @Value("${ead.broker.exchange.payment-command-exchange.name}")
                                           final String paymentCommandExchange,
                                           @Value("${ead.broker.key.payment-command-key}")
                                           final String paymentCommandKey) {
        this.rabbitTemplate = rabbitTemplate;
        this.paymentMapper = paymentMapper;
        this.paymentCommandExchange = paymentCommandExchange;
        this.paymentCommandKey = paymentCommandKey;
    }

    @Transactional
    @Override
    public void publishPaymentCommend(final PaymentCommandDomain domain){
        rabbitTemplate.convertAndSend(paymentCommandExchange, paymentCommandKey, paymentMapper.toCommandDTO(domain));
    }

}
