package mycode.dxa.enrollment.controller;

import mycode.dxa.enrollment.service.EnrollmentCommandService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
public class EnrollmentController implements EnrollmentControllerApi {

    private final EnrollmentCommandService enrollmentService;

    public EnrollmentController(EnrollmentCommandService enrollmentService) {
        this.enrollmentService = enrollmentService;
    }

    @Override
    public ResponseEntity<String> enrollStudent(Long studentId, Long classId, LocalDate expirationDate) {
        enrollmentService.enrollStudent(studentId, classId, expirationDate);
        return ResponseEntity.ok("Studentul a fost înscris cu succes până la data de " + expirationDate);
    }

    @Override
    public ResponseEntity<String> unenrollStudent(Long studentId, Long classId) {
        enrollmentService.unenrollStudent(studentId, classId);
        return ResponseEntity.ok("Înscrierea a fost anulată.");
    }
}