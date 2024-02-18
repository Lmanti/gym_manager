package com.epam.projects.gym.application.service.auth;

//import java.util.HashSet;
//import java.util.Optional;
//import java.util.Set;
//
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;

//import com.epam.projects.gym.domain.entity.User;
//import com.epam.projects.gym.domain.repository.UserRepository;
//
//@Service
public class AuthService {
	
//	private UserRepository userRepository;
//	
//	public AuthService(UserRepository userRepository) {
//		this.userRepository = userRepository;
//	}
//
//	@Override
//	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//		Optional<User> user = userRepository.findByUsername(username);
//		
//		if (user.isPresent()) {
//			return new org.springframework.security.core.userdetails
//					.User(
//							user.get().getUsername(),
//							user.get().getPassword(),
//							user.get().getIsActive(),
//							true,
//							true,
//							true,
//							getAuthority(user.get())
//						);
//		} else {
//			throw new UsernameNotFoundException("Invalid credentials.");
//		}
//	}
//	
//	private Set<SimpleGrantedAuthority> getAuthority(User user) {
//		Set<SimpleGrantedAuthority> authorities = new HashSet<>();
//
//		if (user.getTraineeId() != null) {
//			authorities.add(new SimpleGrantedAuthority("TRAINEE"));
//		} else if (user.getTrainerId() != null) {
//			authorities.add(new SimpleGrantedAuthority("TRAINER"));
//		}
//		return authorities;
//	}

}
