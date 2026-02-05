package mycode.dxa.enrollment.service;

import mycode.dxa.classes.models.DanceClass;
import mycode.dxa.classes.repository.DanceClassRepository;
import mycode.dxa.enrollment.models.Enrollment;
import mycode.dxa.enrollment.models.EnrollmentStatus;
import mycode.dxa.enrollment.repository.EnrollmentRepository;
import mycode.dxa.user.models.User;
import mycode.dxa.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.Optional;

@Service
@Transactional
public class EnrollmentCommandServiceImpl implements EnrollmentCommandService {

    private final EnrollmentRepository enrollmentRepository;
    private final UserRepository userRepository;
    private final DanceClassRepository danceClassRepository;

    public EnrollmentCommandServiceImpl(EnrollmentRepository enrollmentRepository,
                                        UserRepository userRepository,
                                        DanceClassRepository danceClassRepository) {
        this.enrollmentRepository = enrollmentRepository;
        this.userRepository = userRepository;
        this.danceClassRepository = danceClassRepository;
    }

    @Override
    public void enrollStudent(Long studentId, Long classId, LocalDate expirationDate) {
        Optional<Enrollment> existing = enrollmentRepository.findByStudentIdAndDanceClassId(studentId, classId);

        if (existing.isPresent()) {
            // Dacă există deja, doar actualizăm data (Reînnoire)
            Enrollment enrollment = existing.get();
            enrollment.setExpirationDate(expirationDate);
            enrollment.setStatus(EnrollmentStatus.ACTIVE);
            enrollmentRepository.save(enrollment);
        } else {
            // Dacă nu există, creăm o înscriere nouă
            User student = userRepository.findById(studentId).orElseThrow(() -> new RuntimeException("Student negăsit"));
            DanceClass danceClass = danceClassRepository.findById(classId).orElseThrow(() -> new RuntimeException("Curs negăsit"));

            Enrollment enrollment = new Enrollment();
            enrollment.setStudent(student);
            enrollment.setDanceClass(danceClass);
            enrollment.setExpirationDate(expirationDate);
            enrollment.setStatus(EnrollmentStatus.ACTIVE);
            enrollmentRepository.save(enrollment);
        }
    }

    @Override
    public void unenrollStudent(Long studentId, Long classId) {
        Enrollment enrollment = enrollmentRepository.findByStudentIdAndDanceClassId(studentId, classId)
                .orElseThrow(() -> new RuntimeException("Această înscriere nu există!"));
        enrollmentRepository.delete(enrollment);
    }

    @Override
    public void toggleParticipation(Long studentId, Long classId, boolean participated) {
        Enrollment enrollment = enrollmentRepository.findByStudentIdAndDanceClassId(studentId, classId)
                .orElseThrow(() -> new RuntimeException("Înscrierea nu a fost găsită!"));
        enrollment.setParticipated(participated);
        enrollmentRepository.save(enrollment);
    }
}