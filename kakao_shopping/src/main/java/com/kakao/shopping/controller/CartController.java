package com.kakao.shopping.controller;

import com.kakao.shopping._core.utils.ApiUtils;
import com.kakao.shopping.dto.CartInsertRequest;
import com.kakao.shopping.dto.CartUpdateRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/carts")
public class CartController {
    @GetMapping("/")
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok(ApiUtils.success(null));
    }

    @PostMapping("/")
    public ResponseEntity<?> insert(@RequestBody CartInsertRequest request) {
        return ResponseEntity.ok().body(ApiUtils.success(null));
    }

    @PutMapping("/")
    public ResponseEntity<?> update(@RequestBody CartUpdateRequest request) {
        return ResponseEntity.ok().body(ApiUtils.success(null));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable int id) {
        return ResponseEntity.ok().body(ApiUtils.success(null));
    }
}
