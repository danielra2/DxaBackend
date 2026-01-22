package mycode.dxa.user.service;

import mycode.dxa.user.dtos.UserListResponse;
import mycode.dxa.user.mappers.UserMapper;
import mycode.dxa.user.models.User;
import mycode.dxa.user.models.UserType;
import mycode.dxa.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // <--- IMPORT IMPORTANT

import java.util.List;

@Service
@Transactional(readOnly = true) // <--- LINIA CARE REZOLVĂ PROBLEMA
public class UserQueryServiceImpl implements UserQueryService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserQueryServiceImpl(UserRepository userRepository, UserMapper userMapper){
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public UserListResponse getAllUsers() {
        List<User> students = userRepository.findAllByUserType(UserType.STUDENT);
        return userMapper.mapUserListToUserListResponse(students);
    }
}