package com.example.order.domain.event;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * version : 1.0
 * author : JUNSEOB
 */
@Data
public class OrderStatusChangeEvent extends DomainEvent {
    public OrderStatusChangeEvent(String orderId, String orderStatus, LocalDateTime occurredAt) {
        super(orderId, orderStatus, occurredAt);
    }
}
