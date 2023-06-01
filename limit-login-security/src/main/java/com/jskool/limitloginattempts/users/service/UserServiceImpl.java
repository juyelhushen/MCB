package com.jskool.limitloginattempts.users.service;

import com.jskool.limitloginattempts.users.dao.RoleDao;
import com.jskool.limitloginattempts.users.dao.UserDao;
import com.jskool.limitloginattempts.users.dto.UserDto;
import com.jskool.limitloginattempts.users.model.Role;
import com.jskool.limitloginattempts.users.model.User;
import com.jskool.limitloginattempts.utils.MCBConstant;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    public static final int MAX_FAILED_ATTEMPTS = 3;  //limit attempt

    public static final long LOCK_TIME_DURATION = 300000;  //10 min

    @Autowired
    private final UserDao userDao;

    @Autowired
    private final RoleDao roleDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void saveUser(UserDto userDto) {
        Role role = roleDao.findByName(MCBConstant.Roles.USER);
        if (role == null) {
            role = roleDao.save(new Role(MCBConstant.Roles.USER));
        }

        User user = User.builder()
                .name(userDto.getName())
                .email(userDto.getEmail())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .enabled(true)
                .accountNonLocked(true)
                .failedAttempt(0)
                .lockTime(null)
                .roles(List.of(role)).build();
        userDao.save(user);
    }


    @Override
    public User findUserByEmail(String email) {
        return userDao.findUserByEmail(email);
    }

    @Override
    public void increaseFailedAttempts(User user) {
        int failedAttempts = user.getFailedAttempt() + 1;
        userDao.updateFailedAttempt(failedAttempts, user.getEmail());
    }

    @Override
    public void lockUser(User user) {
        user.setAccountNonLocked(false);
        user.setLockTime(LocalDateTime.now());
        userDao.save(user);
    }

    @Override
    public void unlockUser(User user) {
        LocalDateTime lockTime = user.getLockTime();
        if (lockTime != null) {
            long lockTimeInMills = lockTime.toInstant(ZoneOffset.UTC).toEpochMilli();
            long currentTimeMillis = LocalDateTime.now().atZone(ZoneOffset.UTC).toInstant().toEpochMilli();
            long unlockTimeMillis = lockTimeInMills + LOCK_TIME_DURATION;

            System.out.println(currentTimeMillis + " > " + unlockTimeMillis);
            if (currentTimeMillis > unlockTimeMillis) {
                System.out.println("dsjbfjhbfjdsh");
                user.setAccountNonLocked(true);
                user.setLockTime(null);
                user.setFailedAttempt(0);
                userDao.save(user);
            }
        }
    }

    @Override
    public void resetFailedAttempts(String email) {
        System.out.println(email);
        userDao.updateFailedAttempt(0, email);
    }
}
