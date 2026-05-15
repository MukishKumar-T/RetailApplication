package com.retail.orderservice.client;

import com.retail.orderservice.dto.ProductDTO;
import com.retail.orderservice.dto.ProductQuantityDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Locale;

@FeignClient(name = "productservice")
public interface ProductClient {

    @GetMapping("/products/{id}")
    ProductDTO getProductById(@PathVariable("id") Long id);

    @PostMapping("/products/decrement")
    void decrementQuantity(@RequestBody ProductQuantityDTO dto);
}