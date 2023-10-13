package com.ead.payment.adapter.config;

import com.ead.payment.PaymentApplication;
import com.ead.payment.adapter.mapper.CreditCardMapper;
import com.ead.payment.adapter.mapper.PaymentMapper;
import com.ead.payment.adapter.mapper.UserMapper;
import com.ead.payment.adapter.outbound.publisher.PaymentCommandPublisherPortImpl;
import com.ead.payment.adapter.outbound.publisher.PaymentEventPublisherPortImpl;
import com.ead.payment.adapter.service.decorator.CreditCardServicePortImplDecorator;
import com.ead.payment.adapter.service.decorator.PaymentServicePortImplDecorator;
import com.ead.payment.adapter.service.decorator.UserServicePortImplDecorator;
import com.ead.payment.adapter.service.decorator.query.CreditCardQueryServicePortImplDecorator;
import com.ead.payment.adapter.service.decorator.query.PaymentQueryServicePortImplDecorator;
import com.ead.payment.adapter.service.decorator.query.UserQueryServicePortImplDecorator;
import com.ead.payment.core.port.persistence.CreditCardPersistencePort;
import com.ead.payment.core.port.persistence.PaymentPersistencePort;
import com.ead.payment.core.port.persistence.UserPersistencePort;
import com.ead.payment.core.port.publisher.PaymentCommandPublisherPort;
import com.ead.payment.core.port.publisher.PaymentEventPublisherPort;
import com.ead.payment.core.port.service.CreditCardServicePort;
import com.ead.payment.core.port.service.PaymentProcessorServicePort;
import com.ead.payment.core.port.service.PaymentServicePort;
import com.ead.payment.core.port.service.UserServicePort;
import com.ead.payment.core.port.service.query.CreditCardQueryServicePort;
import com.ead.payment.core.port.service.query.PaymentQueryServicePort;
import com.ead.payment.core.port.service.query.UserQueryServicePort;
import com.ead.payment.core.service.CreditCardServicePortImpl;
import com.ead.payment.core.service.PaymentServicePortImpl;
import com.ead.payment.core.service.UserServicePortImpl;
import com.ead.payment.core.service.query.CreditCardQueryServicePortImpl;
import com.ead.payment.core.service.query.PaymentQueryServicePortImpl;
import com.ead.payment.core.service.query.UserQueryServicePortImpl;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
@ComponentScan(basePackageClasses = PaymentApplication.class)
public class BeanConfig {

    @Bean
    @Primary
    public CreditCardQueryServicePort creditCardQueryServicePort(final CreditCardPersistencePort creditCardPersistencePort){
        var creditCardQueryServicePort = new CreditCardQueryServicePortImpl(creditCardPersistencePort);
        return new CreditCardQueryServicePortImplDecorator(creditCardQueryServicePort);
    }

    @Bean
    @Primary
    public PaymentQueryServicePort paymentQueryServicePort(final PaymentPersistencePort persistencePort){
        var paymentQueryServicePort = new PaymentQueryServicePortImpl(persistencePort);
        return new PaymentQueryServicePortImplDecorator(paymentQueryServicePort);
    }

    @Bean
    @Primary
    public UserQueryServicePort userQueryServicePort(final UserPersistencePort userPersistencePort){
        var userQueryServicePort = new UserQueryServicePortImpl(userPersistencePort);
        return new UserQueryServicePortImplDecorator(userQueryServicePort);
    }

    @Bean
    @Primary
    public CreditCardServicePort creditCardServicePort(final CreditCardPersistencePort creditCardPersistencePort){
        var creditCardServicePort = new CreditCardServicePortImpl(creditCardPersistencePort);
        return new CreditCardServicePortImplDecorator(creditCardServicePort);
    }

    @Bean
    @Primary
    public UserServicePort userServicePort(final UserPersistencePort userPersistencePort){
        var userServicePort = new UserServicePortImpl(userPersistencePort);
        return new UserServicePortImplDecorator(userServicePort);
    }

    @Bean
    @Primary
    public PaymentCommandPublisherPort publisherPort(final RabbitTemplate rabbitTemplate, final PaymentMapper paymentMapper,
                                                     @Value("${ead.broker.exchange.payment-command-exchange.name}")
                                                     final String paymentCommandExchange,
                                                     @Value("${ead.broker.key.payment-command-key}")
                                                         final String paymentCommandKey){
        return new PaymentCommandPublisherPortImpl(rabbitTemplate, paymentMapper, paymentCommandExchange, paymentCommandKey);
    }

    @Bean
    @Primary
    public PaymentEventPublisherPort eventPublisherPort(final RabbitTemplate rabbitTemplate,
                                                        final PaymentMapper paymentMapper,
                                                        @Value("${ead.broker.exchange.payment-event-exchange.name}")
                                                        final String exchangePaymentEvent){
        return new PaymentEventPublisherPortImpl(rabbitTemplate, paymentMapper, exchangePaymentEvent);
    }

    @Bean
    @Primary
    public PaymentServicePort paymentServicePort(final PaymentPersistencePort persistencePort, final PaymentQueryServicePort paymentQueryServicePort,
                                                 final PaymentMapper paymentMapper, final PaymentCommandPublisherPort publisherPort,
                                                 final PaymentEventPublisherPort eventPublisherPort, final PaymentProcessorServicePort paymentProcessorServicePort,
                                                 final CreditCardServicePort creditCardServicePort, final CreditCardQueryServicePort creditCardQueryServicePort,
                                                 final CreditCardMapper creditCardMapper, final UserServicePort userServicePort,
                                                 final UserQueryServicePort userQueryServicePort, UserMapper userMapper){
        var paymentServicePort = new PaymentServicePortImpl(persistencePort, paymentQueryServicePort, paymentMapper,
                publisherPort, eventPublisherPort, paymentProcessorServicePort, creditCardServicePort, creditCardQueryServicePort,
                creditCardMapper, userServicePort, userQueryServicePort, userMapper);
        return new PaymentServicePortImplDecorator(paymentServicePort);
    }

}
