package com.example.demo.services;


import java.sql.Date;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.entities.Role;
import com.example.demo.entities.User;
import com.example.demo.repositories.RoleRepository;
import com.example.demo.repositories.UserRepository;

@Service
public class JwtUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private RoleRepository roleRepository;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	    User user = userRepository.findByUsernameOrEmail(username,username)
	            .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

	    List<GrantedAuthority> authorities = new ArrayList<>();
	    for (Role role : user.getRoles()) {
	        authorities.add(new SimpleGrantedAuthority(role.getName()));
	    }

	    return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
	            authorities);
	}
	public void createAdminUser(String Username, String password) {
		User user = new User();
		user.setName(Username);
		user.setUsername(Username);
		user.setEmail(Username);
    
		user.setPassword(passwordEncoder.encode(password));

		Optional<Role> roleOptional = roleRepository.findByName("ADMIN");

		Role role;
		if (roleOptional.isPresent()) {
			role = roleOptional.get();

		} else {
			role = new Role();
			role.setName("ADMIN");
			roleRepository.save(role);

		}
		Set<Role> userRoles = new HashSet<>();
		userRoles.add(role);

		user.setRoles(userRoles);

 
		System.out.println(user);
		userRepository.save(user);
	}
}