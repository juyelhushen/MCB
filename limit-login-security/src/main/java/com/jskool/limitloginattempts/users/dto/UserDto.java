package com.jskool.limitloginattempts.users.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private Long id;

    @NotEmpty(message = "Please enter valid name")
    private String name;

    @NotEmpty(message = "Please enter valid email")
    private String email;

    @NotEmpty(message = "Please enter valid password")
    private String password;

    private boolean enabled;

    private boolean accountNonLocked;

    private int failedAttempt;

    private LocalDateTime lockTime;

}
