package mycode.dxa.enrollment.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/admin/enrollments")
@Tag(name = "Enrollments", description = "Managementul inscrierilor la cursuri")
public interface EnrollmentControllerApi {

    @PostMapping("/student/{studentId}/class/{classId}")
    @Operation(summary = "Inscriere student", description = "Inscrie un student la un curs specific")
    ResponseEntity<String> enrollStudent(@PathVariable Long studentId, @PathVariable Long classId);

    @DeleteMapping("/student/{studentId}/class/{classId}")
    @Operation(summary = "Dezabonare student", description = "Sterge inscrierea unui student de la un curs")
    ResponseEntity<String> unenrollStudent(@PathVariable Long studentId, @PathVariable Long classId);
}