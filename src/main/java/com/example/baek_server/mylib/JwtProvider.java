package com.example.baek_server.mylib;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtProvider {

    @Value("{jwt.password}")
    private String secretKey;

    //create token
    public String createJwtToken(String userName, String name){
        Date now = new Date();

        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE) // (1)
                .setIssuer("fresh") // (2)
                .setIssuedAt(now) // (3)
                .setExpiration(new Date(now.getTime() + Duration.ofMinutes(60).toMillis())) // (4)
                .claim("userName", userName) // (5)
                .claim("name", name)
                .signWith(SignatureAlgorithm.HS256, Base64.getEncoder().encodeToString(secretKey.getBytes())) // (6)
                .compact();
    }

    //check token
    public Claims parseJwtToken(String token){
        token = BearerRemove(token);
        return Jwts.parser()
                .setSigningKey(Base64.getEncoder().encodeToString(secretKey.getBytes()))
                .parseClaimsJws(token)
                .getBody();
    }

    //remove bearer
    public String BearerRemove(String token){
        return token.substring("Bearer ".length());
    }
}
