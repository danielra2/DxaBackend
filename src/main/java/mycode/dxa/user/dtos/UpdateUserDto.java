package mycode.dxa.user.dtos;

import java.time.LocalDate;

public record UpdateUserDto(
        String firstName,
        String lastName,
        String phone,

        // Modificând asta, schimbi automat și STATUSUL (Active/Dormant)
        LocalDate subscriptionExpirationDate,

        Double lastPaymentAmount,
        Double nextPaymentAmount // Câmpul nou
) {}