package com.example.order.infrastructure.repository;

import com.example.order.domain.exception.OrderNotFoundException;
import com.example.order.domain.model.Order;
import com.example.order.domain.model.OrderId;
import com.example.order.domain.repository.OrderRepository;
import com.example.order.infrastructure.entity.OrderEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Infra Structure --> Domain Structure에서의 추상화를 실제 구현
 * version : 1.0
 * author : JUNSEOB
 */
@Repository
@RequiredArgsConstructor
public class JpaOrderRepository implements OrderRepository {

    private final SpringDataOrderRepository jpaRepository;


    @Override
    public void save(Order order) {
        OrderEntity entity = OrderEntity.fromDomain(order);
        jpaRepository.save(entity);
    }

    @Override
    public Order findById(OrderId orderId) {
        OrderEntity entity = jpaRepository.findById(orderId.value()).orElseThrow(() -> new OrderNotFoundException(orderId));
        return entity.toDomain();
    }

    @Override
    public List<Order> findAllByCustomerId(String customerId) {
        List<OrderEntity> orderEntities = jpaRepository.findAllByCustomerId(customerId);
        return orderEntities.stream()
                .map(entity -> entity.toDomain())
                .toList();
    }
}
