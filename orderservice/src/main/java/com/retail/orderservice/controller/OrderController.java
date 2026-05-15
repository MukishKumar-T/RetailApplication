package com.retail.orderservice.controller;

import com.retail.orderservice.dto.OrderDTO;
import com.retail.orderservice.dto.OrderItemDTO;
import com.retail.orderservice.entity.Order;
import com.retail.orderservice.entity.OrderItem;
import com.retail.orderservice.service.OrderService;
import com.retail.orderservice.util.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.graphql.GraphQlProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    OrderService orderService;

    @Autowired
    JWTUtil jwtUtil;

    @PostMapping("/createOrder")
    public ResponseEntity<Order> createOrder(@RequestBody OrderDTO orderDTO,
                                             @RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer ", "");

        long userId = jwtUtil.extractUserId(token);

        orderDTO.setUserId(userId);

        Order order = orderService.createOrder(orderDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(order);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Order>> allOrders(){
        return ResponseEntity.ok(orderService.allOrders());
    }
}
