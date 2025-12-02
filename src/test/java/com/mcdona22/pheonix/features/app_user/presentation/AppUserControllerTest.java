package com.mcdona22.pheonix.features.app_user.presentation;

import com.mcdona22.pheonix.features.app_user.AppUser;
import com.mcdona22.pheonix.features.app_user.AppUserService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.net.URI;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@Tag("unit")
@ExtendWith(MockitoExtension.class)
public class AppUserControllerTest {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    CreateAppUserRequest dto;
    AppUserController controller;
    @Mock
    private AppUser mockUser;
    @Mock
    private AppUserService mockService;

    @BeforeAll()
    static void setupAll() {
        MockHttpServletRequest mockRequest = new MockHttpServletRequest();
        mockRequest.setRequestURI("/users");
        mockRequest.setScheme("http");
        mockRequest.setServerName("localhost");
        mockRequest.setServerPort(8080);

        // 2. Bind the mock request to the RequestContextHolder.
        // This makes the static method 'fromCurrentRequest()' work in a unit test.
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(mockRequest));
    }


    @BeforeEach
    void setUp() {
        controller = new AppUserController(mockService);
        dto = new CreateAppUserRequest("TestDisplay", "test@test.com", "testUrl");

    }

    @Test
    @DisplayName("Happy Path: User creation with controller")
    public void testUserCreationHappyPath() {
        // setup
        final var expectedId = "ds88sd2HJ2-BB12";
        final URI expectedUri = URI.create("http://localhost:8080/users/" + expectedId);
        final var expectedAppUser = new AppUser(expectedId, dto.displayName(), dto.email(),
                                                dto.photoURL()
        );

        when(mockService.createUser(dto)).thenReturn(expectedAppUser);
        // act
        final var result = controller.createUser(dto);
        final URI actualLocation = result.getHeaders().getLocation();
        final var foundUser = result.getBody();

        // compare
        assertNotNull(result);
        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertEquals(actualLocation, expectedUri);
        assertEquals(expectedId, Objects.requireNonNull(foundUser).getId());
        assertEquals(dto.displayName(), foundUser.getDisplayName());
        assertEquals(dto.email(), foundUser.getEmail());
        assertEquals(dto.photoURL(), foundUser.getPhotoURL());

    }

    @Test
    @DisplayName("Happy Path : Get existing user")
    public void testFetchUserWithID() {
        // setup
        final var userId = "ds8-8sd-2HJ2-BB12";
        final var expectedUser = new AppUser(userId, "Display Name", "person@test.com", "photoURL");
        when(mockService.getUser(userId)).thenReturn(Optional.of(expectedUser));
        // act
        var response = controller.getUserById(userId);

        // compare
        assertEquals(HttpStatus.OK, response.getStatusCode(), "This should be OK");
        verify(mockService, times(1)).getUser(userId);
    }


//    private Stream<Arguments> getTestUsers() {
//        List<AppUser> hasZero = List.of();
//        List<AppUser> hasOne = List.of(mock(AppUser.class));
//        List<AppUser> hasMany = List.of(
//                mock(AppUser.class),
//                mock(AppUser.class),
//                mock(AppUser.class)
//                                       );
//
//        return Stream.of(
//                Arguments.of(hasZero, 0, "Zero Users Test"),
//                Arguments.of(hasOne, 1, "One User Test"),
//                Arguments.of(hasMany, 3, "Many Users Test")
//                        );
//    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 5})
    @DisplayName("Happy Path: Find all for some, one, none users")
    public void testFindVariousNumberOfAppUsers(int itemCount) {
        // setup
        final var list = new ArrayList<AppUser>();
        for (var i = 0; i < itemCount; i++) {
            list.add(Mockito.mock(AppUser.class));
        }
        when(mockService.findAllUsers()).thenReturn(list);

        // act
        var response = controller.getAllUsers();

        // compare
        verify(mockService, times(1)).findAllUsers();
        assertEquals(HttpStatus.OK, response.getStatusCode(), "This should be OK");
        assert response.getBody() != null;
        assertEquals(itemCount, response.getBody().size());
    }


}

