package com.mcdona22.pheonix.features.app_user.presentation;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import tools.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Tag("integration")
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class AppUserControllerIntegrationTest {
    final String
            email = "mcdona22@gmail.com",
            displayName = "Test User",
            photoURL = "www.test-pic.com";
    final String postUrl = "/users";
    private CreateAppUserRequest dto;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Happy Path:  Create user with all fields specified")
    public void testCreateUserWithAllFields() throws Exception {
        // setup
        dto = new CreateAppUserRequest(displayName, email, photoURL);

        // act
        var result = mockMvc
                .perform(MockMvcRequestBuilders
                                 .post(postUrl)
                                 .content(objectMapper.writeValueAsString(dto))
                                 .contentType(MediaType.APPLICATION_JSON)
                                 .accept(MediaType.APPLICATION_JSON));


        // compare
        result.andExpect(status().isCreated());
        result.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.displayName")
                                              .value(displayName));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.email")
                                              .value(email));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.photoURL")
                                              .value(photoURL));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.id")
                                              .exists());


    }

    @Test
    @DisplayName("Happy Path:  Create user with photoUrlMissing")
    public void testCreateUserWithPhotoUrlMissing() throws Exception {
        // setup
//        dto = new CreateAppUserRequest(displayName, email);
        dto = CreateAppUserRequest.builder().displayName(displayName).email(email).build();
        // act
        var result = mockMvc
                .perform(MockMvcRequestBuilders
                                 .post(postUrl)
                                 .content(objectMapper.writeValueAsString(dto))
                                 .contentType(MediaType.APPLICATION_JSON)
                                 .accept(MediaType.APPLICATION_JSON));


        // compare
        result.andExpect(status().isCreated());
        result.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.displayName")
                                              .value(displayName));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.email")
                                              .value(email));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.photoURL")
                                              .value(""));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.id")
                                              .exists());
    }
}
