package com.kakao.shopping.domain.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kakao.shopping.dto.user.UserLoginRequest;
import com.kakao.shopping.dto.user.UserRegisterRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@DisplayName("UserAccount Controller Test")
@AutoConfigureMockMvc
@SpringBootTest
public class UserAccountControllerTest {
    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;

    public UserAccountControllerTest(
            @Autowired MockMvc mockMvc,
            @Autowired ObjectMapper objectMapper
    ) {
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
    }

    @DisplayName("TEST : POST /join : success")
    @Test
    public void register_success_test() throws Exception {
        // given
        UserRegisterRequest request = new UserRegisterRequest(
                "test", "test@kakao.com", "qwer1234!", LocalDate.of(2000, 1, 1)
        );

        // when
        ResultActions resultActions = getResultActions(request);

        // then
        resultActions.andExpect(jsonPath("$.success").value("true"));
    }

    @DisplayName("TEST : POST /join : fail - invalid email format")
    @Test
    public void register_fail_test_invalid_email_format() throws Exception {
        // given
        UserRegisterRequest request = new UserRegisterRequest(
                "test", "testkakao.com", "qwer1234!", LocalDate.of(2000, 1, 1)
        );

        // when
        ResultActions resultActions = getResultActions(request);

        // then
        resultActions.andExpect(jsonPath("$.success").value("false"));
    }

    @DisplayName("TEST : POST /join : fail - invalid password length")
    @Test
    public void register_fail_test_invalid_password_length() throws Exception {
        // given
        UserRegisterRequest request = new UserRegisterRequest(
                "test", "test@kakao.com", "qwer12!", LocalDate.of(2000, 1, 1)
        );

        // when
        ResultActions resultActions = getResultActions(request);

        // then
        resultActions.andExpect(jsonPath("$.success").value("false"));
    }

    @DisplayName("TEST : POST /join : fail - invalid password format - 특수기호 누락")
    @Test
    public void register_fail_test_invalid_password_format() throws Exception {
        // given
        UserRegisterRequest request = new UserRegisterRequest(
                "test", "test@kakao.com", "qwer12345", LocalDate.of(2000, 1, 1)
        );

        // when
        ResultActions resultActions = getResultActions(request);

        // then
        resultActions.andExpect(jsonPath("$.success").value("false"));
    }

    @DisplayName("TEST : POST /join : fail - invalid password format - 숫자 누락")
    @Test
    public void register_fail_test_invalid_password_format2() throws Exception {
        // given
        UserRegisterRequest request = new UserRegisterRequest(
                "test", "test@kakao.com", "qwer!!!!!", LocalDate.of(2000, 1, 1)
        );

        // when
        ResultActions resultActions = getResultActions(request);

        // then
        resultActions.andExpect(jsonPath("$.success").value("false"));
    }

    @DisplayName("TEST : POST /join : fail - invalid password format - 영문자 누락")
    @Test
    public void register_fail_test_invalid_password_format3() throws Exception {
        // given
        UserRegisterRequest request = new UserRegisterRequest(
                "test", "test@kakao.com", "1234!!!!!", LocalDate.of(2000, 1, 1)
        );

        // when
        ResultActions resultActions = getResultActions(request);

        // then
        resultActions.andExpect(jsonPath("$.success").value("false"));
    }

    @DisplayName("TEST : POST /join : fail - email already exist")
    @Test
    public void register_fail_test_email_already_exist() throws Exception {
        // given
        UserRegisterRequest request = new UserRegisterRequest(
                "test", "moon@naver.com", "qwer1234!", LocalDate.of(2000, 1, 1)
        );

        // when
        ResultActions resultActions = getResultActions(request);

        // then
        resultActions.andExpect(jsonPath("$.success").value("false"));
    }

    @DisplayName("TEST : POST /join : fail - name 누락")
    @Test
    public void register_fail_test_missing_name() throws Exception {
        // given
        UserRegisterRequest request = new UserRegisterRequest(
                null, "test@naver.com", "qwer1234!", LocalDate.of(2000, 1, 1)
        );

        // when
        ResultActions resultActions = getResultActions(request);

        // then
        resultActions.andExpect(jsonPath("$.success").value("false"));
    }

    @DisplayName("TEST : POST /join : fail - email 누락")
    @Test
    public void register_fail_test_missing_email() throws Exception {
        // given
        UserRegisterRequest request = new UserRegisterRequest(
                "moon", null, "qwer1234!", LocalDate.of(2000, 1, 1)
        );

        // when
        ResultActions resultActions = getResultActions(request);

        // then
        resultActions.andExpect(jsonPath("$.success").value("false"));
    }

    @DisplayName("TEST : POST /join : fail - password 누락")
    @Test
    public void register_fail_test_missing_password() throws Exception {
        // given
        UserRegisterRequest request = new UserRegisterRequest(
                "moon", "test@naver.com", null, LocalDate.of(2000, 1, 1)
        );

        // when
        ResultActions resultActions = getResultActions(request);

        // then
        resultActions.andExpect(jsonPath("$.success").value("false"));
    }

    @DisplayName("TEST : POST /login : success")
    @Test
    public void login_success_test() throws Exception {
        // given
        UserLoginRequest request = new UserLoginRequest("moon@naver.com", "qwer1234!");

        // when
        ResultActions resultActions = getResultActions(request);

        // then
        resultActions.andExpect(jsonPath("$.success").value("true"));
    }

    @DisplayName("TEST : POST /login : fail - 존재하지 않는 이메일")
    @Test
    public void login_success_fail_test_email_not_exist() throws Exception {
        // given
        UserLoginRequest request = new UserLoginRequest("moon@nate.com", "qwer1234!");

        // when
        ResultActions resultActions = getResultActions(request);

        // then
        resultActions.andExpect(jsonPath("$.success").value("false"));
    }

    @DisplayName("TEST : POST /login : fail - 비밀번호 불일치")
    @Test
    public void login_success_fail_test_password_mismatch() throws Exception {
        // given
        UserLoginRequest request = new UserLoginRequest("moon@naver.com", "asdf1234!");

        // when
        ResultActions resultActions = getResultActions(request);

        // then
        resultActions.andExpect(jsonPath("$.success").value("false"));
    }


    // ------------------------------------------------------------------------------------


    private ResultActions getResultActions(UserRegisterRequest request) throws Exception {
        String requestBody = objectMapper.writeValueAsString(request);
        return mockMvc.perform(
                post("/join")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody)
        );
    }

    private ResultActions getResultActions(UserLoginRequest request) throws Exception {
        String requestBody = objectMapper.writeValueAsString(request);
        return mockMvc.perform(
                post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody)
        );
    }
}
