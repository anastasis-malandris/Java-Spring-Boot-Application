package com.example.lesson;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
public class LessonServiceIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    // Use the postgres version you are using on production.
    // If you remove static then each test method will use a new container.
    @Container
    private static PostgreSQLContainer postgreSQLContainer = new PostgreSQLContainer("postgres:latest");

    @DynamicPropertySource
    public static void overrideProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
        registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
        registry.add("spring.datasource.driver-class-name", postgreSQLContainer::getDriverClassName);
    }

    @Test
    void addAndGetLessons() throws Exception {

        // I can get all names then there is nothing there!
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/lessons/all"))
                .andExpect(status().isOk())
                .andExpect(content().string("[]"));


        // I can add a lesson without problems
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/lessons")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"lessonTitle\": \"Lesson 1\"}"))
                .andExpect(status().isOk());

        // I can get the lesson I added in the previous step by title
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/lessons/all?title=Lesson 1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", Matchers.is(1)))
                .andExpect(jsonPath("$[0].lessonTitle").value("Lesson 1"));

        // I can get the lesson I added in the previous step by id
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/lessons/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("{\"id\":1,\"lessonTitle\":\"Lesson 1\"}"));
    }
}
