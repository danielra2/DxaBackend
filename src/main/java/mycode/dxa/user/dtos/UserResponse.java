package mycode.dxa.user.dtos;

import java.time.LocalDate;
import java.util.List;

public record UserResponse(
        Long id,
        String firstName,
        String lastName,
        String email,
        String phone,
        String status,
        LocalDate subscriptionExpirationDate,

        // Date financiare
        Double lastPaymentAmount,
        Double nextPaymentAmount, // <--- Câmp Adăugat

        List<EnrolledClassDto> enrolledClasses
) {}