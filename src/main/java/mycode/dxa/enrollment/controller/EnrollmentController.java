package mycode.dxa.enrollment.controller;

import mycode.dxa.enrollment.service.EnrollmentCommandService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
public class EnrollmentController implements EnrollmentControllerApi {

    private final EnrollmentCommandService enrollmentService;

    public EnrollmentController(EnrollmentCommandService enrollmentService) {
        this.enrollmentService = enrollmentService;
    }

    @Override
    public ResponseEntity<String> enrollStudent(
            @PathVariable Long studentId,
            @PathVariable Long classId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate expirationDate) {


        enrollmentService.enrollStudent(studentId, classId, expirationDate);
        return ResponseEntity.ok("Succes");
    }

    @Override
    public ResponseEntity<String> unenrollStudent(@PathVariable Long studentId, @PathVariable Long classId) {
        enrollmentService.unenrollStudent(studentId, classId);
        return ResponseEntity.ok("Înscrierea a fost anulată.");
    }
}