package kr.ac.jbnu.ksh.wsdbookstoreassign2.book;

import kr.ac.jbnu.ksh.wsdbookstoreassign2.support.ApiTestSupport;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class BookControllerIT extends ApiTestSupport {

    @Test
    void list_public_ok() throws Exception {
        mockMvc.perform(get("/books?page=0&size=10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray());
    }

    @Test
    void create_admin_created() throws Exception {
        String body = """
                {
                  "isbn": "ISBN-NEW-1",
                  "publisher": "Pub",
                  "title": "New Book",
                  "summary": "Summary",
                  "price": 12000,
                  "publicationYear": 2025
                }
                """;

        mockMvc.perform(post("/books")
                        .header("Authorization", bearerFor(admin))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.isbn").value("ISBN-NEW-1"));
    }

    @Test
    void create_user_forbidden() throws Exception {
        String body = """
                {
                  "isbn": "ISBN-NEW-2",
                  "publisher": "Pub",
                  "title": "New Book",
                  "summary": "Summary",
                  "price": 12000,
                  "publicationYear": 2025
                }
                """;

        mockMvc.perform(post("/books")
                        .header("Authorization", bearerFor(user))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isForbidden());
    }

    @Test
    void create_duplicateIsbn_conflict() throws Exception {
        String body = """
                {
                  "isbn": "ISBN-0001",
                  "publisher": "Pub",
                  "title": "Dup",
                  "summary": "Summary",
                  "price": 12000,
                  "publicationYear": 2025
                }
                """;

        mockMvc.perform(post("/books")
                        .header("Authorization", bearerFor(admin))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isConflict());
    }

    @Test
    void get_notFound_404() throws Exception {
        mockMvc.perform(get("/books/999999"))
                .andExpect(status().isNotFound());
    }
}
