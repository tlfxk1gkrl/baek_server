package com.example.baek_server.controller;

import com.example.baek_server.entity.UserEntity;
import com.example.baek_server.mylib.JwtProvider;
import com.example.baek_server.repo.UserRepository;
import io.jsonwebtoken.Claims;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequiredArgsConstructor
public class TokenController {

    private final JwtProvider jwtProvider;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping(value = "/tokenCreate")
    public TokenResponse createToken(@RequestBody UserEntity user) throws Exception{
        if (userRepository.existsByUsername(user.getUsername())){
            UserEntity tempUser = userRepository.findByUsername(user.getUsername());
            if(passwordEncoder.matches(user.getPassword(),tempUser.getPassword())){
                String token = jwtProvider.createJwtToken(user.getUsername(), user.getName());
                Claims claims = jwtProvider.parseJwtToken("Bearer "+token);

                TokenDataResponse tokenDataResponse = new TokenDataResponse(token, claims.getSubject(), claims.getIssuedAt().toString(), claims.getExpiration().toString());
                TokenResponse tokenResponse = new TokenResponse("200", "OK", tokenDataResponse);

                return tokenResponse;
            }
        }
        return null;
    }

    @GetMapping(value = "/checkToken")
    public TokenResponseNoData checkToken(@RequestHeader(value = "Authorization") String token) throws Exception {
        Claims claims = jwtProvider.parseJwtToken(token);

        TokenResponseNoData tokenResponseNoData = new TokenResponseNoData("200", "success");
        return tokenResponseNoData;
    }


    @Data
    @AllArgsConstructor
    static class TokenResponse<T>{
        private String code;
        private String msg;
        private T data;
    }

    @Data
    @AllArgsConstructor
    static class TokenResponseNoData<T>{
        private String code;
        private String msg;
    }

    @Data
    @AllArgsConstructor
    static class TokenDataResponse{
        private String token;
        private String subject;
        private String issued_time;
        private String expired_time;
    }
}
