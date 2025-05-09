package com.example.order.application.handler;

import com.example.order.application.service.OrderService;
import com.example.order.domain.event.DomainEvent;
import com.example.order.domain.event.OrderStatusChangeEvent;
import com.example.order.domain.model.OrderId;
import com.example.order.domain.model.OrderStatus;
import com.example.order.dto.OrderStatusDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

/**
 * version : 1.0
 * author : JUNSEOB
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class OrderEventHandler {

    private final OrderService orderService;

    @Bean
    public Consumer<Message<OrderStatusDto>> orderChangeStatus() {
        return input -> {
            OrderStatusDto dto = input.getPayload();
            orderService.updateOrderStatus(new OrderId(dto.orderId()), OrderStatus.valueOf(dto.orderStatus()));
        };
    }

}
