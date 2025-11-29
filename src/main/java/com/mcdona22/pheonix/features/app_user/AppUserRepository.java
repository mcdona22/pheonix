package com.mcdona22.pheonix.features.app_user;

import lombok.NonNull;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AppUserRepository extends CrudRepository<AppUser, String> {
    AppUser findByDisplayName(String displayName);

    @NonNull
    Optional<AppUser> findById(String id);
}
