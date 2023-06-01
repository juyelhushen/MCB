package com.jskool.limitloginattempts.users.service;

import com.jskool.limitloginattempts.users.dto.UserDto;
import com.jskool.limitloginattempts.users.model.User;

public interface UserService {

    void saveUser(UserDto userDto);
    User findUserByEmail(String email);

    void increaseFailedAttempts(User user);
    void lockUser(User user);

    void unlockUser(User user);

    void resetFailedAttempts(String email);
}
