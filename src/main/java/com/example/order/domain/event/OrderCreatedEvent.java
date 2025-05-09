package com.example.order.domain.event;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * version : 1.0
 * author : JUNSEOB
 */
@Data
public class OrderCreatedEvent extends DomainEvent {
    private String customerId;
    private List<OrderLineCreatedEvent> orderLines;

    public OrderCreatedEvent(String orderId, String customerId, String orderStatus, List<OrderLineCreatedEvent> orderLines, LocalDateTime occurredAt) {
        super(orderId, orderStatus, occurredAt);
        this.customerId = customerId;
        this.orderLines = orderLines;
    }

    public record OrderLineCreatedEvent(
            String productId,
            int quantity
    ) {}
}
