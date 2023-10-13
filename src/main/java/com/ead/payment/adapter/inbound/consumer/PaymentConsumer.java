package com.ead.payment.adapter.inbound.consumer;

import com.ead.payment.adapter.dto.PaymentCommandDTO;
import com.ead.payment.core.port.service.PaymentServicePort;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import static org.springframework.amqp.core.ExchangeTypes.TOPIC;
@AllArgsConstructor
@Component
public class PaymentConsumer {

    private final PaymentServicePort paymentServicePort;

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "${ead.broker.queue.payment-command-exchange.name}", durable = "true"),
            exchange = @Exchange(value = "${ead.broker.exchange.payment-command-exchange.name}", type = TOPIC),
            key = "${ead.broker.key.payment-command-key}"
    ))
    public void listenPaymentCommand(@Payload final PaymentCommandDTO dto){
        paymentServicePort.makePayment(dto);
    }

}
