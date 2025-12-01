package com.mcdona22.pheonix.features.app_user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.io.Serializable;

@Entity
@Table(name = "APP_USER")
@Getter // Use explicit Getter
@Setter // Use explicit Setter
@ToString // Safe to include all fields in toString
@NoArgsConstructor // Required by JPA
@AllArgsConstructor // For convenience
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class AppUser implements Serializable {
    @Id
    @Column(name = "ID")
    @EqualsAndHashCode.Include
    @NonNull
    private String id;
    @NonNull
    private String displayName;
    @NonNull
    private String email;
    private String photoURL;

}