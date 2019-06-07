package com.it.app.integration.controller;

import com.it.app.config.DatabaseConfiguration;
import com.it.app.integration.configuration.TestWebConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.AnnotationConfigWebContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.Charset;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(loader = AnnotationConfigWebContextLoader.class, classes = {TestWebConfiguration.class, DatabaseConfiguration.class})
@WebAppConfiguration
@Transactional
public class UserControllerTest {

    public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void testGetOneExist() throws Exception {
        mockMvc.perform(get("/users/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("user.simple"))
                .andReturn();
    }

    @Test
    public void testGetOneNotExist() throws Exception {
        mockMvc.perform(get("/users/999"))
                .andDo(print())
                .andExpect(status().is5xxServerError())
                .andExpect(jsonPath("$.message").value("User doesn't exist!"))
                .andReturn();
    }

    @Test
    public void testGetAll() throws Exception {
        mockMvc.perform(get("/users"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("user.simple"))
                .andExpect(jsonPath("$[1].name").value("user.admin"))
                .andReturn();
    }

    @Test
    public void testPutOneBadRequest() throws Exception {
        mockMvc.perform(put("/users/1").contentType(APPLICATION_JSON_UTF8).content("{\"id\":2,\"name\":\"user.simple1\",\"password\":\"1111\",\"roleIds\":[1,2]}"))
                .andDo(print())
                .andExpect(status().is5xxServerError())
                .andExpect(jsonPath("$.message").value("Url param id is not equals to userId!"))
                .andReturn();
    }

    @Test
    public void testPutOneExistBadRequest() throws Exception {
        mockMvc.perform(put("/users/1").contentType(APPLICATION_JSON_UTF8).content("{\"id\":1,\"name\":\"user.admin\",\"password\":\"1111\"}"))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.message").value("User's Role must not be null!;"))
                .andReturn();
    }

    @Test
    public void testPutOneExist() throws Exception {
        mockMvc.perform(put("/users/1").contentType(APPLICATION_JSON_UTF8).content("{\"id\":1,\"name\":\"user.admin\",\"password\":\"1111\",\"roleIds\":[1,2]}"))
                .andDo(print())
                .andExpect(status().is5xxServerError())
                .andExpect(jsonPath("$.message").value("User name is not unique!"))
                .andReturn();
    }

    @Test
    public void testPutOneNotExist() throws Exception {
        mockMvc.perform(put("/users/3").contentType(APPLICATION_JSON_UTF8).content("{\"id\":3,\"name\":\"user.test\",\"password\":\"1111\",\"roleIds\":[1,2]}"))
                .andDo(print())
                .andExpect(status().is5xxServerError())
                .andExpect(jsonPath("$.message").value("User doesn't exist!"))
                .andReturn();
    }

    @Test
    public void testSaveBadRequest() throws Exception {
        mockMvc.perform(post("/users").contentType(APPLICATION_JSON_UTF8).content("{\"name\":\"user.simple\",\"password\":\"1111\"}"))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.message").value("User's Role must not be null!;"))
                .andReturn();
    }

    @Test
    public void testSaveExistBadRequest() throws Exception {
        mockMvc.perform(post("/users").contentType(APPLICATION_JSON_UTF8).content("{\"name\":\"user.simple\",\"password\":\"1111\",\"roleIds\":[1,2]}"))
                .andDo(print())
                .andExpect(status().is5xxServerError())
                .andExpect(jsonPath("$.message").value("User name is not unique!"))
                .andReturn();
    }

    @Test
    public void testSaveNotExist() throws Exception {
        mockMvc.perform(post("/users").contentType(APPLICATION_JSON_UTF8).content("{\"name\":\"user.test\",\"password\":\"1111\",\"roleIds\":[1,2]}"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("user.test"))
                .andReturn();
    }


    @Test
    public void testDeleteExist() throws Exception {
        mockMvc.perform(get("/users/2"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void testDeleteNotExist() throws Exception {
        mockMvc.perform(delete("/users/3"))
                .andDo(print())
                .andExpect(status().is5xxServerError())
                .andExpect(jsonPath("$.message").value("User doesn't exist!"))
                .andReturn();
    }
}
