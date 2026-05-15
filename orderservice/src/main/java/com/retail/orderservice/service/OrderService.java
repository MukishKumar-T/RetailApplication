package com.retail.orderservice.service;

import com.retail.orderservice.client.ProductClient;
import com.retail.orderservice.dto.OrderDTO;
import com.retail.orderservice.dto.OrderItemDTO;
import com.retail.orderservice.dto.ProductDTO;
import com.retail.orderservice.dto.ProductQuantityDTO;
import com.retail.orderservice.entity.Order;
import com.retail.orderservice.entity.OrderItem;
import com.retail.orderservice.repository.OrderRepository;
import com.retail.orderservice.util.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {
    @Autowired
    OrderRepository orderRepository;

    @Autowired
    ProductClient productClient;

    @Autowired
    JWTUtil jwtUtil;

    public Order createOrder(OrderDTO orderDTO){
        Order o = new Order();
        o.setUserId(orderDTO.getUserId());

        double totAmount = 0;

        List<OrderItem> orderedItems = new ArrayList<>();
        for(OrderItemDTO i : orderDTO.getOrderItems()){
            OrderItem ord = new OrderItem();
            ord.setOrder(o);
            ord.setProductId(i.getProductId());
            ord.setQuantity(i.getQuantity());
            ProductDTO productDTO = productClient.getProductById(i.getProductId());
            totAmount += i.getQuantity() * productDTO.getPrice();
            orderedItems.add(ord);

            ProductQuantityDTO dto = new ProductQuantityDTO();
            dto.setProductId(i.getProductId());
            dto.setQuantity(i.getQuantity());
            productClient.decrementQuantity(dto);
        }

        o.setOrderItems(orderedItems);
        o.setAmount(totAmount);

        return orderRepository.save(o);
    }

    public List<Order> allOrders(){
        return orderRepository.findAll();
    }
}
