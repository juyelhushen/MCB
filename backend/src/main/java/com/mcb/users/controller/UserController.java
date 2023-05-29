package com.mcb.users.controller;

import com.mcb.users.payload.AuthRequest;
import com.mcb.users.payload.JwtRegister;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RequestMapping("/user")
@CrossOrigin(origins = "*")
public interface UserController {

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody(required = true) JwtRegister register);

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody(required = true) AuthRequest request);


    @GetMapping("/enabledisable/{id}")
    public ResponseEntity<String> enableDisableUser(@PathVariable long id);

    @GetMapping("/block/{id}")
    public ResponseEntity<String> blockUser(@PathVariable long id);



}

