package com.epam.projects.gym.infrastructure.service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.epam.projects.gym.infrastructure.datasource.entity.UserEntity;
import com.epam.projects.gym.infrastructure.datasource.repository.UserJpaRepository;

@Service
public class AuthService implements UserDetailsService {
	
	private UserJpaRepository userJpaRepository;
	
	public AuthService(UserJpaRepository userJpaRepository) {
		this.userJpaRepository = userJpaRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<UserEntity> user = userJpaRepository.findByUsername(username);
		
		if (user.isPresent()) {
			return new User(
							user.get().getUsername(),
							user.get().getPassword(),
							user.get().getIsActive(),
							true,
							true,
							true,
							getAuthority(user.get())
						);
		} else {
			throw new UsernameNotFoundException("Invalid credentials.");
		}
	}
	
	private Set<SimpleGrantedAuthority> getAuthority(UserEntity user) {
		Set<SimpleGrantedAuthority> authorities = new HashSet<>();

		if (user.getTraineeId() != null) {
			authorities.add(new SimpleGrantedAuthority("TRAINEE"));
		} else if (user.getTrainerId() != null) {
			authorities.add(new SimpleGrantedAuthority("TRAINER"));
		}
		return authorities;
	}

}
