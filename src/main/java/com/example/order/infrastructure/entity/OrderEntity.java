package com.example.order.infrastructure.entity;

import com.example.order.domain.model.*;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * version : 1.0
 * author : JUNSEOB
 */
@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
public class OrderEntity {
    @Id
    @Column(name = "order_id")
    private String id;

    @Column(name = "customer_id")
    private String customerId;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private OrderStatus status;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private List<OrderLineEntity> orderLines = new ArrayList<>();

    public OrderEntity(String orderId, String customerId, OrderStatus status, List<OrderLineEntity> orderLines) {
        this.id = orderId;
        this.customerId = customerId;
        this.status = status;
        this.orderLines = orderLines;
    }

    public static OrderEntity fromDomain(Order order) {
        List<OrderLineEntity> lines = order.getOrderLines().stream()
                .map(line -> new OrderLineEntity(line.productId(), line.quantity()))
                .collect(Collectors.toList());
        return new OrderEntity(order.getOrderId().value(), order.getCustomerId().value(), order.getOrderStatus(), lines);
    }

    public Order toDomain() {
        List<Order.OrderLine> lines = orderLines.stream()
                .map(entity -> new Order.OrderLine(entity.getProductId(), entity.getQuantity()))
                .collect(Collectors.toList());
        return new Order(new OrderId(id), new CustomerId(customerId), status, lines);
    }
}
