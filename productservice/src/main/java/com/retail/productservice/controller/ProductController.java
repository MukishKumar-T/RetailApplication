package com.retail.productservice.controller;

import com.retail.productservice.dto.ProductDTO;
import com.retail.productservice.dto.ProductQuantityDTO;
import com.retail.productservice.entity.Product;
import com.retail.productservice.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    ProductService productService;

    @GetMapping("/all")
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    @PostMapping("/addProduct")
    public ResponseEntity<Product> addProduct(@RequestBody Product product){
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.addProduct(product));
    }

    @PutMapping("/updateQuantity")
    public ResponseEntity<?> updateQuantity(@RequestBody ProductQuantityDTO productQuantityDTO){
        return ResponseEntity.status(HttpStatus.ACCEPTED).body((productService.updateQuantity(productQuantityDTO)));
    }

    @DeleteMapping("/removeProduct")
    public ResponseEntity<Product> removeProduct(@RequestBody ProductDTO productDTO){
        return ResponseEntity.status(HttpStatus.OK).body(productService.removeProduct(productDTO));
    }
}