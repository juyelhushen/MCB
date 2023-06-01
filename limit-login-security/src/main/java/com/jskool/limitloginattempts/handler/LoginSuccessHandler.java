package com.jskool.limitloginattempts.handler;

import com.jskool.limitloginattempts.users.model.CustomUserDetails;
import com.jskool.limitloginattempts.users.model.User;
import com.jskool.limitloginattempts.users.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final UserService userService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        User user = userDetails.getUser();

        if (user.getFailedAttempt() > 0) {
            System.out.println("i am in suucess auuth handle-"+user.getEmail());
            userService.resetFailedAttempts(user.getEmail());
        }

        // Perform any additional tasks upon successful login

        response.sendRedirect("/user/"); // Redirect to the desired URL after successful login
    }
}

