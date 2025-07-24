package com.erenavci.projecteren.config;

import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
public class CustomPermissionEvaluator implements PermissionEvaluator {

    @Override
    public boolean hasPermission(Authentication auth, Object targetDomainObject, Object permission) {
        // Basit demo: "read" iznini her zaman kabul et, diğerlerini reddet
        if ("read".equals(permission)) {
            return true;
        }
        return false;
    }

    @Override
    public boolean hasPermission(Authentication auth, Serializable targetId, String targetType, Object permission) {
        // Gelişmiş senaryolar için burayı genişletebilirsin
        return false;
    }
}
