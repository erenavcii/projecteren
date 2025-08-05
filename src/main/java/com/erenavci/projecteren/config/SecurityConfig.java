	package com.erenavci.projecteren.config;
	
	import com.erenavci.projecteren.service.CustomUserDetailsService;
	import org.springframework.beans.factory.annotation.Autowired;
	import org.springframework.context.annotation.Bean;
	import org.springframework.context.annotation.Configuration;
	import org.springframework.security.access.PermissionEvaluator;
	import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
	import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
	import org.springframework.security.config.Customizer;
	import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
	import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
	import org.springframework.security.config.annotation.web.builders.HttpSecurity;
	import org.springframework.security.config.http.SessionCreationPolicy;
	import org.springframework.security.authentication.AuthenticationManager;
	import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
	import org.springframework.security.crypto.password.PasswordEncoder;
	import org.springframework.security.web.SecurityFilterChain;
	import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
	
	@Configuration
	@EnableMethodSecurity(prePostEnabled = true)
	public class SecurityConfig {
	
	    @Autowired
	    private CustomUserDetailsService userDetailsService;
	
	    @Autowired
	    private JwtAuthFilter jwtAuthFilter;
	
	    @Bean
	    public PasswordEncoder passwordEncoder() {
	        return new BCryptPasswordEncoder();
	    }
	
	    @Bean
	    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
	        return http.getSharedObject(AuthenticationManagerBuilder.class)
	                   .userDetailsService(userDetailsService)
	                   .passwordEncoder(passwordEncoder())
	                   .and()
	                   .build();
	    }
	
	    @Bean
	    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
	        http
	          .csrf(csrf -> csrf.disable())
	          .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
	          .authorizeHttpRequests(auth -> auth
	              .requestMatchers(
	                  "/api/auth/**",
	                  "/h2-console/**",
	                  "/v3/api-docs/**",
	                  "/swagger-ui/**"
	              ).permitAll()
	              .anyRequest().authenticated()
	          )
	          .headers(headers -> headers.frameOptions(frame -> frame.sameOrigin()))
	          .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
	          .httpBasic(Customizer.withDefaults());
	
	        return http.build();
	    }
	
	    @Bean
	    public MethodSecurityExpressionHandler methodSecurityExpressionHandler(PermissionEvaluator permissionEvaluator) {
	        DefaultMethodSecurityExpressionHandler handler = new DefaultMethodSecurityExpressionHandler();
	        handler.setPermissionEvaluator(permissionEvaluator);
	        return handler;
	    }
	}
