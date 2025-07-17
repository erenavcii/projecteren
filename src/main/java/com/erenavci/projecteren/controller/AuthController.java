package com.erenavci.projecteren.controller;

import com.erenavci.projecteren.dto.LoginRequest;
import com.erenavci.projecteren.dto.SignupRequest;
import com.erenavci.projecteren.dto.JwtResponse;
import com.erenavci.projecteren.model.User;
import com.erenavci.projecteren.repository.UserRepository;
import com.erenavci.projecteren.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;             // ⚠️ Doğru sınıf
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@Valid @RequestBody SignupRequest req) {
        if (userRepository.findByUsername(req.getUsername()).isPresent()) {
            return ResponseEntity
                .badRequest()
                .body("Error: Username is already taken!");
        }
        User user = new User();
        user.setUsername(req.getUsername());
        user.setPassword(passwordEncoder.encode(req.getPassword()));
        userRepository.save(user);
        return ResponseEntity.ok("User registered successfully!");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest req) {
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
                .body("Error: Invalid credentials");
        }
        String token = jwtUtil.generateToken(req.getUsername());
        JwtResponse resp = new JwtResponse(token, "Bearer", req.getUsername());
        return ResponseEntity.ok(resp);
    }
}
