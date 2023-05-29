package com.mcb.users.model;

import com.mcb.users.model.Role;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String firstname;
    private String lastname;
    private String email;
    private String mobilenumber;
    private String password;
    private boolean nonLocked;
    private boolean enabled;
    @Enumerated(EnumType.STRING)
    private Role role;
    private LocalDateTime createdOn;
    private int failedAttempt;
    private LocalDateTime lockTime;

}