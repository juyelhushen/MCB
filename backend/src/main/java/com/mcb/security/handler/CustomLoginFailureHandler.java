package com.mcb.security.handler;

import com.mcb.constant.MCBConstant;
import com.mcb.users.model.User;
import com.mcb.users.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomLoginFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Autowired
    private UserService userService;
    @Override
    public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response,
                                        AuthenticationException exception)
            throws IOException, ServletException {

        String email = request.getParameter("email");
        User user = userService.findByEmail(email);

        if (user != null) {
            if (user.isEnabled() && user.isNonLocked()) {
                if (user.getFailedAttempt() < MCBConstant.MAX_FAILED_ATTEMPTS - 1) {
                    userService.increaseFailedAttempts(user);
                } else {
                    userService.lock(user);
                    exception = new LockedException("Your account has been locked due to 3 failed attempts" +
                            ". It will be unlocked after 24 hours");
                }
            } else if (!user.isNonLocked()) {
                if (userService.unlock(user)) {
                    exception = new LockedException("Your account has been unlocked. Please try to login again.");
                }

            }
        }

        super.setDefaultFailureUrl("/login/error");
        super.onAuthenticationFailure(request, response, exception);
    }
}
