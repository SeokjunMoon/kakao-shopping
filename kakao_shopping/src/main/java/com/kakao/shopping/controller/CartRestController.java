package com.kakao.shopping.controller;

import com.kakao.shopping._core.utils.ApiUtils;
import com.kakao.shopping.domain.cart.dto.CartItemDTO;
import com.kakao.shopping.domain.cart.dto.CartProductDTO;
import com.kakao.shopping.domain.cart.dto.CartDTO;
import com.kakao.shopping.domain.product.dto.ProductOptionDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class CartRestController {
    @GetMapping("/carts")
    public ResponseEntity<?> findAll() {
        List<CartItemDTO> cartItemDTOList = new ArrayList<>();

        CartItemDTO cartItemDTO1 = CartItemDTO.builder()
                .id(4)
                .quantity(5)
                .price(50000)
                .build();
        cartItemDTO1.setOption(ProductOptionDTO.builder()
                .id(1)
                .optionName("01. 슬라이딩 지퍼백 크리스마스에디션 4종")
                .price(10000)
                .build());
        cartItemDTOList.add(cartItemDTO1);

        CartItemDTO cartItemDTO2 = CartItemDTO.builder()
                .id(5)
                .quantity(5)
                .price(54500)
                .build();
        cartItemDTO2.setOption(ProductOptionDTO.builder()
                .id(1)
                .optionName("02. 슬라이딩 지퍼백 크리스마스에디션 5종")
                .price(10900)
                .build());
        cartItemDTOList.add(cartItemDTO2);

        List<CartProductDTO> cartProductDTOList = new ArrayList<>();
        cartProductDTOList.add(
                CartProductDTO.builder()
                        .id(1)
                        .productName("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전")
                        .carts(cartItemDTOList)
                        .build()
        );

        CartDTO responseDTO = new CartDTO(cartProductDTOList, 104500);
        return ResponseEntity.ok(ApiUtils.success(responseDTO));
    }
}
