package com.example.order.infrastructure.repository;

import com.example.order.infrastructure.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * version : 1.0
 * author : JUNSEOB
 */
@Repository
public interface SpringDataOrderRepository extends JpaRepository<OrderEntity, String> {
    List<OrderEntity> findAllByCustomerId(String customerId);
}
