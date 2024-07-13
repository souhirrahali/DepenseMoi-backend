package com.example.depenses.jwt;

import java.util.Date;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.example.depenses.dao.entities.User;
import com.example.depenses.dao.repositories.UserRepository;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {
    private final String secret_key="4bb6d1dfbafb64a681139d1586b6f1160d18159afd57c8c79136d7490630407c";
    private UserRepository repo;

public String extractUsername(String token){
    return extractClaim(token, Claims ::getSubject);
}

public Integer extactUserId(String token){
    String email=extractUsername(token);
    User user=repo.findByEmail(email).orElseThrow();
    return user.getUserId();
}
    
public boolean isValid(String token,UserDetails user){
        String username=extractUsername(token);
        return username.equals(user.getUsername())&&!isTokenExpired(token);
    }
    
private boolean isTokenExpired(String token) {
        return extractExpiration (token).before(new Date());
    }
    
public String generatToken(User  user){
        String token =Jwts.builder().subject(user.getEmail())
                            .issuedAt(new Date(System.currentTimeMillis()))
                            .expiration(new Date(System.currentTimeMillis() +24*60*60*1000))
                            .signWith(getSigninKey())
                            .compact();
        return token;
    }
    
private SecretKey getSigninKey() {
        byte[] keyBytes=Decoders.BASE64URL.decode(secret_key);
        return Keys.hmacShaKeyFor(keyBytes);
    }
    
private Date extractExpiration(String token) {
        return extractClaim(token, Claims ::getExpiration);
    }
    
public<T> T extractClaim(String token, Function<Claims,T> resolver){
        Claims claims=extractAllClaims(token);
        return resolver.apply(claims);
    }

private Claims extractAllClaims(String token){
        return Jwts.parser()
                    .verifyWith(getSigninKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
    }


}
