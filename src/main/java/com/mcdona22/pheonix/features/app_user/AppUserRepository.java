package com.mcdona22.pheonix.features.app_user;

import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppUserRepository extends JpaRepository<@NonNull AppUser, @NonNull String> {
//    AppUser findByDisplayName(String displayName);
//
//    @NonNull
//    Optional<AppUser> findByID(String id);
}
