package com.example.order.application.publisher;

import com.example.order.domain.event.DomainEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

/**
 * version : 1.0
 * author : JUNSEOB
 */
@Service
@RequiredArgsConstructor
public class DomainEventPublisher {
    @Value("${spring.cloud.stream.bindings.domainEventPublish-out-0.destination}")
    private String domainEventPublishTopicNm;
    private final StreamBridge bridge;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void domainEventPublish(DomainEvent domainEvent) {
        Message<DomainEvent> message = MessageBuilder.withPayload(domainEvent).build();
        bridge.send(domainEventPublishTopicNm, message);
    }
}
