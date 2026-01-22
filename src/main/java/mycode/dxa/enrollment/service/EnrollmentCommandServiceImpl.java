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
    public void enrollStudent(Long studentId, Long classId) {
        if (enrollmentRepository.existsByStudentIdAndDanceClassId(studentId, classId)) {
            throw new RuntimeException("Studentul este deja înscris la acest curs!");
        }

        User student = userRepository.findById(studentId).orElseThrow(() -> new RuntimeException("Studentul nu există!"));
        DanceClass danceClass = danceClassRepository.findById(classId).orElseThrow(() -> new RuntimeException("Cursul nu există!"));
        Enrollment enrollment = new Enrollment();
        enrollment.setStudent(student);
        enrollment.setDanceClass(danceClass);
        enrollment.setStatus(EnrollmentStatus.ACTIVE);
        enrollmentRepository.save(enrollment);
    }

    @Override
    public void unenrollStudent(Long studentId, Long classId) {
        Enrollment enrollment = enrollmentRepository.findByStudentIdAndDanceClassId(studentId, classId).orElseThrow(() -> new RuntimeException("Această înscriere nu există!"));

        enrollmentRepository.delete(enrollment);
    }
}