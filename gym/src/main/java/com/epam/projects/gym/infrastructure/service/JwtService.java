package com.epam.projects.gym.infrastructure.service;

import java.io.Serializable;
import java.security.Key;
import java.time.LocalDate;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.spec.SecretKeySpec;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.epam.projects.gym.infrastructure.datasource.entity.RevokedToken;
import com.epam.projects.gym.infrastructure.datasource.repository.RevokedTokenRepository;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JwtService implements Serializable {
	
	private RevokedTokenRepository revokedTokenRepository;
	
	public JwtService(RevokedTokenRepository revokedTokenRepository) {
		this.revokedTokenRepository = revokedTokenRepository;
	}

	private static final long serialVersionUID = 1L;
	
	private static final String SECRET_KEY = "testing012345678910testingdasda1891651847dsasdasd109874563210pmvoba191519cqhoqhcq195211q64dffaf964f";
    private static final long EXPIRATION_TIME = 864_000_000;
    private final Key key = new SecretKeySpec(Base64.getEncoder().encode(SECRET_KEY.getBytes()), SignatureAlgorithm.HS512.getJcaName());
    
    public String generateJwtToken(UserDetails userDetails) { 
        Map<String, Object> claims = new HashMap<>();
        
        return Jwts.builder().setClaims(claims).setSubject(userDetails.getUsername()) 
           .setIssuedAt(new Date(System.currentTimeMillis())) 
           .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME * 1000)) 
           .signWith(key, SignatureAlgorithm.HS512).compact(); 
    }
    
    public String getUsernameFromToken(String token) {
        final Claims claims = Jwts.parserBuilder()
        		.setSigningKey(key)
        		.build()
        		.parseClaimsJws(token)
        		.getBody();
        return claims.getSubject(); 
    }
    
    public Boolean validateJwtToken(String token, UserDetails userDetails) { 
        String username = getUsernameFromToken(token); 
        Claims claims = Jwts.parserBuilder()
        		.setSigningKey(key)
        		.build()
        		.parseClaimsJws(token)
        		.getBody();
        Boolean isTokenExpired = claims.getExpiration().before(new Date());
        Boolean isTokenRevoked = revokedTokenRepository.existsById(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired && !isTokenRevoked); 
    }
    
    public void revokeToken(String token) {
		final LocalDate expiration = LocalDate.now();
		boolean isRevoked = revokedTokenRepository.existsById(token);
		if (token != null && !token.trim().isEmpty() && !isRevoked) {
			revokedTokenRepository.save(new RevokedToken(token, expiration));
		}
	}
	
}
