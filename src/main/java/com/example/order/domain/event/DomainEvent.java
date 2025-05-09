package com.example.order.domain.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * version : 1.0
 * author : JUNSEOB
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class DomainEvent {
    private String orderId;
    private String orderStatus;
    private LocalDateTime occurredAt;
}
