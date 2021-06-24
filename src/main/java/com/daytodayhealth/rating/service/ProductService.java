package com.daytodayhealth.rating.service;

import com.daytodayhealth.rating.entity.ProductEntity;
import com.daytodayhealth.rating.exception.NoProductException;
import com.daytodayhealth.rating.exception.ValidProductException;
import com.daytodayhealth.rating.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class ProductService {

    @Autowired
    ProductRepository productRepository;

    // get available list of products
    public List<ProductEntity> getProductList() {
        return productRepository.findAll();
    }

    public ProductEntity checkProductExistence(UUID productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new NoProductException("Please specify a valid product"));
    }

    public UUID validateProduct(String productId) {
        UUID product;
        try {
            product = UUID.fromString(productId);
        } catch (Exception e) {
            throw new ValidProductException("Please provide a valid product id");
        }
        return product;
    }

    public void add(ProductEntity productEntity) {
        productRepository.save(productEntity);
    }

    //todo: add and delete products using admin authorities
}
