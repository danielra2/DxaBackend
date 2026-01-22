package mycode.dxa.classes.dtos;

import java.util.List;

public record DanceClassResponse(
        Long id,
        String title,
        String description,
        String schedule,
        String location,
        List<ClassStudentDto> students // <--- Lista nouă
) {}