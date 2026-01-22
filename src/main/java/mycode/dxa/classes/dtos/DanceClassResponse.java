package mycode.dxa.classes.dtos;

public record DanceClassResponse(
        Long id,
        String title,
        String description,
        String schedule,
        String location
) {}