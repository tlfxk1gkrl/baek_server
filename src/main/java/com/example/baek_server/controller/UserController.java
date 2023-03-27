package com.example.baek_server.controller;

import com.example.baek_server.entity.UserEntity;
import com.example.baek_server.service.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserRepository userRepository;

    /**
     * select all
     * @return
     */
    @GetMapping("user")
    public List<UserEntity> findAllMember() {
        return userRepository.findAll();
    }

    /**
     * create
     * @return
     */
    @PostMapping("user")
    public UserEntity signUp() {
        final UserEntity member = UserEntity.builder()
                .username("test_user@gmail.com")
                .name("test user")
                .password("1234")
                .build();
        return userRepository.save(member);
    }
}
