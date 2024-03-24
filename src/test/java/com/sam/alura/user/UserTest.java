package com.sam.alura.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserTest {

    @Autowired
    private MockMvc mockMvc;

    private static NewUserRequest request;

    @BeforeAll()
    public static void setup() {
        request = new NewUserRequest("samuel", "samcamargo", "sam-camargo@live.com", "123");
    }

    @Test
    @DisplayName("Should create user")
    @Order(1)
    void shouldCreateUser() throws Exception {

        var json = new ObjectMapper().writeValueAsString(request);

        mockMvc.perform(MockMvcRequestBuilders.post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)).andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    @DisplayName("Should not create user")
    @Order(2)
    void shouldNotCreateUser() throws Exception {

        var json = new ObjectMapper().writeValueAsString(request);

        mockMvc.perform(MockMvcRequestBuilders.post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)).andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @DisplayName("Should not create user invalid email")
    @Order(3)
    void shouldNotCreateUserInvalidEmail() throws Exception {
        NewUserRequest invalidUser =   new NewUserRequest("samuel", "camargo", "sam-camargolive.com", "123");
        var json = new ObjectMapper().writeValueAsString(invalidUser);

        mockMvc.perform(MockMvcRequestBuilders.post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)).andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
}
