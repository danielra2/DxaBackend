package mycode.dxa.user.dtos;

import jakarta.validation.constraints.NotBlank;

public record LoginDto(
        @NotBlank(message = "Email-ul este obligatoriu")
        String email,

        @NotBlank(message = "Parola este obligatorie")
        String password
) {}