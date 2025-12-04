package com.mcdona22.pheonix.features.app_user.presentation;

import com.mcdona22.pheonix.common.TestDatabaseSetup;
import com.mcdona22.pheonix.features.app_user.AppUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
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
    final String baseUrl = "/users";
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private CreateAppUserRequest dto;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private TestDatabaseSetup testDatabaseSetup;


    @BeforeEach
    public void setup() throws SQLException {
        dto = new CreateAppUserRequest(displayName, email, photoURL);

        testDatabaseSetup.deleteAllUsers();

    }

    @Test
    @DisplayName("Happy Path:  Create user with all fields specified")
    public void testCreateUserWithAllFields() throws Exception {
        // setup

        // act
        var result = mockMvc
                .perform(MockMvcRequestBuilders
                                 .post(baseUrl)
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
    @DisplayName("Happy Path:  Create user with photoUrl Missing")
    public void testCreateUserWithPhotoUrlMissing() throws Exception {
        // setup
        dto = CreateAppUserRequest.builder().displayName(displayName).email(email).build();
        // act
        var result = mockMvc
                .perform(MockMvcRequestBuilders
                                 .post(baseUrl)
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

    @Test
    @DisplayName("Happy Path: User Id not found")
    public void testUserNotFound() throws Exception {
        // setup - app-user is empty at this point
        final var id = "test-id";
        final var path = baseUrl + id;
        // act
        var result = mockMvc
                .perform(MockMvcRequestBuilders
                                 .get(path)
                                 .accept(MediaType.APPLICATION_JSON));

        // compare
        result.andExpect(status().isNotFound());

    }

    @Test
    @DisplayName("Happy Path: User exists and is retrieved")
    public void testFetchWhenUserFound() throws Exception {
        // setup
        final var id = "test-id";
//        final var details = new CreateAppUserRequest(displayName, email, photoURL);
        testDatabaseSetup.createAppUser(id, dto.displayName(), dto.email(),
                                        dto.photoURL()
                                       );
        final var path = baseUrl + "/" + id;
        // act

        var result = mockMvc
                .perform(MockMvcRequestBuilders
                                 .get(path)
                                 .accept(MediaType.APPLICATION_JSON));


        // compare
        result.andExpect(status().isOk());
        result.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.id")
                                              .value(id));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.displayName")
                                              .value(dto.displayName()));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.email")
                                              .value(dto.email()));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.photoURL")
                                              .value(dto.photoURL()));

    }

    private static Stream<Arguments> testArguments() {
        return Stream.of(
                arguments("No app users", List.of()),
                arguments("One app users", List.of("test-id")),
                arguments("Some app users", List.of("test-id-1", "test-id-2", "test-id-3"))
                        );
    }

    @ParameterizedTest(name = "Happy Path: Get all users scenario: {0}")
    @MethodSource("testArguments")
    public void testFetchAllUsersWhenNone(String name, List<String> idList) throws Exception {
        // setup
        logger.info("Scenario: {}, Data: {}", name, idList);
        idList.forEach(id -> {
            testDatabaseSetup.createAppUser(
                    id, dto.displayName(),
                    dto.email(),
                    dto.photoURL()
                                           );
        });
        // act
        var resultActions = mockMvc
                .perform(MockMvcRequestBuilders
                                 .get(baseUrl)
                                 .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andDo(print());

        var result = resultActions.andReturn();

        String jsonResponse = result.getResponse().getContentAsString();
        List<AppUser> actualUsers = objectMapper.readValue(
                jsonResponse,
                new TypeReference<List<AppUser>>() {
                } // TypeReference for List<AppUser>
                                                          );

        // compare


        assertEquals(result.getResponse().getStatus(), HttpStatus.OK.value());
        assertEquals(result.getResponse().getContentType(), MediaType.APPLICATION_JSON.toString());
        assertEquals(actualUsers.size(), idList.size());
        // we know we have the correct count - lets check the ids against the expected set
        for (String id : idList) {
            Optional<AppUser>
                    found = actualUsers.stream().filter(u -> u.getId().equals(id)).findFirst();
            assertTrue(found.isPresent(), "User " + id + " not found");
        }
    }
}
