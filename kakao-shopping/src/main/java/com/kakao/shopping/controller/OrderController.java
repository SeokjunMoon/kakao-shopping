package com.kakao.shopping.controller;

import com.kakao.shopping._core.security.CustomUserDetails;
import com.kakao.shopping._core.utils.ApiUtils;
import com.kakao.shopping.dto.order.OrderDTO;
import com.kakao.shopping.dto.order.request.OrderUpdateRequest;
import com.kakao.shopping.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;

@RequiredArgsConstructor
@RestController
public class OrderController {
    private final OrderService orderService;

    @GetMapping("/order/{id}")
    public ResponseEntity<?> findById(
            @PathVariable @Min(1) Long id,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        OrderDTO order = orderService.findById(id, userDetails.getUserAccount());
        return ResponseEntity.ok().body(ApiUtils.success(order));
    }

    @PostMapping("/order")
    public ResponseEntity<?> insert(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        OrderDTO order = orderService.save(customUserDetails.getUserAccount());
        return ResponseEntity.ok().body(ApiUtils.success(order));
    }

    @PutMapping("/order")
    public ResponseEntity<?> update(
            @Valid @RequestBody OrderUpdateRequest request,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        return ResponseEntity.ok().body(ApiUtils.success(null));
    }
}
