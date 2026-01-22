package mycode.dxa.enrollment.controller;

import mycode.dxa.enrollment.service.EnrollmentCommandService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EnrollmentController implements EnrollmentControllerApi {

    private final EnrollmentCommandService enrollmentService;

    public EnrollmentController(EnrollmentCommandService enrollmentService) {
        this.enrollmentService = enrollmentService;
    }

    @Override
    public ResponseEntity<String> enrollStudent(Long studentId, Long classId) {
        enrollmentService.enrollStudent(studentId, classId);
        return ResponseEntity.ok("Studentul a fost înscris cu succes!");
    }

    @Override
    public ResponseEntity<String> unenrollStudent(Long studentId, Long classId) {
        enrollmentService.unenrollStudent(studentId, classId);
        return ResponseEntity.ok("Studentul a fost șters de la curs!");
    }
    @Override
    public ResponseEntity<Void> toggleParticipation(Long studentId, Long classId, boolean participated) {
        enrollmentService.toggleParticipation(studentId, classId, participated);
        return ResponseEntity.ok().build();
    }
}