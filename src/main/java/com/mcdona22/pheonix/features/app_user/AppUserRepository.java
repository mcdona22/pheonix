package com.mcdona22.pheonix.features.app_user;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppUserRepository extends CrudRepository<AppUser, Long> {
    AppUser findByDisplayName(String displayName);
}
