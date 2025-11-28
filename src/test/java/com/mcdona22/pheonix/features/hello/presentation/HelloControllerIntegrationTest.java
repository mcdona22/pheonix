package com.mcdona22.pheonix.features.hello.presentation;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Tag("integration")
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class HelloControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private HelloController controller;

    @Test
    public void shouldCreateTheController() throws Exception {
        final var service = ReflectionTestUtils.getField(controller, "helloService");

        assertNotNull(service, "The service should be injected by Spring");

//        final var expected = "Hello from Boot";
//        final var response = controller.helloService.getHelloResponse();
//        assertEquals(expected, response.message(), "This isn't right");

    }

    @Test
    public void helloWithVersion() throws Exception {
        final var expectedMessage = "Hello from Boot";
        final var expectedVersion = "1.0";
        var result =
                mockMvc.perform(MockMvcRequestBuilders.get("/").accept(MediaType.APPLICATION_JSON));
        result.andExpect(status().isOk());
        result.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.message")
                                              .value(expectedMessage));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.version").value(expectedVersion));
    }

    @Test
    public void checkHealthEndPoint() throws Exception {
        var path = "/actuator/health";

        mockMvc.perform(MockMvcRequestBuilders.get(path).accept(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk());

    }
}