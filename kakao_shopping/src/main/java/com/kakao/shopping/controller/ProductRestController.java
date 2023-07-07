package com.kakao.shopping.controller;

import com.kakao.shopping._core.utils.ApiUtils;
import com.kakao.shopping.domain.product.dto.ProductOptionDTO;
import com.kakao.shopping.domain.product.dto.ProductListItemDTO;
import com.kakao.shopping.domain.product.dto.ProductDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ProductRestController {
    @GetMapping("/products")
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok().body(ApiUtils.success(null));
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<?> findById(@PathVariable int id) {
        return ResponseEntity.ok(ApiUtils.success(null));
    }
}
