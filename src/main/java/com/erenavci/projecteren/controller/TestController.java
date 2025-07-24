package com.erenavci.projecteren.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/test")
public class TestController {

    /** 
     * Sadece ROLE_USER sahibi kullanıcılar erişebilir 
     */
    @GetMapping("/hello")
    @PreAuthorize("hasRole('USER')")
    public String hello() {
        return "Hello, USER!";
    }

    /** 
     * Sadece ROLE_ADMIN sahibi kullanıcılar erişebilir 
     */
    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String adminOnly() {
        return "Hello, ADMIN!";
    }

    /** 
     * hasPermission örneği: 
     * PermissionEvaluator’daki logic’e göre “read” izni her zaman true dönüyor 
     */
    @GetMapping("/resource/{id}")
    @PreAuthorize("hasPermission(#id, 'Resource', 'read')")
    public String readResource(@PathVariable Long id) {
        return "Resource " + id;
    }
}
