package kr.ac.jbnu.ksh.wsdbookstoreassign2.auth;

import com.fasterxml.jackson.databind.JsonNode;
import kr.ac.jbnu.ksh.wsdbookstoreassign2.support.ApiTestSupport;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AuthControllerIT extends ApiTestSupport {

    @Test
    void login_ok_returnsTokens() throws Exception {
        mockMvc.perform(
                        post("/auth/login")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"email\":\"user1@example.com\",\"password\":\"P@ssw0rd!\"}")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken").isString())
                .andExpect(jsonPath("$.refreshToken").isString());
    }

    @Test
    void login_wrongPassword_returns401() throws Exception {
        mockMvc.perform(
                        post("/auth/login")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"email\":\"user1@example.com\",\"password\":\"wrong\"}")
                )
                .andExpect(status().isUnauthorized());
    }

    @Test
    void refresh_ok_returnsNewAccessToken() throws Exception {
        String refreshToken = loginAndGetRefreshToken("user1@example.com", "P@ssw0rd!");

        mockMvc.perform(
                        post("/auth/refresh")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"refreshToken\":\"" + refreshToken + "\"}")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken").isString())
                .andExpect(jsonPath("$.refreshToken").isString());
    }

    @Test
    void refresh_unknownToken_returns401() throws Exception {
        mockMvc.perform(
                        post("/auth/refresh")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"refreshToken\":\"not-a-token\"}")
                )
                .andExpect(status().isUnauthorized());
    }

    @Test
    void logout_thenRefresh_returns401() throws Exception {
        String refreshToken = loginAndGetRefreshToken("user1@example.com", "P@ssw0rd!");

        mockMvc.perform(
                        post("/auth/logout")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"refreshToken\":\"" + refreshToken + "\"}")
                )
                .andExpect(status().isNoContent());

        mockMvc.perform(
                        post("/auth/refresh")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"refreshToken\":\"" + refreshToken + "\"}")
                )
                .andExpect(status().isUnauthorized());
    }

    private String loginAndGetRefreshToken(String email, String password) throws Exception {
        MvcResult res = mockMvc.perform(
                        post("/auth/login")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"email\":\"" + email + "\",\"password\":\"" + password + "\"}")
                )
                .andExpect(status().isOk())
                .andReturn();

        JsonNode body = objectMapper.readTree(res.getResponse().getContentAsString());
        String refreshToken = body.get("refreshToken").asText();
        assertThat(refreshToken).isNotBlank();
        return refreshToken;
    }
}
