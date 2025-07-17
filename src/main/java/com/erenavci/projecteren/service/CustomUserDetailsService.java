package com.erenavci.projecteren.service;

import com.erenavci.projecteren.model.User;           // ← Uygulamanın User modeli
import com.erenavci.projecteren.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Burada appUser kesinlikle model.User tipi olmalı
        User appUser = userRepository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        // Spring Security’nin UserDetails implementasyonunu tam nitelikli kullanıyoruz
        org.springframework.security.core.userdetails.User.UserBuilder builder =
            org.springframework.security.core.userdetails.User
                .withUsername(appUser.getUsername())
                .password(appUser.getPassword())
                .roles("USER");

        return builder.build();
    }
}
