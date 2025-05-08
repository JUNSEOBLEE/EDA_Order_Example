package com.example.order.domain.model;

import com.example.order.domain.event.DomainEvent;
import com.example.order.domain.event.OrderStatusChangeEvent;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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

    public static Order createOrder(OrderId orderId, CustomerId customerId, OrderStatus orderStatus, List<OrderLine> orderLines) {
        Order order = new Order(orderId, customerId, orderStatus, orderLines);
        order.registerEvent(new OrderStatusChangeEvent(orderId.value(), orderStatus.toString()));
        return order;
    }

    public void updateOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
        registerEvent(new OrderStatusChangeEvent(this.orderId.value(), orderStatus.toString()));
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
