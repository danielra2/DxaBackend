package mycode.dxa.user.service;

import mycode.dxa.user.dtos.CreateStudentDto;
import mycode.dxa.user.dtos.UpdateUserDto;
import mycode.dxa.user.dtos.UserResponse;

public interface UserCommandService {
    UserResponse createStudent(CreateStudentDto dto);
    UserResponse updateUser(Long id, UpdateUserDto dto);
}
