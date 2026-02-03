package mycode.dxa.user.dtos;

import java.time.LocalDate;

// EnrolledClassDto.java
public record EnrolledClassDto(
        Long id,
        String title,
        String schedule,
        LocalDate expirationDate // ADAUGAT
) {}