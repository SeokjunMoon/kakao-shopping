package com.example.kakao_shopping.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductRestController {
    @GetMapping("/products")
    public ResponseEntity<?> findAll() {

    }
}
