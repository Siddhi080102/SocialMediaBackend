package com.app.twitter.controllers;

import com.app.twitter.entities.dtos.security.SigninDto;
import com.app.twitter.entities.dtos.security.SignupDto;
import com.app.twitter.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> signin(@RequestBody SigninDto signinDto) {
        return userService.signin(signinDto);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> register(@RequestBody SignupDto signupDto) {
        return userService.register(signupDto);
    }


    @GetMapping("/user")
    public ResponseEntity<?> getUserById(@RequestParam(name = "userID") Long id) {
        return userService.getUserById(id);
    }

    @GetMapping("/users")
    public ResponseEntity<?> getAllUsers() {
        return userService.getAllUsers();
    }


}
