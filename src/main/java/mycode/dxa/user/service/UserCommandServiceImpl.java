package mycode.dxa.user.service;

import mycode.dxa.classes.models.DanceClass;
import mycode.dxa.classes.repository.DanceClassRepository;
import mycode.dxa.enrollment.models.Enrollment;
import mycode.dxa.enrollment.models.EnrollmentStatus;
import mycode.dxa.enrollment.repository.EnrollmentRepository;
import mycode.dxa.user.dtos.CreateStudentDto;
import mycode.dxa.user.dtos.UserResponse;
import mycode.dxa.user.exceptions.EmailAlreadyInUseException;
import mycode.dxa.user.mappers.UserMapper;
import mycode.dxa.user.models.User;
import mycode.dxa.user.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserCommandServiceImpl implements UserCommandService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    // Dependințe noi pentru gestionarea cursurilor
    private final DanceClassRepository danceClassRepository;
    private final EnrollmentRepository enrollmentRepository;

    public UserCommandServiceImpl(UserRepository userRepository,
                                  UserMapper userMapper,
                                  PasswordEncoder passwordEncoder,
                                  DanceClassRepository danceClassRepository,
                                  EnrollmentRepository enrollmentRepository) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
        this.danceClassRepository = danceClassRepository;
        this.enrollmentRepository = enrollmentRepository;
    }

    @Override
    @Transactional
    public UserResponse createStudent(CreateStudentDto dto) {
        // 1. Verificare unicitate email
        if (userRepository.existsByEmail(dto.email())) {
            throw new EmailAlreadyInUseException();
        }

        // 2. Mapare și setări de bază user
        User student = userMapper.mapCreateStudentDtoToUser(dto);
        student.setPassword(passwordEncoder.encode(dto.password()));

        // 3. Logică pentru Comentariu Plată (Motiv reducere / Notă)
        // Dacă s-a introdus o sumă, gestionăm comentariul
        if (dto.lastPaymentAmount() != null && dto.lastPaymentAmount() > 0) {
            // Dacă din frontend vine un comentariu specific, îl salvăm pe acela
            if (dto.lastPaymentComment() != null && !dto.lastPaymentComment().isBlank()) {
                student.setLastPaymentComment(dto.lastPaymentComment());
            } else {
                // Altfel punem unul default
                student.setLastPaymentComment("Plată inițială la înscriere");
            }
        }

        // 4. Salvarea efectivă a studentului în DB
        User savedStudent = userRepository.save(student);

        // 5. Înscriere automată la cursurile selectate
        if (dto.enrolledClassIds() != null && !dto.enrolledClassIds().isEmpty()) {
            List<DanceClass> selectedClasses = danceClassRepository.findAllById(dto.enrolledClassIds());

            for (DanceClass danceClass : selectedClasses) {
                Enrollment enrollment = new Enrollment();
                enrollment.setStudent(savedStudent);
                enrollment.setDanceClass(danceClass);

                // Important: Data de expirare a cursului o setăm egală cu cea a abonamentului general
                enrollment.setExpirationDate(dto.subscriptionExpirationDate());
                enrollment.setStatus(EnrollmentStatus.ACTIVE);

                enrollmentRepository.save(enrollment);
            }
        }

        return userMapper.mapToResponse(savedStudent);
    }

    @Override
    @Transactional
    public UserResponse updateUser(Long id, mycode.dxa.user.dtos.UpdateUserDto dto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));

        // Mapăm doar câmpurile care vin în DTO
        userMapper.updateUserFromDto(dto, user);

        // Salvăm utilizatorul
        User savedUser = userRepository.save(user);

        // IMPORTANT: Returnăm răspunsul fără a declanșa alte logici automate de update pe înscrieri aici,
        // deoarece reînnoirea cursurilor se face prin endpoint-ul dedicat de Enrollments.
        return userMapper.mapToResponse(savedUser);
    }
}