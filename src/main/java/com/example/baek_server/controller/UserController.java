package com.example.baek_server.controller;

import com.example.baek_server.entity.UserEntity;
import com.example.baek_server.mylib.BaeksLibrary;
import com.example.baek_server.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin("*")
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TokenController tokenController;

    private BaeksLibrary library = new BaeksLibrary();
    /**
     * select all
     * @return
     */
    @GetMapping("/user")
    public List<UserEntity> findAllUser() {
        return userRepository.findAll();
    }

    /**
     * login
     *
     * @return
     */
    @PostMapping("/login")
    public String login(@RequestBody UserEntity user) {
        if (userRepository.existsByUsername(user.getUsername())){
            UserEntity tempUser = userRepository.findByUsername(user.getUsername());
            if(passwordEncoder.matches(user.getPassword(),tempUser.getPassword())){
                return user.getName();
            }
        }
        return "Error";
    }

    /**
     * create
     * @return
     */
    @PostMapping("/create")
    public UserEntity createUser(@RequestBody UserEntity user) {
        String encodePw = passwordEncoder.encode(user.getPassword());
        final UserEntity member = UserEntity.builder()
                .username(user.getUsername())
                .name(user.getName())
                .password(encodePw)
                .build();
        return userRepository.save(member);
    }
}
