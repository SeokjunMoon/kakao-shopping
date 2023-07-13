package com.kakao.shopping.controller;

import com.kakao.shopping._core.utils.ApiUtils;
import com.kakao.shopping.dto.CartItemDTO;
import com.kakao.shopping.dto.OrderUpdateRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/orders")
public class OrderController {
    @GetMapping("/")
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok().body(ApiUtils.success(null));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable int id) {
        return ResponseEntity.ok().body(ApiUtils.success(null));
    }

    @PostMapping("/")
    public ResponseEntity<?> insert(@RequestBody List<CartItemDTO> products) {
        return ResponseEntity.ok().body(ApiUtils.success(null));
    }

    @PutMapping("/")
    public ResponseEntity<?> update(@RequestBody OrderUpdateRequest request) {
        return ResponseEntity.ok().body(ApiUtils.success(null));
    }
}
