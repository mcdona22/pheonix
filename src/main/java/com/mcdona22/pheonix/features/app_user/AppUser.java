package com.mcdona22.pheonix.features.app_user;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Entity
//@Table(name = "app_user")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppUser {
    @Id
    @NonNull
    private String id;
    @NonNull
    private String displayName;
    @NonNull
    private String email;
    private String photoURL;

}