package com.retail.productservice.service;

import com.retail.productservice.dto.ProductDTO;
import com.retail.productservice.dto.ProductQuantityDTO;
import com.retail.productservice.entity.Product;
import com.retail.productservice.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    ProductRepository productRepository;

    public Product addProduct(Product product){
        return productRepository.save(product);
    }

    public List<Product> getAllProducts(){
        return productRepository.findAll();
    }

    public Product updateQuantity(ProductQuantityDTO productQuantityDTO) {

        Product product = productRepository.findByName(productQuantityDTO.getName())
                .orElseThrow(() -> new RuntimeException("Product not found with name: " + productQuantityDTO.getName()));

        if (productQuantityDTO.getAdd()) {
            product.setQuantity(product.getQuantity() + productQuantityDTO.getQuantity());
        } else {
            int q = product.getQuantity();

            if (q - productQuantityDTO.getQuantity() < 0) {
                throw new RuntimeException("Only " + q + " quantity available!");
            }

            product.setQuantity(q - productQuantityDTO.getQuantity());
        }

        return productRepository.save(product);
    }

    public Product removeProduct(ProductDTO productDTO){
        Product product = productRepository.findByName(productDTO.getName())
                        .orElseThrow(() -> new RuntimeException("Product not found with name: " + productDTO.getName()));
        productRepository.delete(product);
        return product;
    }

    public ProductDTO getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        return new ProductDTO(product.getId(), product.getName(), product.getPrice(), product.getQuantity());
    }

    public void decrementQuantity(Long productId, int quantity) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));

        if (product.getQuantity() < quantity) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Not enough stock");
        }

        product.setQuantity(product.getQuantity() - quantity);
        productRepository.save(product);
    }
}
