package com.example.order.domain.event;

/**
 * version : 1.0
 * author : JUNSEOB
 */
public record OrderStatusChangeEvent(
        String orderId,
        String orderStatus
) implements DomainEvent {
}
