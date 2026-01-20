package mycode.dxa.user.service;

import mycode.dxa.user.dtos.UserListResponse;
import mycode.dxa.user.mappers.UserMapper;
import mycode.dxa.user.models.User;
import mycode.dxa.user.models.UserType;
import mycode.dxa.user.repository.UserRepository;

import java.util.List;

public class UserQueryServiceImpl implements UserQueryService {
    private UserRepository userRepository;
    private UserMapper userMapper;

    public UserQueryServiceImpl(UserRepository userRepository,UserMapper userMapper){
        this.userRepository=userRepository;
        this.userMapper=userMapper;
    }


    @Override
    public UserListResponse getAllUsers() {
        List<User> students = userRepository.findAllByUserType(UserType.STUDENT);
        return userMapper.mapUserListToUserListResponse(students);
    }
}
