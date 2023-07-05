package com.br.Library.security;

import java.security.Key;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;

@Component
public class TokenUtil {

    private static final String EMISSOR = "tour-track";
    private static final String TOKEN_HEADER = "Bearer ";
    private static final String TOKEN_KEY = "01234567890123456789012345678901";
    
    public static String encodeToken(String username) {
        Key secretKey = Keys.hmacShaKeyFor(TOKEN_KEY.getBytes());
        String tokenJwt = Jwts.builder()
            .setSubject(username)
            .setIssuer(EMISSOR)
            .signWith(secretKey, SignatureAlgorithm.HS256)
            .compact();

        return tokenJwt;
    };

    public static String getSubject(HttpServletRequest request) {
        String jwtToken = request.getHeader("Authorization");

        return getSubject(jwtToken);
    }

    public static String getSubject(String jwtToken) {
        try {

            while(jwtToken.startsWith("Bearer ")) {
                jwtToken = jwtToken.replace(TOKEN_HEADER, "");
            }
            
            //leitura do token
            Jws<Claims> jwsClaims = Jwts
                .parserBuilder()
                .setSigningKey(TOKEN_KEY.getBytes())
                .build()
                .parseClaimsJws(jwtToken);

            String subject = jwsClaims.getBody().getSubject();
            String emissor = jwsClaims.getBody().getIssuer();

            if(subject.length() > 0 && emissor.equals(EMISSOR)) {
                return subject;
            } else {
                return null; 
            }
        } catch(Exception e) {
            return null;
        }
    }
}
