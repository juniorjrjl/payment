package com.ead.payment.adapter.inbound.consumer;

import com.ead.payment.adapter.dto.ActionType;
import com.ead.payment.adapter.dto.UserEventDTO;
import com.ead.payment.adapter.mapper.UserMapper;
import com.ead.payment.adapter.outbound.persistence.entity.PaymentStatus;
import com.ead.payment.core.port.service.UserServicePort;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import static org.springframework.amqp.core.ExchangeTypes.FANOUT;

@AllArgsConstructor
@Component
public class UserConsumer {

    private final UserServicePort userServicePort;
    private final UserMapper userMapper;

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "${ead.broker.queue.user-event-queue.name}", durable = "true"),
            exchange = @Exchange(value = "${ead.broker.exchange.user-event-exchange.name}", type = FANOUT, ignoreDeclarationExceptions = "true")))
    public void listenUserEvent(@Payload final UserEventDTO event){
        switch (ActionType.valueOf(event.actionType())){
            case CREATE -> {
                var model = userMapper.toDomain(event);
                userServicePort.save(model.toBuilder().paymentStatus(PaymentStatus.NOT_STARTED).build());
            }
            case UPDATE -> {
                var model = userMapper.toDomain(event);
                userServicePort.update(model);
            }
            case DELETE -> userServicePort.delete(event.id());
        }
    }
}
