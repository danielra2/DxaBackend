package mycode.dxa.user.dtos;

import java.time.LocalDate;

public record EnrolledClassDto(
        Long id,
        String title,
        String schedule,
        LocalDate expirationDate
) {}