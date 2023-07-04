package com.kakao.shopping.controller;

import com.kakao.shopping._core.utils.ApiUtils;
import com.kakao.shopping.products.dto.ProductResponseFindAllDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ProductRestController {
    @GetMapping("/products")
    public ResponseEntity<?> findAll() {
        List<ProductResponseFindAllDTO> responseDTOList = new ArrayList<>();

        responseDTOList.add(new ProductResponseFindAllDTO(
                1, "기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전", "", "/images/1.jpg", 1000
        ));
        responseDTOList.add(new ProductResponseFindAllDTO(
                2, "[황금약 단밤 골드]2022년산 햇밤 칼집밤700g외/군밤용/생율", "", "/images/2.jpg", 2000
        ));
        responseDTOList.add(new ProductResponseFindAllDTO(
                3, "삼성전자 JBL JR310 외 어린이용/성인용 헤드셋 3종!", "", "/images/3.jpg", 30000
        ));
        responseDTOList.add(new ProductResponseFindAllDTO(
                4, "바른 누룽지맛 발효효소 2박스 역가수치보장 / 외 7종", "", "/images/4.jpg", 4000
        ));
        responseDTOList.add(new ProductResponseFindAllDTO(
                5, "[더주] 컷팅말랑장족, 숏다리 100g/300g 외 주전부리 모음 /중독성 최고/마른 안주", "", "/images/5.jpg", 5000
        ));
        responseDTOList.add(new ProductResponseFindAllDTO(
                6, "굳지않는 앙금절편 1,050g 2팩 외 우리쌀떡 모음전", "", "/images/6.jpg", 15900
        ));
        responseDTOList.add(new ProductResponseFindAllDTO(
                7, "eoe 이너딜리티 30포, 오렌지맛 고 식이섬유 보충제", "", "/images/7.jpg", 26800
        ));
        responseDTOList.add(new ProductResponseFindAllDTO(
                8, "제나벨 PDRN 크림 2개. 피부보습/진정 케어", "", "/images/8.jpg", 25900
        ));
        responseDTOList.add(new ProductResponseFindAllDTO(
                9, "플레이스테이션 VR2 호라이즌 번들. 생생한 몰입감", "", "/images/9.jpg", 797000
        ));

        return ResponseEntity.ok().body(ApiUtils.success(responseDTOList));
    }
}
