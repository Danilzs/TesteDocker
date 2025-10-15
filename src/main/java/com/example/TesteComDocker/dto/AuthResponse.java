package com.example.TesteComDocker.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {
    private String token;
    private String username;
    private String email;
    private boolean twoFactorEnabled;
    private boolean requiresTwoFactor;
    private String message;
    
    public AuthResponse(String token, String username, String email, boolean twoFactorEnabled) {
        this.token = token;
        this.username = username;
        this.email = email;
        this.twoFactorEnabled = twoFactorEnabled;
        this.requiresTwoFactor = false;
    }
}
