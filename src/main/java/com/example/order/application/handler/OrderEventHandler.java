package com.example.order.application.handler;

import com.example.order.application.service.OrderService;
import com.example.order.domain.event.OrderStatusChangeEvent;
import com.example.order.domain.model.OrderId;
import com.example.order.domain.model.OrderStatus;
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
    public Consumer<Message<OrderStatusChangeEvent>> orderChangeStatus() {
        return input -> {
            OrderStatusChangeEvent event = input.getPayload();
            orderService.updateOrderStatus(new OrderId(event.orderId()), OrderStatus.valueOf(event.orderStatus()));
        };
    }

}
