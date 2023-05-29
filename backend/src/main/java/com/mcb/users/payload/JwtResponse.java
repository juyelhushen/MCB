package com.mcb.users.payload;

import com.mcb.users.model.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtResponse {

    private String firstname;
    private String lastname;
    private String token;
    private Role roles;
    private String message;


}
