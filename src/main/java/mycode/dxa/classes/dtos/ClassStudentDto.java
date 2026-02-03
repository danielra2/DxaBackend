package mycode.dxa.classes.dtos;

import java.time.LocalDate;

public record ClassStudentDto(
        Long studentId,
        String fullName,
        boolean participated,
        LocalDate expirationDate // ADAUGĂ ACEST CÂMP
) {}