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
public class UserControllerTest {

    public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(roles={"USER", "ADMIN"})
    public void testGetOneExist() throws Exception {
        mockMvc.perform(get("/users/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("user.simple"))
                .andReturn();
    }

    @Test
    @WithMockUser(roles={"USER", "ADMIN"})
    public void testGetOneNotExist() throws Exception {
        mockMvc.perform(get("/users/3"))
                .andDo(print())
                .andExpect(status().is5xxServerError())
                .andExpect(jsonPath("$.message").value("User doesn't exist!"))
                .andReturn();
    }

    @Test
    @WithMockUser(roles={"USER", "ADMIN"})
    public void testGetAll() throws Exception {
        mockMvc.perform(get("/users"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("user.simple"))
                .andExpect(jsonPath("$[1].name").value("user.admin"))
                .andReturn();
    }

    @Test
    @WithMockUser(roles={"USER", "ADMIN"})
    public void testPutOneBadRequest() throws Exception {
        mockMvc.perform(put("/users/1").contentType(APPLICATION_JSON_UTF8).content("{\"id\":2,\"name\":\"user.simple1\",\"password\":\"1111\",\"roleIds\":[1,2]}"))
                .andDo(print())
                .andExpect(status().is5xxServerError())
                .andExpect(jsonPath("$.message").value("Url param id is not equals to userId!"))
                .andReturn();
    }

    @Test
    @WithMockUser(roles={"USER", "ADMIN"})
    public void testPutOneExistBadRequest() throws Exception {
        mockMvc.perform(put("/users/1").contentType(APPLICATION_JSON_UTF8).content("{\"id\":1,\"name\":\"user.admin\",\"password\":\"1111\"}"))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.message").value("User's Role must not be null!;"))
                .andReturn();
    }

    @Test
    @WithMockUser(roles={"USER", "ADMIN"})
    public void testPutOneExist() throws Exception {
        mockMvc.perform(put("/users/1").contentType(APPLICATION_JSON_UTF8).content("{\"id\":1,\"name\":\"user.admin\",\"password\":\"1111\",\"roleIds\":[1,2]}"))
                .andDo(print())
                .andExpect(status().is5xxServerError())
                .andExpect(jsonPath("$.message").value("User name is not unique!"))
                .andReturn();
    }

    @Test
    @WithMockUser(roles={"USER", "ADMIN"})
    public void testPutOneNotExist() throws Exception {
        mockMvc.perform(put("/users/3").contentType(APPLICATION_JSON_UTF8).content("{\"id\":3,\"name\":\"user.test\",\"password\":\"1111\",\"roleIds\":[1,2]}"))
                .andDo(print())
                .andExpect(status().is5xxServerError())
                .andExpect(jsonPath("$.message").value("User doesn't exist!"))
                .andReturn();
    }

    @Test
    @WithMockUser(roles={"USER", "ADMIN"})
    public void testSaveBadRequest() throws Exception {
        mockMvc.perform(post("/users").contentType(APPLICATION_JSON_UTF8).content("{\"name\":\"user.simple\",\"password\":\"1111\"}"))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.message").value("User's Role must not be null!;"))
                .andReturn();
    }

    @Test
    @WithMockUser(roles={"USER", "ADMIN"})
    public void testSaveExistBadRequest() throws Exception {
        mockMvc.perform(post("/users").contentType(APPLICATION_JSON_UTF8).content("{\"name\":\"user.simple\",\"password\":\"1111\",\"roleIds\":[1,2]}"))
                .andDo(print())
                .andExpect(status().is5xxServerError())
                .andExpect(jsonPath("$.message").value("User name is not unique!"))
                .andReturn();
    }

    @Test
    @WithMockUser(roles={"USER", "ADMIN"})
    public void testSaveNotExist() throws Exception {
        mockMvc.perform(post("/users").contentType(APPLICATION_JSON_UTF8).content("{\"name\":\"user.test\",\"password\":\"1111\",\"roleIds\":[1,2]}"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("user.test"))
                .andReturn();
    }


    @Test
    @WithMockUser(roles={"USER", "ADMIN"})
    public void testDeleteExist() throws Exception {
        mockMvc.perform(get("/users/2"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    @WithMockUser(roles={"USER", "ADMIN"})
    public void testDeleteNotExist() throws Exception {
        mockMvc.perform(delete("/users/3"))
                .andDo(print())
                .andExpect(status().is5xxServerError())
                .andExpect(jsonPath("$.message").value("User doesn't exist!"))
                .andReturn();
    }
}
