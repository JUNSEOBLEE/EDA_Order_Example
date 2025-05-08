package com.example.order.application.publisher;

import com.example.order.domain.event.DomainEvent;
import com.example.order.domain.event.OrderCreatedEvent;
import com.example.order.domain.model.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

/**
 * version : 1.0
 * author : JUNSEOB
 */
@Service
@RequiredArgsConstructor
public class OrderCreatedEventPublisher {
    @Value("${spring.cloud.stream.bindings.orderCreated-out-0.destination}")
    private String orderCreatedTopicNm;
    private final StreamBridge bridge;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void createdPublish(Order order) {
        OrderCreatedEvent event = new OrderCreatedEvent(
                order.getOrderId().value(),
                order.getCustomerId().value(),
                order.getOrderStatus(),
                order.getOrderLines().stream()
                        .map(line -> new OrderCreatedEvent.OrderLineDto(line.getProductId(), line.getQuantity()))
                        .collect(Collectors.toList())
                , LocalDateTime.now()
        );
        Message<OrderCreatedEvent> message = MessageBuilder.withPayload(event).build();
        bridge.send(orderCreatedTopicNm, message);
    }
}
