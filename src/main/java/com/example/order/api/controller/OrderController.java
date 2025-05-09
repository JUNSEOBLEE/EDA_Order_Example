package com.example.order.api.controller;

import com.example.order.application.service.OrderService;
import com.example.order.domain.model.Order;
import com.example.order.dto.OrderDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * version : 1.0
 * author : JUNSEOB
 */
@Slf4j
@RequestMapping("/api/v1/order")
@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/create")
    public ResponseEntity<?> createOrder(@RequestBody OrderDto orderDto) {
        Order order = orderService.createOrder(orderDto);
        return ResponseEntity.ok(order);
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<?> selectOrderList(@PathVariable("customerId") String customerId) {
        List<Order> orderList = orderService.selectOrderList(customerId);
        return ResponseEntity.ok(orderList);
    }
}
