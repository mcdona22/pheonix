package com.mcdona22.pheonix.features.app_user;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class AppUserController {
    private final AppUserService appUserService;


    @GetMapping
    String index() {
        this.appUserService.createUser();
        return "here is the user endpoint";
    }
}
