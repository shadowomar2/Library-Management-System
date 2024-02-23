package com.example.demo.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import com.example.demo.config.JwtTokenUtil;
import com.example.demo.models.JwtRequest;
import com.example.demo.models.JwtResponse;
import com.example.demo.services.JwtUserDetailsService;
 

/**
 * Controller class to handle user authentication with JWT.
 */
@RestController
@CrossOrigin
public class JwtAuthenticationController {

	 @Autowired
	    private AuthenticationManager authenticationManager;

	    @Autowired
	    private JwtTokenUtil jwtTokenUtil;

	    @Autowired
	    private JwtUserDetailsService userDetailsService;

	    /**
	     * Endpoint to authenticate users and generate JWT token.
	     *
	     * @param authenticationRequest The request object containing username/email and password.
	     * @return ResponseEntity with JWT token in the response body.
	     * @throws Exception If authentication fails.
	     */
	    @RequestMapping(value = "/login", method = RequestMethod.POST)
	    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {

	        authenticate(authenticationRequest.getUsernameoremail(), authenticationRequest.getPassword());

	        final UserDetails userDetails = userDetailsService
	                .loadUserByUsername(authenticationRequest.getUsernameoremail());

	        final String token = jwtTokenUtil.generateToken(userDetails);
	        HttpHeaders headers = new HttpHeaders();
	        headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + token);
	        return ResponseEntity.ok()
	                .headers(headers)
	                .body(new JwtResponse(token));
	    }

	    /**
	     * Endpoint to add an admin user.
	     *
	     * @return ResponseEntity with a success message.
	     */
	    @PostMapping("/makeadmin")
	    public ResponseEntity<?> addAdmin() {
	        userDetailsService.createAdminUser("admin", "admin");
	        return ResponseEntity.ok("Admin user created successfully.");
	    }

	    /**
	     * Authenticates the user using the provided username/email and password.
	     *
	     * @param username The username or email of the user.
	     * @param password The password of the user.
	     * @throws Exception If authentication fails.
	     */
	    private void authenticate(String username, String password) throws Exception {

	        try {
	            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
	        } catch (DisabledException e) {
	            throw new Exception("USER_DISABLED", e);
	        } catch (BadCredentialsException e) {
	            throw new Exception("INVALID_CREDENTIALS", e);
	        }
	    }
	}