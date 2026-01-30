package mycode.dxa.classes.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import mycode.dxa.attendance.dtos.MarkAttendanceDto;
import mycode.dxa.classes.dtos.CreateDanceClassDto;
import mycode.dxa.classes.dtos.DanceClassResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RequestMapping("/api")
@Tag(name = "Classes", description = "Managementul cursurilor de dans")
public interface ClassControllerApi {

    @GetMapping("/classes")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Lista cursuri", description = "Returneaza toate cursurile disponibile")
    ResponseEntity<List<DanceClassResponse>> getAllClasses();

    @PostMapping("/admin/classes")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Creare curs", description = "Adauga un curs nou (Admin)")
    ResponseEntity<DanceClassResponse> createClass(@Valid @RequestBody CreateDanceClassDto dto);

    @DeleteMapping("/admin/classes/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Stergere curs", description = "Sterge un curs existent (Admin)")
    ResponseEntity<Void> deleteClass(@PathVariable Long id);

}