package com.epam.projects.gym.infrastructure.config;

//import javax.servlet.http.HttpServletResponse;
//
//import org.springframework.beans.factory.annotation.Autowired;
//
//import static org.springframework.security.config.Customizer.withDefaults;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.config.Customizer;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//
//import com.epam.projects.gym.application.service.auth.AuthService;

//@Configuration
//@EnableWebSecurity
//@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true)
public class SecurityConfig {
	
//	@Autowired
//	private AuthService userDetailsService;
//
//	@Bean
//	AuthenticationManager authenticationManager(HttpSecurity http, BCryptPasswordEncoder bCryptPasswordEncoder, AuthService userDetailService) 
//	  throws Exception {
//	    return http.getSharedObject(AuthenticationManagerBuilder.class)
//	      .userDetailsService(userDetailsService)
//	      .passwordEncoder(bCryptPasswordEncoder)
//	      .and()
//	      .build();
//	}
//	
//	@Bean
//	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http.cors(withDefaults())
//                .csrf(csrf -> csrf
//                        .disable())
//                .exceptionHandling(handling -> handling
//                        .authenticationEntryPoint((request, response, exception) -> response.sendError(HttpServletResponse.SC_UNAUTHORIZED, exception.getMessage())))
//                .sessionManagement(management -> management
//                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//                .authorizeHttpRequests(requests -> requests
//                        .antMatchers("/api/trainees/**").hasAnyRole("TRAINEE")
//                        .antMatchers("/api/trainers/**").hasAnyRole("TRAINER")
//                        .antMatchers("/api/training/**").hasAnyRole("TRAINER")
//                        .antMatchers("/api/auth/**").permitAll()
//                        .antMatchers("/actuator/**").permitAll()
//                        .anyRequest()
//                        .authenticated());
//
//	    return http.build();
//	}
	
}
