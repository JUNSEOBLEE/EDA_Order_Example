package com.example.order.ui.dto;

import com.example.order.domain.model.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * version : 1.0
 * author : JUNSEOB
 */
public record OrderDto(
        String customerId,
        List<OrderLineDto> orderLines
) {
    public record OrderLineDto(
            String productId,
            int quantity
    ) {}

    public Order toDomain() {
        return Order.createOrder(
                new OrderId(UUID.randomUUID().toString()),
                new CustomerId(customerId),
                OrderStatus.CREATED,
                orderLines.stream().map(line -> new OrderLine(line.productId, line.quantity)).collect(Collectors.toList())
        );
    }
}
