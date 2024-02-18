package com.epam.projects.gym.infrastructure.service;

import java.io.Serializable;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.spec.SecretKeySpec;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private static final String SECRET_KEY = "testing012345678910testingdasdasdasdasdadsasdasd109874563210";
    private static final long EXPIRATION_TIME = 864_000_000;
    
    public String generateJwtToken(UserDetails userDetails) { 
        Map<String, Object> claims = new HashMap<>();
        Key key = new SecretKeySpec(Base64.getEncoder().encode(SECRET_KEY.getBytes()), SignatureAlgorithm.HS512.getJcaName());
        
        return Jwts.builder().setClaims(claims).setSubject(userDetails.getUsername()) 
           .setIssuedAt(new Date(System.currentTimeMillis())) 
           .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME * 1000)) 
           .signWith(key, SignatureAlgorithm.HS512).compact(); 
    }
    
    public String getUsernameFromToken(String token) {
        final Claims claims = Jwts.parserBuilder()
        		.setSigningKey(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()))
        		.build()
        		.parseClaimsJws(token)
        		.getBody();
        return claims.getSubject(); 
    }
    
    public Boolean validateJwtToken(String token, UserDetails userDetails) { 
        String username = getUsernameFromToken(token); 
        Claims claims = Jwts.parserBuilder()
        		.setSigningKey(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()))
        		.build()
        		.parseClaimsJws(token)
        		.getBody();
        Boolean isTokenExpired = claims.getExpiration().before(new Date()); 
        return (username.equals(userDetails.getUsername()) && !isTokenExpired); 
    } 
	
}
