package com.erenavci.projecteren.dto;

public class JwtResponse {

    private String token;
    private String tokenType;
    private String username;

    public JwtResponse() {}

    public JwtResponse(String token, String tokenType, String username) {
        this.token = token;
        this.tokenType = tokenType;
        this.username = username;
    }

    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
    }

    public String getTokenType() {
        return tokenType;
    }
    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
}
