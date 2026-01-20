package mycode.dxa.user.service;

import mycode.dxa.user.dtos.UserListResponse;
import org.springframework.stereotype.Component;


public interface UserQueryService {
    UserListResponse getAllUsers();
}
