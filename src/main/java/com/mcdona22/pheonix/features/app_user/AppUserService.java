package com.mcdona22.pheonix.features.app_user;


import com.mcdona22.pheonix.features.app_user.presentation.CreateAppUserRequest;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.function.Supplier;


@RequiredArgsConstructor
@Service
public class AppUserService {
    private final AppUserRepository appUserRepository;
    private final Supplier<String> entityIdSupplier;
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Transactional
    public AppUser createUser(CreateAppUserRequest dto) {
        try {
            throw new EntityNotFoundException("App User not created");
//            final var id = entityIdSupplier.get();
//            var appUser = new AppUser(id, dto.displayName(), dto.email(), dto.photoURL());
//            logger.info("Creating user with id {}", id);
//            appUserRepository.save(appUser);
//            return appUser;
        } catch (Exception e) {
            System.out.println("Error saving app user: " + e.getMessage());
            throw e;
        }
    }
}
