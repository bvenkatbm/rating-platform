package com.daytodayhealth.rating.controller;

import com.daytodayhealth.rating.entity.ProductEntity;
import com.daytodayhealth.rating.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ProductController {

    @Autowired
    ProductService productService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping(value = "/products/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ProductEntity>> displayProductList() {
        return ResponseEntity.ok(productService.getProductList());
    }
}
