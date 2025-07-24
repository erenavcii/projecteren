package com.erenavci.projecteren.controller;

import com.erenavci.projecteren.dto.LoginRequest;
import com.erenavci.projecteren.dto.SignupRequest;
import com.erenavci.projecteren.dto.JwtResponse;
import com.erenavci.projecteren.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@Tag(name = "Authentication", description = "Kullanıcı kayıt ve giriş işlemleri")
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Operation(
      summary = "Yeni kullanıcı kaydı",
      description = "Verilen kullanıcı adı ve şifre ile yeni bir kullanıcı oluşturur",
      responses = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Kullanıcı başarıyla kayıt oldu"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Kullanıcı adı zaten kayıtlı")
      }
    )
    @PostMapping("/signup")
    public ResponseEntity<String> signup(@Valid @RequestBody SignupRequest req) {
        return authService.signup(req);
    }

    @Operation(
      summary = "Giriş (Login)",
      description = "Username ve password doğrulanır, geçerli ise JWT token döner",
      responses = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Giriş başarılı, token döndü"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "Geçersiz kimlik bilgileri")
      }
    )
    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@Valid @RequestBody LoginRequest req) {
        return authService.login(req);
    }
}
