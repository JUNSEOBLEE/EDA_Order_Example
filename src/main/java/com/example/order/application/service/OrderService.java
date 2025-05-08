package com.example.order.application.service;

import com.example.order.application.publisher.DomainEventPublisher;
import com.example.order.application.publisher.OrderCreatedEventPublisher;
import com.example.order.domain.event.DomainEvent;
import com.example.order.domain.model.Order;
import com.example.order.domain.model.OrderId;
import com.example.order.domain.model.OrderStatus;
import com.example.order.domain.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * version : 1.0
 * author : JUNSEOB
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderCreatedEventPublisher orderCreatedEventPublisher;
    private final DomainEventPublisher domainEventPublisher;

    public Order createOrder(Order order) {
        // 1. 주문 저장
        orderRepository.save(order);
        // 2. 주문 이벤트 발행
        orderCreatedEventPublisher.createdPublish(order);
        // 이벤트 발행
        publishDomainEvents(order);
        return order;
    }

    public void updateOrderStatus(OrderId orderId, OrderStatus orderStatus) {
        // 1. 조회
        Order order = orderRepository.findById(orderId);

        // 2. 수정
        order.updateOrderStatus(orderStatus);
        
        // 3. 업데이트
        orderRepository.save(order);
        // 4. 이벤트 발행
        publishDomainEvents(order);
    }

    private void publishDomainEvents(Order order) {
        // 4. domain event 추출
        List<DomainEvent> events = order.getDomainEvents();

        // 5. 이벤트 발행
        for(DomainEvent event : events) {
            domainEventPublisher.domainEventPublish(event);
        }

        // 6. 이벤트 리스트 비우기
        order.clearDomainEvents();
    }

    public List<Order> selectOrderList(String customerId) {
        List<Order> orderList = orderRepository.findAllByCustomerId(customerId);
        return orderList;
    }
}
