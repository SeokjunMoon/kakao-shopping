package com.kakao.shopping.domain.cart;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kakao.shopping.dto.cart.request.CartDeleteRequest;
import com.kakao.shopping.dto.cart.request.CartInsertRequest;
import com.kakao.shopping.dto.cart.request.CartUpdateRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@DisplayName("Cart Controller Test")
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class CartControllerTest {
    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;

    public CartControllerTest(
            @Autowired MockMvc mockMvc,
            @Autowired ObjectMapper objectMapper
    ) {
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
    }

    @DisplayName("GET /cart : success")
    @WithUserDetails(value = "moon@naver.com")
    @Test
    public void find_cart_success_test() throws Exception {
        // given

        // when
        ResultActions resultActions = mockMvc.perform(
                get("/cart")
        );

        // then
        resultActions.andExpect(jsonPath("$.success").value("true"));
        resultActions.andExpect(jsonPath("$.response.products[0].name").value("기본에 슬라이딩 지퍼백 크리스마스/플라워에디션 에디션 외 주방용품 특가전"));
        resultActions.andExpect(jsonPath("$.response.products[0].carts[0].option.name").value("01. 슬라이딩 지퍼백 크리스마스에디션 4종"));
    }

    @DisplayName("POST /cart : success")
    @WithUserDetails(value = "moon@naver.com")
    @Test
    public void insert_cart_success_test() throws Exception {
        // given
        List<CartInsertRequest> requests = List.of(new CartInsertRequest(10L, 10L));
        String requestBody = objectMapper.writeValueAsString(requests);

        // when
        ResultActions resultActions = mockMvc.perform(
                post("/cart")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody)
        );

        // then
        resultActions.andExpect(jsonPath("$.success").value("true"));
    }

    @DisplayName("PUT /cart : success")
    @WithUserDetails(value = "moon@naver.com")
    @Test
    public void update_cart_success_test() throws Exception {
        // given
        List<CartUpdateRequest> requests = List.of(new CartUpdateRequest(1L, 100L));
        String requestBody = objectMapper.writeValueAsString(requests);

        // when
        ResultActions resultActions = mockMvc.perform(
                put("/cart")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody)
        );

        // then
        resultActions.andExpect(jsonPath("$.success").value("true"));
    }

    @DisplayName("DELETE /cart : success")
    @WithUserDetails(value = "moon@naver.com")
    @Test
    public void delete_cart_success_test() throws Exception {
        // given
        List<CartDeleteRequest> requests = List.of(new CartDeleteRequest(2L));
        String requestBody = objectMapper.writeValueAsString(requests);

        // when
        ResultActions resultActions = mockMvc.perform(
                delete("/cart")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody)
        );

        // then
        resultActions.andExpect(jsonPath("$.success").value("true"));
    }
}
