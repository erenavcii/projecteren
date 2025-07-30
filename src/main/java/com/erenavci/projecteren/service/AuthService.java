package com.erenavci.projecteren.service;

import com.erenavci.projecteren.dto.LoginRequest;
import com.erenavci.projecteren.dto.SignupRequest;
import com.erenavci.projecteren.dto.JwtResponse;
import com.erenavci.projecteren.model.User;
import com.erenavci.projecteren.repository.UserRepository;
import com.erenavci.projecteren.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private JwtUtil jwtUtil;

    public ResponseEntity<String> signup(SignupRequest req) {
        if (userRepository.findByUsername(req.getUsername()).isPresent()) {
            return ResponseEntity
                .badRequest()
                .body("Error: Username is already taken!");
        }

        User user = new User();
        user.setUsername(req.getUsername());
        user.setPassword(passwordEncoder.encode(req.getPassword())); // 🔥 burası düzeldi
        userRepository.save(user);

        return ResponseEntity.ok("User registered successfully!");
    }

    public ResponseEntity<JwtResponse> login(LoginRequest req) {
        try {
            authManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    req.getUsername(),
                    req.getPassword()
                )
            );
        } catch (BadCredentialsException ex) {
            return ResponseEntity
                .status(401)
                .build();
        }

        String token = jwtUtil.generateToken(req.getUsername());
        JwtResponse resp = new JwtResponse(token, "Bearer", req.getUsername());
        return ResponseEntity.ok(resp);
    }
}
