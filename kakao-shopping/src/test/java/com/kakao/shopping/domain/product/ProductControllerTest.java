package com.kakao.shopping.domain.product;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kakao.shopping.dto.product.request.OptionStockUpdateRequest;
import com.kakao.shopping.dto.product.request.ProductUpdateRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@DisplayName("Product Controller Test")
@AutoConfigureMockMvc
@SpringBootTest
public class ProductControllerTest {
    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;

    public ProductControllerTest(
            @Autowired MockMvc mockMvc,
            @Autowired ObjectMapper objectMapper
    ) {
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
    }

    @DisplayName("GET /product : success")
    @Test
    public void select_all_success_test() throws Exception {
        // given
        int page = 0;

        // when
        ResultActions resultActions = mockMvc.perform(
                get("/product")
                        .param("page", String.valueOf(page))
                        .contentType(MediaType.APPLICATION_JSON)
        );

        // then
        resultActions.andExpect(jsonPath("$.success").value("true"));
        resultActions.andExpect(jsonPath("$.response[0].name").value("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전"));
        resultActions.andExpect(jsonPath("$.response[0].price").value(1000));
    }

    @DisplayName("GET /product/{id} : success")
    @Test
    public void findByIdTest() throws Exception {
        // given
        Long productId = 1L;

        // when
        ResultActions resultActions = mockMvc.perform(
                get("/product/" + productId)
        );

        // then
        resultActions.andExpect(jsonPath("$.success").value("true"));
        resultActions.andExpect(jsonPath("$.response.name").value("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전"));
    }

    @DisplayName("PUT /product/stock : success")
    @WithUserDetails(value = "moon@naver.com")
    @Test
    public void update_stock_success_test() throws Exception {
        // given
        Long optionId = 1L;
        Long stock = 50L;
        OptionStockUpdateRequest request = new OptionStockUpdateRequest(optionId, stock);
        String requestBody = objectMapper.writeValueAsString(request);

        // when
        ResultActions resultActions = mockMvc.perform(
                put("/product/stock")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody)
        );

        // then
        resultActions.andExpect(jsonPath("$.success").value("true"));
        resultActions.andExpect(jsonPath("$.response.stock").value(stock));
    }

    @DisplayName("PUT /product/{id} : success")
    @WithUserDetails(value = "moon@naver.com")
    @Test
    public void update_product_success_test() throws Exception{
        // given
        Long productId = 2L;
        String testName = "test name";
        String testDescription = "test description";
        String testImage = "test image";
        Long testPrice = 1000000L;
        ProductUpdateRequest request = new ProductUpdateRequest(testName, testDescription, testImage, testPrice);
        String requestBody = objectMapper.writeValueAsString(request);

        // when
        ResultActions resultActions = mockMvc.perform(
                put("/product/" + productId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody)
        );

        // then
        resultActions.andExpect(jsonPath("$.success").value("true"));
        resultActions.andExpect(jsonPath("$.response.name").value(testName));
        resultActions.andExpect(jsonPath("$.response.description").value(testDescription));
        resultActions.andExpect(jsonPath("$.response.image").value(testImage));
        resultActions.andExpect(jsonPath("$.response.price").value(testPrice));
    }
}
