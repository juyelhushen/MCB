package com.mcb.users.service;

import com.mcb.users.model.User;
import com.mcb.users.payload.AuthRequest;
import com.mcb.users.payload.JwtRegister;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface UserService {
    ResponseEntity<String> signup(JwtRegister register);

    ResponseEntity<?> login(AuthRequest request);

    ResponseEntity<String> enableDisableUser(@PathVariable long id);

    ResponseEntity<String> blockedUser(@PathVariable long id);
    User findByEmail(String email);

    void increaseFailedAttempts(User user);

    void lock(User user);

    boolean unlock(User user);
}
