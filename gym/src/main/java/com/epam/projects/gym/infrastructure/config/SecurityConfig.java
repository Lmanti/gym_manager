package com.epam.projects.gym.infrastructure.config;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.epam.projects.gym.infrastructure.service.AuthService;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true)
public class SecurityConfig {
	
	@Autowired
	private AuthService userDetailsService;
	
	@Autowired
	private JwtAuthenticationEntryPoint authenticationEntryPoint;
	
	@Autowired
	private JwtAuthenticationFilter authenticationFilter;
	
	@Bean
    BCryptPasswordEncoder encoder(){
        return new BCryptPasswordEncoder();
    }

	@Bean
	AuthenticationManager authenticationManager(HttpSecurity http, BCryptPasswordEncoder bCryptPasswordEncoder, AuthService userDetailService) 
	  throws Exception {
	    return http.getSharedObject(AuthenticationManagerBuilder.class)
	      .userDetailsService(userDetailsService)
	      .passwordEncoder(bCryptPasswordEncoder)
	      .and()
	      .build();
	}
	
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors(withDefaults())
                .csrf(csrf -> csrf
                        .disable())
                .exceptionHandling(handling -> handling.authenticationEntryPoint(authenticationEntryPoint))
                .sessionManagement(management -> management
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(requests -> requests
                        .antMatchers(HttpMethod.GET, "/api/trainees/**").hasAnyRole("TRAINEE")
                        .antMatchers(HttpMethod.PUT, "/api/trainees/**").hasAnyRole("TRAINEE")
                        .antMatchers(HttpMethod.PATCH, "/api/trainees").hasAnyRole("TRAINEE")
                        .antMatchers(HttpMethod.DELETE, "/api/trainees/**").hasAnyRole("TRAINEE")
                        .antMatchers(HttpMethod.POST, "/api/trainees").permitAll()
                        .antMatchers(HttpMethod.GET, "/api/trainers/**").hasAnyRole("TRAINER")
                        .antMatchers(HttpMethod.PUT, "/api/trainers").hasAnyRole("TRAINER")
                        .antMatchers(HttpMethod.PATCH, "/api/trainers").hasAnyRole("TRAINER")
                        .antMatchers(HttpMethod.POST, "/api/trainers/**").permitAll()
                        .antMatchers("/api/training/**").hasAnyRole("TRAINER", "TRAINEE")
                        .antMatchers(HttpMethod.GET, "/api/auth").permitAll()
                        .antMatchers(HttpMethod.PUT, "/api/auth/trainee").hasAnyRole("TRAINEE")
                        .antMatchers(HttpMethod.PUT, "/api/auth/trainer").hasAnyRole("TRAINER")
                        .antMatchers("/actuator/**").permitAll()
                        .anyRequest()
                        .authenticated())
                .addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class);

	    return http.build();
	}
	
	@Bean
	WebSecurityCustomizer webSecurityCustomizer() {
	    return web -> web.debug(false).ignoring().antMatchers(
	    		"/v2/api-docs",
	    		"/swagger-resources/**",
	    		"/swagger-ui/**",
	    		"/h2-console/**");
	}
	
}
