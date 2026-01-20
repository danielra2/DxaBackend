package mycode.dxa.user.controller;

import lombok.extern.slf4j.Slf4j;
import mycode.dxa.user.dtos.CreateStudentDto;
import mycode.dxa.user.dtos.UserListResponse;
import mycode.dxa.user.dtos.UserResponse;
import mycode.dxa.user.service.UserCommandService;
import mycode.dxa.user.service.UserQueryService;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class UserController implements UserControllerApi {

    private final UserCommandService userCommandService;
    private final UserQueryService userQueryService;

    public UserController(UserCommandService userCommandService, UserQueryService userQueryService) {
        this.userCommandService = userCommandService;
        this.userQueryService = userQueryService;
    }

    @Override
    public UserListResponse getAllUsers() {
        log.info("GET /api/users requested");
        return userQueryService.getAllUsers();
    }

    @Override
    public UserResponse createStudent(CreateStudentDto createStudentDto) {
        log.info("POST /api/users/student - creating student: {}", createStudentDto.email());
        return userCommandService.createStudent(createStudentDto);
    }
}