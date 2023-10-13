package com.ead.payment.zzzz.consumer;

import com.ead.payment.zzzz.dto.UserEventDTO;
import com.ead.payment.zzzz.enumeration.ActionType;
import com.ead.payment.zzzz.enumeration.PaymentStatus;
import com.ead.payment.zzzz.mapper.UserMapper;
import com.ead.payment.zzzz.service.UserService;
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

    private final UserService userService;
    private final UserMapper userMapper;

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "${ead.broker.queue.user-event-queue.name}", durable = "true"),
            exchange = @Exchange(value = "${ead.broker.exchange.user-event-exchange.name}", type = FANOUT, ignoreDeclarationExceptions = "true")))
    public void listenUserEvent(@Payload final UserEventDTO event){
        switch (ActionType.valueOf(event.actionType())){
            case CREATE -> {
                var model = userMapper.toModel(event);
                model.setPaymentStatus(PaymentStatus.NOT_STARTED);
                userService.save(model);
            }
            case UPDATE -> {
                var model = userMapper.toModel(event);
                userService.save(model);
            }
            case DELETE -> userService.delete(event.id());
        }
    }
}
