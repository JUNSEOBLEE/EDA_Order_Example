package com.example.order.application.service;

import com.example.order.application.publisher.DomainEventPublisher;
import com.example.order.domain.event.DomainEvent;
import com.example.order.domain.model.Order;
import com.example.order.domain.model.OrderId;
import com.example.order.domain.model.OrderStatus;
import com.example.order.domain.repository.OrderRepository;
import com.example.order.dto.OrderDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private final DomainEventPublisher domainEventPublisher;

    @Transactional
    public Order createOrder(OrderDto orderDto) {
        // 1. order 변환
        Order order = orderDto.toDomain();

        // 2. 주문 저장
        orderRepository.save(order);

        // 3. 이벤트 발행
        publishDomainEvents(order);

        return order;
    }

    @Transactional
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
        return orderRepository.findAllByCustomerId(customerId);
    }
}
