package com.retail.orderservice.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;

@Component
public class JWTUtil {
    private static final String Secret = "ASuperSimpleSecretKeyForTheRetailApplication!";
    private static final Key key = Keys.hmacShaKeyFor(Secret.getBytes());

    public Long extractUserId(String token){
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("userId", Long.class);
    }
}
