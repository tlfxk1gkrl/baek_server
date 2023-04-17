package com.example.baek_server.mylib;

import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.time.Duration;
import java.util.Date;

public class BaeksLibrary {
    public String makeJwtToken(String userName, String name){
        Date now = new Date();

        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE) // (1)
                .setIssuer("fresh") // (2)
                .setIssuedAt(now) // (3)
                .setExpiration(new Date(now.getTime() + Duration.ofMinutes(60).toMillis())) // (4)
                .claim("userName", userName) // (5)
                .claim("name", name)
                .signWith(SignatureAlgorithm.HS256, "secret") // (6)
                .compact();
    }
}
