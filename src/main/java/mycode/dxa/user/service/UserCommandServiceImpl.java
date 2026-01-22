package mycode.dxa.user.service;

import mycode.dxa.user.dtos.CreateStudentDto;
import mycode.dxa.user.dtos.UserResponse;
import mycode.dxa.user.exceptions.EmailAlreadyInUseException;
import mycode.dxa.user.mappers.UserMapper;
import mycode.dxa.user.models.User;
import mycode.dxa.user.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder; // IMPORT NOU
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserCommandServiceImpl implements UserCommandService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder; // DEPENDINȚĂ NOUĂ


    public UserCommandServiceImpl(UserRepository userRepository, UserMapper userMapper,PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public UserResponse createStudent(CreateStudentDto dto) {
        if (userRepository.existsByEmail(dto.email())) {
            throw new EmailAlreadyInUseException();
        }
        User student = userMapper.mapCreateStudentDtoToUser(dto);
        student.setPassword(passwordEncoder.encode(dto.password()));
        User savedStudent = userRepository.save(student);
        return userMapper.mapToResponse(savedStudent);
    }
    @Override
    @Transactional
    public UserResponse updateUser(Long id, mycode.dxa.user.dtos.UpdateUserDto dto) {
        // 1. Căutăm userul
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));

        // 2. Aplicăm modificările
        userMapper.updateUserFromDto(dto, user);

        // 3. Salvăm (Hibernate face update automat la finalul tranzacției, dar save e explicit)
        User savedUser = userRepository.save(user);

        // 4. Returnăm răspunsul actualizat
        return userMapper.mapToResponse(savedUser);
    }
}