package mycode.dxa.user.service;

import mycode.dxa.user.dtos.UserListResponse;
import mycode.dxa.user.mappers.UserMapper;
import mycode.dxa.user.models.User;
import mycode.dxa.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class UserQueryServiceImpl implements UserQueryService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserQueryServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public UserListResponse getAllUsers(String search, String status, Long courseId) {
        List<User> students = userRepository.findStudentsWithFilters(search, status, courseId);
        return userMapper.mapUserListToUserListResponse(students);
    }
}