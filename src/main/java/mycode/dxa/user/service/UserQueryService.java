package mycode.dxa.user.service;

import mycode.dxa.user.dtos.UserListResponse;

public interface UserQueryService {
    UserListResponse getAllUsers(String search, String status, Long courseId);
}