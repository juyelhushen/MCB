package com.jskool.limitloginattempts.users.service;

import com.jskool.limitloginattempts.users.dto.UserDto;
import com.jskool.limitloginattempts.users.model.User;

import java.util.List;

public interface UserService {

    void saveUser(UserDto userDto);
    User findUserByEmail(String email);

    void increaseFailedAttempts(User user);
    void lockUser(User user);

    boolean unlockUser(User user);

    void resetFailedAttempts(String email);

    List<User> getExpiredLockedUsers();
}
