package com.example.order.domain.model;

import com.example.order.domain.event.DomainEvent;
import com.example.order.domain.event.OrderCreatedEvent;
import com.example.order.domain.event.OrderStatusChangeEvent;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * version : 1.0
 * author : JUNSEOB
 */
@Data
public class Order {
    private final OrderId orderId;
    private final CustomerId customerId;
    private OrderStatus orderStatus;
    private List<OrderLine> orderLines;
    private List<DomainEvent> domainEvents = new ArrayList<>();

    public Order(OrderId orderId, CustomerId customerId, OrderStatus orderStatus, List<OrderLine> orderLines) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.orderStatus = orderStatus;
        this.orderLines = orderLines;
    }

    public record OrderLine(
            String productId,
            int quantity
    ) {}

    public static Order createOrder(OrderId orderId, CustomerId customerId, OrderStatus orderStatus, List<OrderLine> orderLines) {
        Order order = new Order(orderId, customerId, orderStatus, orderLines);
        // domain event 등록
        order.registerEvent(new OrderCreatedEvent(
                order.getOrderId().value(),
                order.getCustomerId().value(),
                order.getOrderStatus().toString(),
                order.getOrderLines().stream()
                        .map(line -> new OrderCreatedEvent.OrderLineCreatedEvent(line.productId(), line.quantity()))
                        .collect(Collectors.toList()),
                LocalDateTime.now()
        ));
        return order;
    }

    public void updateOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
        // domain event 등록
        registerEvent(new OrderStatusChangeEvent(
                this.orderId.value(),
                orderStatus.toString(),
                LocalDateTime.now()
        ));
    }

    public void registerEvent(DomainEvent event) {
        domainEvents.add(event);
    }

    public List<DomainEvent> getDomainEvents() {
        return Collections.unmodifiableList(domainEvents);
    }

    public void clearDomainEvents() {
        domainEvents.clear();
    }
}
