package com.stockflow.auth;

public record AuthResponse(
        String token,
        String email,
        String name,
        String role,
        Long companyId
) {}