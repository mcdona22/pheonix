package com.mcdona22.pheonix.features.app_user.presentation;

import com.mcdona22.pheonix.common.ApiError;
import com.mcdona22.pheonix.common.PheonixRuntimeException;
import com.mcdona22.pheonix.features.app_user.AppUserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import tools.jackson.databind.ObjectMapper;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Tag("integration")
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class AppUserUnhappyPathIntegrationTest {
    @MockitoBean
    private AppUserService mockService;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Unhappy Path: Marshals the service error and creates correct response ")
    public void testResponseIsCorrectWithServiceError() throws Exception {
        // setup
        final String
                email = "mcdona22@gmail.com",
                displayName = "Test User",
                photoURL = "www.test-pic.com";
        final String postUrl = "/users";
        final var expectedError =
                new ApiError(HttpStatus.BAD_REQUEST, "App User Save Error", "");
        final var dto = new CreateAppUserRequest(displayName, email, photoURL);
        final var expectedMessage = "App User Save Error";
        when(mockService.createUser(dto)).thenThrow(new PheonixRuntimeException(expectedError));

        // act

        var result = mockMvc
                .perform(MockMvcRequestBuilders
                                 .post(postUrl)
                                 .content(objectMapper.writeValueAsString(dto))
                                 .contentType(MediaType.APPLICATION_JSON)
                                 .accept(MediaType.APPLICATION_JSON));

        // compare
        result.andExpect(status().isBadRequest());
        result.andExpect(
                MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.message")
                                              .value(expectedMessage));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.status")
                                              .value("400 BAD_REQUEST"));

    }


}
