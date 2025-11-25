package com.mcdona22.pheonix.features.app_user;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@RequiredArgsConstructor
@Service
public class AppUserService {
    private final AppUserRepository appUserRepository;

    @Transactional
    public void createUser() {
        var appUser = new AppUser("john", "mcdona22@gmail.com");
        try {
            System.out.println("saving app user: " + appUser);
            appUserRepository.save(appUser);
            System.out.println("This looked successful");

        } catch (Exception e) {
            System.out.println("Error saving app user: " + e.getMessage());
        }
    }
}
