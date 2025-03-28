package com.java5.demoJV5.entity;


import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    @Column(name = "user_id", nullable = false)
    private Integer id;

    @Column(name = "password", nullable = false, length = 100)
    private String password;

    @Column(name = "name", nullable = false, columnDefinition = "NVARCHAR(50)")
    private String name;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "address", nullable = false, columnDefinition = "NVARCHAR(250)")
    private String address;

    @Column(name = "role", nullable = false)
    private Integer role;

    @Column(name = "status", nullable = false)
    private Boolean status;
    
    @Column(name = "reset_token", length = 255)
    private String resetToken;

    @Column(name = "otp_expiry")
    private LocalDateTime otpExpiry;
    
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private CartEntity cart;
    

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<FavoriteEntity> favorites;
}
