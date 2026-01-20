package mycode.dxa.user.service;

import mycode.dxa.user.dtos.CreateStudentDto;
import mycode.dxa.user.dtos.UserResponse;
import mycode.dxa.user.exceptions.EmailAlreadyInUseException;
import mycode.dxa.user.exceptions.EmailAlreadyInUseException;
import mycode.dxa.user.mappers.UserMapper;
import mycode.dxa.user.models.User;
import mycode.dxa.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserCommandServiceImpl implements UserCommandService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserCommandServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }
    @Override
    @Transactional
    public UserResponse createStudent(CreateStudentDto dto) {
        if (userRepository.existsByEmail(dto.email())) {
            throw new EmailAlreadyInUseException();
        }
        User student = userMapper.mapCreateStudentDtoToUser(dto);
        User savedStudent = userRepository.save(student);
        return userMapper.mapToResponse(savedStudent);
    }
}