package mycode.dxa.user.dtos;

import java.time.LocalDate;

public record UserResponse(
        Long id,
        String firstName,
        String lastName,
        String email,
        String phone,
        String status, // "Active", "Current", "Dormant"
        LocalDate subscriptionExpirationDate,
        Double lastPaymentAmount
) {}