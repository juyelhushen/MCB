package com.mcb.users.service;

import com.mcb.constant.MCBConstant;
import com.mcb.security.jwt.JwtAuthFilter;
import com.mcb.security.jwt.JwtUtils;
import com.mcb.users.impli.UserDetailsServiceImp;
import com.mcb.users.model.Role;
import com.mcb.users.model.User;
import com.mcb.users.payload.AuthRequest;
import com.mcb.users.payload.JwtRegister;
import com.mcb.users.payload.JwtResponse;
import com.mcb.users.repository.UserRepository;
import com.mcb.utils.MCBUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImp implements UserService {

    @Autowired
    private final UserRepository repository;
    private final JwtUtils jwtUtils;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsServiceImp userDetailsService;
    private final JwtAuthFilter filter;


    @Override
    public ResponseEntity<String> signup(JwtRegister register) {
        try {
            if (signupValidate(register)) {
                User user = repository.findByEmail(register.getEmail());
                if (Objects.isNull(user)) {
                    repository.save(register(register));
                    return MCBUtils.getResponse("Successfully registered.", HttpStatus.OK);
                } else {
                    return MCBUtils.getResponse("Email already Exits", HttpStatus.BAD_REQUEST);
                }
            } else {
                return MCBUtils.getResponse(MCBConstant.INVALID_DATA, HttpStatus.BAD_REQUEST);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return MCBUtils.getResponse(MCBConstant.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private User register(JwtRegister register) {
        return User.builder()
                .firstname(register.getFirstname())
                .lastname(register.getLastname())
                .email(register.getEmail())
                .mobilenumber(register.getMobilenumber())
                .password(passwordEncoder.encode(register.getPassword()))
                .nonLocked(true)
                .enabled(true)
                .role(Role.USER)
                .createdOn(LocalDateTime.now())
                .failedAttempt(0)
                .lockTime(null)
                .build();

    }

    private boolean signupValidate(JwtRegister register) {
        return register.getFirstname() != null &&
                register.getLastname() != null &&
                register.getEmail() != null &&
                register.getPassword() != null &&
                register.getMobilenumber() != null;
    }

    @Override
    public ResponseEntity<?> login(AuthRequest request) {
        log.info("inside login");
        try {
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(), request.getPassword()));
            if (auth.isAuthenticated()) {
                User user = repository.findByEmail(request.getEmail());
//                UserDetails user = userDetailsService.loadUserByUsername(request.getEmail());
                if (user.isEnabled()) {
                    SecurityContextHolder.getContext().setAuthentication(auth);
                    String jwtToken = jwtUtils.generateToken(user);
//                    UserDetailsImp userDetails = (UserDetailsImp)  auth.getPrincipal();
//                    List<String> roles =userDetails.getAuthorities().stream().ma
//                    JwtResponse token = JwtResponse.builder().token(jwtToken).build();


                    return ResponseEntity.ok(new JwtResponse(user.getFirstname(), user.getLastname(), jwtToken, user.getRole(), "Login Successfully"));
//                    return LibUtils.getResponse("Successfully Logged in.",HttpStatus.OK);
                } else {
                    return MCBUtils.getResponse("You account has disabled, Please contact admin.", HttpStatus.BAD_REQUEST);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return MCBUtils.getResponse("Wrong Credentials", HttpStatus.BAD_REQUEST);
    }




    @Override
    public ResponseEntity<String> enableDisableUser(@PathVariable long id) {
        try {
            User user = repository.findById(id).get();
            if (user.isEnabled()) {
                repository.disableUser(user.getId());
                return new ResponseEntity<String>("success", HttpStatus.OK);
            } else {
                user.setEnabled(true);
                repository.save(user);
                return new ResponseEntity<String>("User account is activated", HttpStatus.OK);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(MCBConstant.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> blockedUser(@PathVariable long id) {
        try {
            User user = repository.findById(id).get();
            if (user.isNonLocked()) {
                repository.blockUser(id);
                return new ResponseEntity<String>("success", HttpStatus.OK);
            } else {
                user.setNonLocked(true);
                repository.save(user);
                return new ResponseEntity<String>("User account is unblocked.", HttpStatus.OK);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(MCBConstant.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public User findByEmail(String email) {
        return repository.findByEmail(email);
    }

    @Override
    public void increaseFailedAttempts(User user) {
        int newFailedAttempt = user.getFailedAttempt() + 1;
        repository.updateFailedAttempt(newFailedAttempt,user.getEmail());
    }

    @Override
    public void lock(User user) {
        user.setNonLocked(false);
        user.setLockTime(LocalDateTime.now());
        repository.save(user);
    }

    @Override
    public boolean unlock(User user) {
        return false;
    }
}