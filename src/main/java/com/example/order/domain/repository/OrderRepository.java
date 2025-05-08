package com.example.order.domain.repository;

import com.example.order.domain.model.Order;
import com.example.order.domain.model.OrderId;

import java.util.List;
import java.util.Optional;

/**
 * 의존성 역전 원칙
 * Domain Structure는 Infra Structure에서 실제 구현이 어떤지는 몰라야 한다.
 * version : 1.0
 * author : JUNSEOB
 */
public interface OrderRepository {
    void save(Order order);
    Order findById(OrderId orderId);

    List<Order> findAllByCustomerId(String customerId);
}
