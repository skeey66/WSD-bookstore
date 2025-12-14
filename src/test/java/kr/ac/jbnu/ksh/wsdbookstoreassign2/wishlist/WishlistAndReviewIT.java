package kr.ac.jbnu.ksh.wsdbookstoreassign2.wishlist;

import com.fasterxml.jackson.databind.JsonNode;
import kr.ac.jbnu.ksh.wsdbookstoreassign2.support.ApiTestSupport;
import kr.ac.jbnu.ksh.wsdbookstoreassign2.user.domain.Role;
import kr.ac.jbnu.ksh.wsdbookstoreassign2.user.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class WishlistAndReviewIT extends ApiTestSupport {

    @Test
    void wishlist_addThenIsWished_true() throws Exception {
        mockMvc.perform(post("/wishlist/me/" + book.getId())
                        .header("Authorization", bearerFor(user)))
                .andExpect(status().isOk());

        mockMvc.perform(get("/wishlist/me/" + book.getId())
                        .header("Authorization", bearerFor(user)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.wished").value(true));
    }

    @Test
    void wishlist_duplicateAdd_conflict409() throws Exception {
        mockMvc.perform(post("/wishlist/me/" + book.getId())
                        .header("Authorization", bearerFor(user)))
                .andExpect(status().isOk());

        mockMvc.perform(post("/wishlist/me/" + book.getId())
                        .header("Authorization", bearerFor(user)))
                .andExpect(status().isConflict());
    }

    @Test
    void review_createThenList_ok() throws Exception {
        mockMvc.perform(post("/books/" + book.getId() + "/reviews")
                        .header("Authorization", bearerFor(user))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"rating\":5,\"content\":\"good\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.bookId").value(book.getId()))
                .andExpect(jsonPath("$.userId").value(user.getId()));

        mockMvc.perform(get("/books/" + book.getId() + "/reviews?page=0&size=10")
                        .header("Authorization", bearerFor(user)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()").value(1));
    }

    @Test
    void reviewLike_duplicate_conflict409() throws Exception {
        Long reviewId = createReviewAndGetId(user);

        mockMvc.perform(post("/reviews/" + reviewId + "/likes")
                        .header("Authorization", bearerFor(user)))
                .andExpect(status().isOk());

        mockMvc.perform(post("/reviews/" + reviewId + "/likes")
                        .header("Authorization", bearerFor(user)))
                .andExpect(status().isConflict());
    }

    @Test
    void reviewUpdate_nonOwner_forbidden403() throws Exception {
        Long reviewId = createReviewAndGetId(user);
        User other = createUser("other@example.com", Role.USER);

        mockMvc.perform(patch("/reviews/" + reviewId)
                        .header("Authorization", bearerFor(other))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"rating\":4,\"content\":\"edit\"}"))
                .andExpect(status().isForbidden());
    }

    private Long createReviewAndGetId(User author) throws Exception {
        MvcResult res = mockMvc.perform(post("/books/" + book.getId() + "/reviews")
                        .header("Authorization", bearerFor(author))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"rating\":5,\"content\":\"good\"}"))
                .andExpect(status().isOk())
                .andReturn();

        JsonNode body = objectMapper.readTree(res.getResponse().getContentAsString());
        Long id = body.get("id").asLong();
        assertThat(id).isNotNull();
        return id;
    }
}
