package com.mcdona22.pheonix.features.app_user;


import com.mcdona22.pheonix.common.ApiError;
import com.mcdona22.pheonix.common.PheonixRuntimeException;
import com.mcdona22.pheonix.features.app_user.presentation.CreateAppUserRequest;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.function.Supplier;


@RequiredArgsConstructor
@Service
public class AppUserService {
    private final AppUserRepository appUserRepository;
    private final Supplier<String> entityIdSupplier;
    Logger logger = LoggerFactory.getLogger(this.getClass());

    public AppUser createUser(CreateAppUserRequest dto) {
        final var id = entityIdSupplier.get();
        logger.info("User ID is {}", id);

        var appUser = new AppUser(id, dto.displayName(), dto.email(), dto.photoURL());
        try {
            logger.info("Creating {}", appUser);

            appUserRepository.save(appUser);
            return appUser;
        } catch (Exception e) {
            logger.info("In exception.  Logging only.  Could not create app user {}", id);
            var message = "There was a problem saving " + appUser + " - " + e.getMessage();
            var error = new ApiError(HttpStatus.BAD_REQUEST, "App User Save Error", message);

            throw new PheonixRuntimeException(error);
        }
    }

    public Optional<AppUser> getUser(String userId) {
        logger.info("Search for app-user with id {}", userId);

        return appUserRepository.findById(userId);
    }
}
