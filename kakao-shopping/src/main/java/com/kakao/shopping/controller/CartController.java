package com.kakao.shopping.controller;

import com.kakao.shopping._core.security.CustomUserDetails;
import com.kakao.shopping._core.utils.ApiUtils;
import com.kakao.shopping.dto.cart.CartInsertRequest;
import com.kakao.shopping.dto.cart.CartUpdateRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CartController {
    @GetMapping("/carts")
    public ResponseEntity<?> findAll(@AuthenticationPrincipal CustomUserDetails userDetails) {
        return ResponseEntity.ok(ApiUtils.success(null));
    }

    @PostMapping("/carts")
    public ResponseEntity<?> insert(@RequestBody List<CartInsertRequest> request, @AuthenticationPrincipal CustomUserDetails userDetails) {
        return ResponseEntity.ok().body(ApiUtils.success(null));
    }

    @PutMapping("/carts")
    public ResponseEntity<?> update(@RequestBody List<CartUpdateRequest> request, @AuthenticationPrincipal CustomUserDetails userDetails) {
        return ResponseEntity.ok().body(ApiUtils.success(null));
    }

    @DeleteMapping("/carts/{id}")
    public ResponseEntity<?> delete(@PathVariable int id, @AuthenticationPrincipal CustomUserDetails userDetails) {
        return ResponseEntity.ok().body(ApiUtils.success(null));
    }
}
