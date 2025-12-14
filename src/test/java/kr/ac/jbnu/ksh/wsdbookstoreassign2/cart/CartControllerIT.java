package kr.ac.jbnu.ksh.wsdbookstoreassign2.cart;

import com.fasterxml.jackson.databind.JsonNode;
import kr.ac.jbnu.ksh.wsdbookstoreassign2.support.ApiTestSupport;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CartControllerIT extends ApiTestSupport {

    @Test
    void getMyCart_empty_ok() throws Exception {
        mockMvc.perform(get("/carts/me")
                        .header("Authorization", bearerFor(user)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value(user.getId()))
                .andExpect(jsonPath("$.items").isArray())
                .andExpect(jsonPath("$.items.length()").value(0));
    }

    @Test
    void addItem_ok_returnsItem() throws Exception {
        String body = "{\"bookId\":" + book.getId() + ",\"quantity\":2}";

        mockMvc.perform(post("/carts/me/items")
                        .header("Authorization", bearerFor(user))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items.length()").value(1))
                .andExpect(jsonPath("$.items[0].bookId").value(book.getId()))
                .andExpect(jsonPath("$.items[0].quantity").value(2));
    }

    @Test
    void updateItem_ok_changesQuantity() throws Exception {
        Long itemId = addOneItemAndGetItemId();

        mockMvc.perform(put("/carts/me/items/" + itemId)
                        .header("Authorization", bearerFor(user))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"quantity\":5}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items.length()").value(1))
                .andExpect(jsonPath("$.items[0].id").value(itemId))
                .andExpect(jsonPath("$.items[0].quantity").value(5));
    }

    @Test
    void clear_ok_thenEmpty() throws Exception {
        addOneItemAndGetItemId();

        mockMvc.perform(delete("/carts/me/items")
                        .header("Authorization", bearerFor(user)))
                .andExpect(status().isOk());

        mockMvc.perform(get("/carts/me")
                        .header("Authorization", bearerFor(user)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items.length()").value(0));
    }

    private Long addOneItemAndGetItemId() throws Exception {
        String body = "{\"bookId\":" + book.getId() + ",\"quantity\":1}";

        MvcResult res = mockMvc.perform(post("/carts/me/items")
                        .header("Authorization", bearerFor(user))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk())
                .andReturn();

        JsonNode root = objectMapper.readTree(res.getResponse().getContentAsString());
        JsonNode items = root.get("items");
        assertThat(items).isNotNull();
        return items.get(0).get("id").asLong();
    }
}
