package mycode.dxa.user.dtos;

import java.time.LocalDate;

public record UpdateUserDto(
        String firstName,
        String lastName,
        String phone,
        LocalDate subscriptionExpirationDate,
        Double lastPaymentAmount,
        Double nextPaymentAmount,
        String lastPaymentComment 
) {}