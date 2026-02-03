package mycode.dxa.enrollment.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Tag(name = "Enrollment", description = "Endpoints pentru gestionarea înscrierilor")
@RequestMapping("/admin/enrollments")
public interface EnrollmentControllerApi {

    @Operation(summary = "Înscrie un student la un curs cu o dată specifică de expirare")
    @PostMapping("/student/{studentId}/class/{classId}")
    ResponseEntity<String> enrollStudent(
            @PathVariable Long studentId,
            @PathVariable Long classId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate expirationDate // ADAUGAT
    );

    @Operation(summary = "Anulează înscrierea unui student")
    @DeleteMapping("/student/{studentId}/class/{classId}")
    ResponseEntity<String> unenrollStudent(
            @PathVariable Long studentId,
            @PathVariable Long classId
    );
}