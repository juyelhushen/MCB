package com.mcb.users.controller;

import com.mcb.constant.MCBConstant;
import com.mcb.users.payload.AuthRequest;
import com.mcb.users.payload.JwtRegister;
import com.mcb.users.service.UserService;
import com.mcb.utils.MCBUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
@RequiredArgsConstructor
public class UserControllerImp implements UserController {

    @Autowired
    private final UserService userService;

    @Override
    public ResponseEntity<String> signup(JwtRegister register) {

        try {
            return userService.signup(register);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return MCBUtils.getResponse(MCBConstant.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<?> login(AuthRequest request) {
        try {
            return userService.login(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return MCBUtils.getResponse(MCBConstant.INVALID_DATA, HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity<String> enableDisableUser(@PathVariable long id) {
        try {
            return userService.enableDisableUser(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return MCBUtils.getResponse(MCBConstant.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> blockUser(@PathVariable long id) {
        try {
            return userService.blockedUser(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return MCBUtils.getResponse(MCBConstant.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }




}


