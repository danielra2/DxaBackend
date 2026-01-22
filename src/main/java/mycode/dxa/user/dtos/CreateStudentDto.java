package mycode.dxa.user.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;

public record CreateStudentDto(
        @NotBlank(message = "Prenumele este obligatoriu")
        String firstName,

        @NotBlank(message = "Numele este obligatoriu")
        String lastName,

        @NotBlank(message = "Email-ul este obligatoriu")
        @Email(message = "Format invalid de email")
        String email,

        @NotBlank(message = "Parola este obligatorie")
        @Size(min = 6, message = "Parola trebuie să aibă minim 6 caractere")
        String password,

        String phone,

        LocalDate subscriptionExpirationDate,
        Double lastPaymentAmount,
        Double nextPaymentAmount
) {}