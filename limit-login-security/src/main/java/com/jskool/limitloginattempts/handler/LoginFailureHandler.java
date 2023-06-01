package com.jskool.limitloginattempts.handler;

import com.jskool.limitloginattempts.users.model.User;
import com.jskool.limitloginattempts.users.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class LoginFailureHandler implements AuthenticationFailureHandler {

    private final UserService userService;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response,
                                        AuthenticationException exception)
            throws IOException, ServletException {
        String email = request.getParameter("username");
        User user = userService.findUserByEmail(email);
        if (user != null) {
            if (user.isEnabled() && user.isAccountNonLocked()) {
                if (user.getFailedAttempt() < 2) {
                    userService.increaseFailedAttempts(user);
                } else {
                    userService.lockUser(user);
                    exception = new LockedException("Your account has been locked due to 3 failed attempts" +
                            ". It will be unlocked after 24 hours");
                    request.getSession().setAttribute("lockedExceptionMessage", exception.getMessage());
                }
            } else if (!user.isAccountNonLocked()) {
                userService.unlockUser(user);
                exception = new LockedException("Your account has been unlocked. Please try to login again.");
                request.getSession().setAttribute("lockedExceptionMessage", exception.getMessage());
            }
        }

        response.sendRedirect("/login?error"); // Redirect to the desired URL after authentication failure
    }
}

