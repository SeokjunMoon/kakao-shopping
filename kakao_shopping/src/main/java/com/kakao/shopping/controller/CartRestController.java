package com.kakao.shopping.controller;

import com.kakao.shopping._core.utils.ApiUtils;
import com.kakao.shopping.domain.cart.dto.CartItemDTO;
import com.kakao.shopping.domain.cart.dto.CartProductDTO;
import com.kakao.shopping.domain.cart.dto.CartDTO;
import com.kakao.shopping.domain.product.dto.ProductOptionDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/carts")
public class CartRestController {
    @GetMapping("/")
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok(ApiUtils.success(null));
    }

    @PostMapping("/")
    public ResponseEntity<?> insert() {
        return ResponseEntity.ok().body(ApiUtils.success(null));
    }

    @PutMapping("/")
    public ResponseEntity<?> update() {
        return ResponseEntity.ok().body(ApiUtils.success(null));
    }
}
