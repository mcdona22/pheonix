package com.mcdona22.pheonix.features.app_user;

import com.mcdona22.pheonix.common.PheonixRuntimeException;
import com.mcdona22.pheonix.features.app_user.presentation.CreateAppUserRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

import java.util.Optional;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@Tag("unit")
@ExtendWith(MockitoExtension.class)
public class AppUserServiceTest {
    final String expectedId = "ee33-eHHD-dsx90";
    Logger logger = LoggerFactory.getLogger(this.getClass());
    private AppUserService appUserService;
    private CreateAppUserRequest dto;
    @Mock
    private Supplier<String> mockIdSupplier;
    @Mock
    private AppUserRepository mockRepository;

    @BeforeEach
    void setUp() {
        appUserService = new AppUserService(mockRepository, mockIdSupplier);
        dto = new CreateAppUserRequest("TestDisplay", "test@test.com", "testUrl");

        lenient().when(mockIdSupplier.get()).thenReturn(expectedId);

    }

    @Test
    @DisplayName("Happy Path:  Good request returns a value")
    void shouldUseTheRepoForGoodRequest() {

        var result = appUserService.createUser(dto);

        assertNotNull(result);
        assertEquals(expectedId, result.getId());
        assertEquals(dto.displayName(), result.getDisplayName());
        assertEquals(dto.email(), result.getEmail());
        assertEquals(dto.photoURL(), result.getPhotoURL());
        verify(mockRepository, times(1)).save(any(AppUser.class));
    }

    @Test
    @DisplayName("Should wrap an exception appropriately")
    public void testCorrectExceptionThrown() {
        // setup
        final var errorMsg = "App User Save Error";
        final var debugMsg = "expected error message";
        when(mockRepository.save(any(AppUser.class))).thenThrow(new RuntimeException(debugMsg));

        // act
        final var caught =
                assertThrows(PheonixRuntimeException.class, () -> appUserService.createUser(dto));

        // compare
        assertEquals(HttpStatus.BAD_REQUEST, caught.getError().getStatus(),
                     "Found incorrect status code"
                    );
        assertEquals(errorMsg, caught.getError().getMessage());
        assertTrue(caught.getError().getDebugMessage().contains(debugMsg),
                   "Doesn't contain message as expected"
                  );
    }

    @Test
    @DisplayName("Happy Path - test when user exists")
    public void testRetrieveFoundAppUser() {
        // setup
        final var mockUser = mock(AppUser.class);
        when(mockRepository.findById(expectedId)).thenReturn(Optional.of(mockUser));
        // act
        final var result = appUserService.getUser(expectedId);
        // compare
        assertTrue(result.isPresent());
        assertEquals(result.get(), mockUser);
        verify(mockRepository, times(1)).findById(expectedId);
    }

    @Test
    @DisplayName("Happy Path - Test when user doesn't exist")
    public void testRetrieveNotFoundAppUser() {
        // setup
        when(mockRepository.findById(expectedId)).thenReturn(Optional.empty());

        // act
        final var result = appUserService.getUser(expectedId);

        // compare
        assertTrue(result.isEmpty());
        verify(mockRepository, times(1)).findById(expectedId);
    }


}
