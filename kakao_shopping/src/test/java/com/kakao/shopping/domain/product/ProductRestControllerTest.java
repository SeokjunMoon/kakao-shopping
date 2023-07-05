package com.kakao.shopping.domain.product;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class ProductRestControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void findAllTest() throws Exception {
        ResultActions resultActions = mockMvc.perform(
                get("/products")
        );

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        resultActions.andExpect(jsonPath("$.success").value("true"));

        resultActions.andExpect(jsonPath("$.response[0].id").value(1));
        resultActions.andExpect(jsonPath("$.response[0].name").value("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전"));
        resultActions.andExpect(jsonPath("$.response[0].description").value(""));
        resultActions.andExpect(jsonPath("$.response[0].image").value("/images/1.jpg"));
        resultActions.andExpect(jsonPath("$.response[0].price").value(1000));

        resultActions.andExpect(jsonPath("$.response[1].id").value(2));
        resultActions.andExpect(jsonPath("$.response[1].name").value("[황금약 단밤 골드]2022년산 햇밤 칼집밤700g외/군밤용/생율"));
        resultActions.andExpect(jsonPath("$.response[1].description").value(""));
        resultActions.andExpect(jsonPath("$.response[1].image").value("/images/2.jpg"));
        resultActions.andExpect(jsonPath("$.response[1].price").value(2000));

        resultActions.andExpect(jsonPath("$.response[2].id").value(3));
        resultActions.andExpect(jsonPath("$.response[2].name").value("삼성전자 JBL JR310 외 어린이용/성인용 헤드셋 3종!"));
        resultActions.andExpect(jsonPath("$.response[2].description").value(""));
        resultActions.andExpect(jsonPath("$.response[2].image").value("/images/3.jpg"));
        resultActions.andExpect(jsonPath("$.response[2].price").value(30000));

        resultActions.andExpect(jsonPath("$.response[3].id").value(4));
        resultActions.andExpect(jsonPath("$.response[3].name").value("바른 누룽지맛 발효효소 2박스 역가수치보장 / 외 7종"));
        resultActions.andExpect(jsonPath("$.response[3].description").value(""));
        resultActions.andExpect(jsonPath("$.response[3].image").value("/images/4.jpg"));
        resultActions.andExpect(jsonPath("$.response[3].price").value(4000));

        resultActions.andExpect(jsonPath("$.response[4].id").value(5));
        resultActions.andExpect(jsonPath("$.response[4].name").value("[더주] 컷팅말랑장족, 숏다리 100g/300g 외 주전부리 모음 /중독성 최고/마른 안주"));
        resultActions.andExpect(jsonPath("$.response[4].description").value(""));
        resultActions.andExpect(jsonPath("$.response[4].image").value("/images/5.jpg"));
        resultActions.andExpect(jsonPath("$.response[4].price").value(5000));

        resultActions.andExpect(jsonPath("$.response[5].id").value(6));
        resultActions.andExpect(jsonPath("$.response[5].name").value("굳지않는 앙금절편 1,050g 2팩 외 우리쌀떡 모음전"));
        resultActions.andExpect(jsonPath("$.response[5].description").value(""));
        resultActions.andExpect(jsonPath("$.response[5].image").value("/images/6.jpg"));
        resultActions.andExpect(jsonPath("$.response[5].price").value(15900));

        resultActions.andExpect(jsonPath("$.response[6].id").value(7));
        resultActions.andExpect(jsonPath("$.response[6].name").value("eoe 이너딜리티 30포, 오렌지맛 고 식이섬유 보충제"));
        resultActions.andExpect(jsonPath("$.response[6].description").value(""));
        resultActions.andExpect(jsonPath("$.response[6].image").value("/images/7.jpg"));
        resultActions.andExpect(jsonPath("$.response[6].price").value(26800));

        resultActions.andExpect(jsonPath("$.response[7].id").value(8));
        resultActions.andExpect(jsonPath("$.response[7].name").value("제나벨 PDRN 크림 2개. 피부보습/진정 케어"));
        resultActions.andExpect(jsonPath("$.response[7].description").value(""));
        resultActions.andExpect(jsonPath("$.response[7].image").value("/images/8.jpg"));
        resultActions.andExpect(jsonPath("$.response[7].price").value(25900));

        resultActions.andExpect(jsonPath("$.response[8].id").value(9));
        resultActions.andExpect(jsonPath("$.response[8].name").value("플레이스테이션 VR2 호라이즌 번들. 생생한 몰입감"));
        resultActions.andExpect(jsonPath("$.response[8].description").value(""));
        resultActions.andExpect(jsonPath("$.response[8].image").value("/images/9.jpg"));
        resultActions.andExpect(jsonPath("$.response[8].price").value(797000));
    }

    @Test
    public void findByIdTest() throws Exception {
        int id = 1;

        ResultActions resultActions = mockMvc.perform(
                get("/products/" + id)
        );

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        resultActions.andExpect(jsonPath("$.success").value("true"));

        resultActions.andExpect(jsonPath("$.response.id").value(1));
        resultActions.andExpect(jsonPath("$.response.productName").value("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전"));
        resultActions.andExpect(jsonPath("$.response.description").value(""));
        resultActions.andExpect(jsonPath("$.response.image").value("/images/1.jpg"));
        resultActions.andExpect(jsonPath("$.response.price").value(1000));

        resultActions.andExpect(jsonPath("$.response.options[0].id").value(1));
        resultActions.andExpect(jsonPath("$.response.options[0].optionName").value("01. 슬라이딩 지퍼백 크리스마스에디션 4종"));
        resultActions.andExpect(jsonPath("$.response.options[0].price").value(10000));

        resultActions.andExpect(jsonPath("$.response.options[1].id").value(2));
        resultActions.andExpect(jsonPath("$.response.options[1].optionName").value("02. 슬라이딩 지퍼백 플라워에디션 5종"));
        resultActions.andExpect(jsonPath("$.response.options[1].price").value(10900));
    }
}
