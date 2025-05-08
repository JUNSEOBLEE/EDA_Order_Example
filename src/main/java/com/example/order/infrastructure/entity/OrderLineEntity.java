package com.example.order.infrastructure.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * version : 1.0
 * author : JUNSEOB
 */
@Entity
@Table(name = "order_lines")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderLineEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "product_id", nullable = false)
    private String productId;

    @Column(name = "quantity", nullable = false)
    private int quantity;

    public OrderLineEntity(String productId, int quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }
}
