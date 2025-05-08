package com.example.order.domain.exception;

import com.example.order.domain.model.OrderId;

/**
 * version : 1.0
 * author : JUNSEOB
 */
public class OrderNotFoundException extends RuntimeException{
    public OrderNotFoundException(OrderId orderId) {
        super("주문을 찾을 수 없습니다 : " + orderId.value());
    }
}
