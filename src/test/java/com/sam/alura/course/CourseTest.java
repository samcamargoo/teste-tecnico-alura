package com.sam.alura.course;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CourseTest {

    private static NewCourseRequest request;
    @Autowired
    private MockMvc mockMvc;

    @BeforeAll
    static void setup() {
        request = new NewCourseRequest("Spring Boot", "javac", 2L, "java with spring boot");
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @Order(1)
    void shouldCreateCourse() throws Exception {
        var json = new ObjectMapper().writeValueAsString(request);

        mockMvc.perform(MockMvcRequestBuilders.post("/admin/courses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @Order(2)
    void shouldNotCreateCourseWithSameCode() throws Exception {
        var json = new ObjectMapper().writeValueAsString(request);

        mockMvc.perform(MockMvcRequestBuilders.post("/admin/courses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @Order(3)
    void shouldNotCreateCourseIfUserIsNotInstructor() throws Exception {
        NewCourseRequest notInstructor = new NewCourseRequest("Spring Boot", "java-test", 1L, "java with spring boot");

        var json = new ObjectMapper().writeValueAsString(notInstructor);

        mockMvc.perform(MockMvcRequestBuilders.post("/admin/courses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @Order(4)
    void shouldDisableCourse() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.patch("/admin/courses/disable/1"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @Order(5)
    void shouldNotDisableNonExistingCourse() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.patch("/admin/courses/disable/2"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @Order(6)
    void shouldListCoursesPaginatedByStatus() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/admin/courses?status=inactive"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.pageable").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].name").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].code").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].instructor").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].description").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].status").exists());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @Order(7)
    void shouldListCoursesPaginatedWithoutSendingStatus() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/admin/courses"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.pageable").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].name").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].code").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].instructor").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].description").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].status").exists());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"STUDENT"})
    @Order(8)
    void shouldNotCreateCourseIfUserIsLoggedUserIsNotAdmin() throws Exception {
        NewCourseRequest notInstructor = new NewCourseRequest("Spring Boot", "NEW COURSE", 1L, "java with spring boot");

        var json = new ObjectMapper().writeValueAsString(notInstructor);

        mockMvc.perform(MockMvcRequestBuilders.post("/admin/courses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @Order(9)
    void shouldNotCreateCourseIfCodeIsInvalid() throws Exception {
        NewCourseRequest invalidRequest = new NewCourseRequest("Spring Boot", "123", 2L, "java with spring boot");

        var json = new ObjectMapper().writeValueAsString(invalidRequest);

        mockMvc.perform(MockMvcRequestBuilders.post("/admin/courses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.fieldErrors[0].field").exists());
    }
}
