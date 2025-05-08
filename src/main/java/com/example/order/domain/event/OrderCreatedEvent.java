package com.example.order.domain.event;

import com.example.order.domain.model.OrderStatus;

import java.time.LocalDateTime;
import java.util.List;

/**
 * version : 1.0
 * author : JUNSEOB
 */
public record OrderCreatedEvent(
        String orderId,
        String customerId,
        OrderStatus orderStatus,
        List<OrderLineDto> orderLines,
        LocalDateTime occurredAt
) implements DomainEvent {
    public record OrderLineDto(
            String productId,
            int quantity
    ) {}
}
