package mycode.dxa.enrollment.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Tag(name = "Enrollment", description = "Endpoints pentru gestionarea înscrierilor")
@RequestMapping("/api/admin/enrollments")
@Tag(name = "Enrollments", description = "Managementul inscrierilor la cursuri")
public interface EnrollmentControllerApi {

    @PostMapping("/student/{studentId}/class/{classId}")
    @Operation(summary = "Inscriere student", description = "Inscrie un student la un curs specific cu data de expirare")
    ResponseEntity<String> enrollStudent(
            @PathVariable Long studentId,
            @PathVariable Long classId,
            @RequestParam LocalDate expirationDate
    );

    @Operation(summary = "Anulează înscrierea unui student")
    @DeleteMapping("/student/{studentId}/class/{classId}")
    ResponseEntity<String> unenrollStudent(
            @PathVariable Long studentId,
            @PathVariable Long classId
    );
}