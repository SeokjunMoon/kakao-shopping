package com.kakao.shopping.controller;

import com.kakao.shopping._core.security.CustomUserDetails;
import com.kakao.shopping._core.utils.ApiUtils;
import com.kakao.shopping.dto.cart.CartDTO;
import com.kakao.shopping.dto.cart.request.CartDeleteRequest;
import com.kakao.shopping.dto.cart.request.CartInsertRequest;
import com.kakao.shopping.dto.cart.request.CartUpdateRequest;
import com.kakao.shopping.dto.cart.response.CartUpdateResponse;
import com.kakao.shopping.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class CartController {
    private final CartService cartService;

    @GetMapping("/cart")
    public ResponseEntity<?> findAll(@AuthenticationPrincipal CustomUserDetails userDetails) {
        CartDTO carts = cartService.findAll(userDetails.getUserAccount());
        return ResponseEntity.ok(ApiUtils.success(carts));
    }

    @PostMapping("/cart")
    public ResponseEntity<?> insert(
            @Valid @RequestBody List<CartInsertRequest> request,
            Errors errors,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        cartService.addCartList(request, userDetails.getUserAccount());
        return ResponseEntity.ok().body(ApiUtils.success(null));
    }

    @PutMapping("/cart")
    public ResponseEntity<?> update(
            @Valid @RequestBody List<CartUpdateRequest> request,
            Errors errors,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        CartUpdateResponse carts = cartService.update(request, userDetails.getUserAccount());
        return ResponseEntity.ok().body(ApiUtils.success(carts));
    }

    @DeleteMapping("/cart")
    public ResponseEntity<?> delete(
            @Valid @RequestBody List<CartDeleteRequest> requests,
            Errors errors,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        cartService.delete(requests, userDetails.getUserAccount());
        return ResponseEntity.ok().body(ApiUtils.success(null));
    }
}
