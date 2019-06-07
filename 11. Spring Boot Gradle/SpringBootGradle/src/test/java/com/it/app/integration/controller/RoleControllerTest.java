package com.it.app.integration.controller;

import com.it.app.Main;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.Charset;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = Main.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
public class RoleControllerTest {

    public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(roles={"USER", "ADMIN"})
    public void testGetOneExist() throws Exception {
        mockMvc.perform(get("/roles/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("ROLE_USER"))
                .andReturn();
    }

    @Test
    @WithMockUser(roles={"USER", "ADMIN"})
    public void testGetOneNotExist() throws Exception {
        mockMvc.perform(get("/roles/3"))
                .andDo(print())
                .andExpect(status().is5xxServerError())
                .andExpect(jsonPath("$.message").value("Role doesn't exist!"))
                .andReturn();
    }

    @Test
    @WithMockUser(roles={"USER", "ADMIN"})
    public void testGetAll() throws Exception {
        mockMvc.perform(get("/roles"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("ROLE_USER"))
                .andExpect(jsonPath("$[1].name").value("ROLE_ADMIN"))
                .andReturn();
    }

    @Test
    @WithMockUser(roles={"USER", "ADMIN"})
    public void testPutOneBadRequest() throws Exception {
        mockMvc.perform(put("/roles/1").contentType(APPLICATION_JSON_UTF8).content("{\"id\":2,\"name\":\"user1\"}"))
                .andDo(print())
                .andExpect(status().is5xxServerError())
                .andExpect(jsonPath("$.message").value("Url param id is not equals to roleId!"))
                .andReturn();
    }

    @Test
    @WithMockUser(roles={"USER", "ADMIN"})
    public void testPutOneExistBadRequest() throws Exception {
        mockMvc.perform(put("/roles/1").contentType(APPLICATION_JSON_UTF8).content("{\"id\":1,\"name\":\"ROLE_ADMIN\"}"))
                .andDo(print())
                .andExpect(status().is5xxServerError())
                .andExpect(jsonPath("$.message").value("Role name is not unique!"))
                .andReturn();
    }

    @Test
    @WithMockUser(roles={"USER", "ADMIN"})
    public void testPutOneExist() throws Exception {
        mockMvc.perform(put("/roles/2").contentType(APPLICATION_JSON_UTF8).content("{\"id\":2,\"name\":\"ROLE_USER\"}"))
                .andDo(print())
                .andExpect(status().is5xxServerError())
                .andExpect(jsonPath("$.message").value("Role name is not unique!"))
                .andReturn();
    }

    @Test
    @WithMockUser(roles={"USER", "ADMIN"})
    public void testPutOneNotExist() throws Exception {
        mockMvc.perform(put("/roles/3").contentType(APPLICATION_JSON_UTF8).content("{\"id\":3,\"name\":\"user1\"}"))
                .andDo(print())
                .andExpect(status().is5xxServerError())
                .andExpect(jsonPath("$.message").value("Role doesn't exist!"))
                .andReturn();
    }

    @Test
    @WithMockUser(roles={"USER", "ADMIN"})
    public void testSaveExistBadRequest() throws Exception {
        mockMvc.perform(post("/roles").contentType(APPLICATION_JSON_UTF8).content("{\"name\":\"ROLE_ADMIN\"}"))
                .andDo(print())
                .andExpect(status().is5xxServerError())
                .andExpect(jsonPath("$.message").value("Role name is not unique!"))
                .andReturn();
    }

    @Test
    @WithMockUser(roles={"USER", "ADMIN"})
    public void testSaveNotExist() throws Exception {
        mockMvc.perform(post("/roles").contentType(APPLICATION_JSON_UTF8).content("{\"name\":\"user2\"}"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("user2"))
                .andReturn();
    }

    @Test
    @WithMockUser(roles={"USER", "ADMIN"})
    public void testDeleteExist() throws Exception {
        mockMvc.perform(get("/roles/2"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    @WithMockUser(roles={"USER", "ADMIN"})
    public void testDeleteNotExist() throws Exception {
        mockMvc.perform(delete("/roles/3"))
                .andDo(print())
                .andExpect(status().is5xxServerError())
                .andExpect(jsonPath("$.message").value("Role doesn't exist!"))
                .andReturn();
    }
}
