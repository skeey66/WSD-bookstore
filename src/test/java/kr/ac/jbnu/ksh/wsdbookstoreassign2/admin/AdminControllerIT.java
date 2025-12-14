package kr.ac.jbnu.ksh.wsdbookstoreassign2.admin;

import kr.ac.jbnu.ksh.wsdbookstoreassign2.support.ApiTestSupport;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AdminControllerIT extends ApiTestSupport {

    @Test
    void ping_admin_ok() throws Exception {
        mockMvc.perform(get("/admin/ping")
                        .header("Authorization", bearerFor(admin)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.ok").value("admin"));
    }

    @Test
    void listUsers_admin_ok() throws Exception {
        mockMvc.perform(get("/admin/users?page=0&size=10")
                        .header("Authorization", bearerFor(admin)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content.length()").value(2));
    }

    @Test
    void listUsers_user_forbidden() throws Exception {
        mockMvc.perform(get("/admin/users?page=0&size=10")
                        .header("Authorization", bearerFor(user)))
                .andExpect(status().isForbidden());
    }

    @Test
    void updateRole_admin_ok() throws Exception {
        mockMvc.perform(patch("/admin/users/" + user.getId() + "/role")
                        .header("Authorization", bearerFor(admin))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"role\":\"ADMIN\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(user.getId()))
                .andExpect(jsonPath("$.role").value("ADMIN"));
    }

    @Test
    void deactivate_admin_ok_setsDeletedAt() throws Exception {
        mockMvc.perform(patch("/admin/users/" + user.getId() + "/deactivate")
                        .header("Authorization", bearerFor(admin)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(user.getId()))
                .andExpect(jsonPath("$.deletedAt").isNotEmpty());
    }
}
