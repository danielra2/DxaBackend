package mycode.dxa.user.dtos;

public record AuthResponse(
        String token,
        String email,
        String role
) {}