package com.jskool.limitloginattempts.cron;

import com.jskool.limitloginattempts.users.model.User;
import com.jskool.limitloginattempts.users.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class UserAccountUnlockJob {

    private final UserService userService;

    @Scheduled(cron = "0 */2 * * * *")   //Every five minutes after it's going to execute
    public void unlockUserAccount() {
        List<User> lockedUsers = userService.getExpiredLockedUsers();
        for (User user : lockedUsers) {
            System.out.println("inside cron operation"+ LocalDateTime.now());
            userService.unlockUser(user);
        }
    }
}
