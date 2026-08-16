package com.choem_vannin._security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtService {

    @Value("${jwt.secret}") // AUTO generate random String
    private String secret;

    @Value("${jwt.expiration}") // Generate 86400000s = 24 hours
    private long expiration;

    // Generate Token
    public String generateToken(UserDetails userDetails){
        // TODO: 8/12/2026: find role first
        String role = userDetails.getAuthorities().stream()
                .findFirst()
                .map(GrantedAuthority::getAuthority)
                .orElse("ROLE_USER"); // default if none found

        return Jwts.builder()
                .subject(userDetails.getUsername()) //get username(email) and store in subject
                .claim("role", role)
                .issuedAt(new Date()) // Record when the token is created
                .expiration(new Date(System.currentTimeMillis() + expiration)) // expired date
                .signWith(getSignKey())
                .compact();
    }

    private SecretKey getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    // extract username from jwt
    public String extractUsername(String token){
        return Jwts.parser()
                .verifyWith(getSignKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject(); // finally we get email(example@gmail.com) from our jwt token(eo2o4h3fw2fw...)
    }
    //extract role from jwt
    public String extractRole(String token){
        return Jwts.parser()
                .verifyWith(getSignKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .get("role", String.class);
    }

    // TODO: 8/10/2026: Add validation
    public boolean isTokenValid(String token, UserDetails userDetails){
        final String username = extractUsername(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token){
        return Jwts.parser()
                .verifyWith(getSignKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getExpiration();
    }
}
