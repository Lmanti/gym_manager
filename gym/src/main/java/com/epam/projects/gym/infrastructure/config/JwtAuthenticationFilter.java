package com.epam.projects.gym.infrastructure.config;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.epam.projects.gym.infrastructure.service.AuthService;
import com.epam.projects.gym.infrastructure.service.JwtService;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
	
	private AuthService userDetailsService;
	
	private JwtService tokenManager;
	
	private final Map<String, Integer> loginAttemptsCache = new HashMap<>();
	
	public JwtAuthenticationFilter(AuthService userDetailsService, JwtService tokenManager) {
		this.userDetailsService = userDetailsService;
		this.tokenManager = tokenManager;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		String header = request.getHeader("Authorization");
		if (header == null || !header.startsWith("Bearer ")) {
			String username = request.getParameter("username");
			
			if (username != null && !username.isEmpty()) {
				int attempts = loginAttemptsCache.getOrDefault(username, 0);
		        attempts++;
		        log.info("User with username {} has attempted to log in {} times.", username, attempts);
		        loginAttemptsCache.put(username, attempts);
		        
		        if (attempts >= 3) {
		        	log.info("User with username {} has attempted to log in {} times. Account blocked for 5 minutes.", username, attempts);
		            try {
						Thread.sleep(((60 * 5) * 1000));
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
		            loginAttemptsCache.remove(username);
		        }
			}

            filterChain.doFilter(request, response);
            return;
        }
		
		String token = header.replace("Bearer ", "");
		String username = null;
		
		try {
			username = tokenManager.getUsernameFromToken(token);
        } catch (MalformedJwtException ex) {
        	log.error("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
        	log.error("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
        	log.error("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
        	log.error("JWT claims string is empty.");
        } catch (JwtException e) {
        	log.warn("invalid token generate an error", e);
        }
		try {
			if (null != username && SecurityContextHolder.getContext().getAuthentication() == null) {
				UserDetails userDetails = userDetailsService.loadUserByUsername(username);
		        if (tokenManager.validateJwtToken(token, userDetails)) {
		           UsernamePasswordAuthenticationToken
		           authenticationToken = new UsernamePasswordAuthenticationToken(
		           userDetails, null,
		           userDetails.getAuthorities());
		           authenticationToken.setDetails(new
		           WebAuthenticationDetailsSource().buildDetails(request));
		           SecurityContextHolder.getContext().setAuthentication(authenticationToken);
		        }
		    }
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		filterChain.doFilter(request, response);
		
	}

}
