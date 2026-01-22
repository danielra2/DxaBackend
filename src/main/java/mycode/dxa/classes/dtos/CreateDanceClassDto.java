package mycode.dxa.classes.dtos;

import jakarta.validation.constraints.NotBlank;

public record CreateDanceClassDto(
        @NotBlank(message = "Titlul cursului este obligatoriu")
        String title,

        String description,

        @NotBlank(message = "Programul este obligatoriu")
        String schedule, // Ex: "Luni & Miercuri 19:00"

        @NotBlank(message = "Locația este obligatorie")
        String location
) {}