package com.example.baek_server.entity;

import jakarta.persistence.*;
import lombok.*;


@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name="user") // table name
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long pid;

    @Column(nullable = false, unique = true, length = 30)
    private String username;

    @Column(nullable = false, length = 100)
    private String name;
    @Column(nullable = false)
    private String password;

    @Builder
    public UserEntity(String username, String name, String password) {
        this.username = username;
        this.name = name;
        this.password = password;
    }
}
