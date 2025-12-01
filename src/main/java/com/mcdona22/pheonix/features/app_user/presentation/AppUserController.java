package com.mcdona22.pheonix.features.app_user.presentation;

import com.mcdona22.pheonix.features.app_user.AppUser;
import com.mcdona22.pheonix.features.app_user.AppUserService;
import jakarta.validation.Valid;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class AppUserController {
    private final AppUserService appUserService;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    @PostMapping
    ResponseEntity<@NonNull AppUser> createUser(@Valid @RequestBody CreateAppUserRequest request) {
        logger.info("Received request to create app user: {}", request);
        final var user = appUserService.createUser(request);
        logger.info("App user created: {}", user);


        final URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                                                        .buildAndExpand(user.getId()).toUri();
        return ResponseEntity.created(location).body(user);

    }

    @GetMapping("/{id}")
    public ResponseEntity<@NonNull AppUser> getUserById(@PathVariable("id") String id) {
        logger.info("Processing request to get app user by id: {}", id);

        final URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                                                        .buildAndExpand(id).toUri();

        Optional<AppUser> optionalUser = appUserService.getUser(id);
        
        return optionalUser.map(ResponseEntity::ok)
                           .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
