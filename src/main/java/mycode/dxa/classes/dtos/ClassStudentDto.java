package mycode.dxa.classes.dtos;

public record ClassStudentDto(
        Long studentId,
        String fullName,
        boolean participated
) {}