package com.retail.productservice.controller;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    @GetMapping("/all")
    public List<String> getProducts() {
        return List.of("Laptop", "Phone", "Tablet");
    }
}