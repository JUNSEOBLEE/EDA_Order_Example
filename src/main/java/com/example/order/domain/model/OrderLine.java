package com.example.order.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * version : 1.0
 * author : JUNSEOB
 */
@Data
@AllArgsConstructor
public class OrderLine {
    private String productId;
    private int quantity;
}
