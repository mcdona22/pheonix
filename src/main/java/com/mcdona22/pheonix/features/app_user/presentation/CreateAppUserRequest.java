package com.mcdona22.pheonix.features.app_user.presentation;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record CreateAppUserRequest(
        @NotBlank(message = "Display name is required")
        String displayName,

        @NotBlank(message = "Email is required")
        @Email(message = "Must be a valid email address")
        String email,

        String photoURL
) {
}
