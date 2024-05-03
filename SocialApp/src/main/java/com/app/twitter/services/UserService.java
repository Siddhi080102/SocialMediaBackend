package com.app.twitter.services;

import com.app.twitter.entities.dtos.error.ErrResponse;
import com.app.twitter.entities.dtos.security.SigninDto;
import com.app.twitter.entities.dtos.security.SignupDto;
import com.app.twitter.entities.dtos.user.UserDTO;
import com.app.twitter.entities.User;
import com.app.twitter.repositories.UserRepository;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public ResponseEntity<?> register(SignupDto signupDto) {
        Optional<User> existingUser = userRepository.findByEmail(signupDto.getEmail());
        if (existingUser.isPresent()) {
            return ResponseEntity.ok(new ErrResponse("Forbidden, Account already exists"));
        }
        User user = new User();
        user.setName(signupDto.getName());
        user.setEmail(signupDto.getEmail());
        user.setPassword(signupDto.getPassword());
        userRepository.save(user);
        return ResponseEntity.ok("Account Creation Successful");
    }

    public ResponseEntity<?> signin(SigninDto signinDto) {
        Optional<User> user = userRepository.findByEmail(signinDto.getEmail()); // <>
        if (user.isEmpty()) {
            return ResponseEntity.ok(new ErrResponse("User does not exist"));
        } else if (user.get().getPassword() != null && user.get().getPassword().equals(signinDto.getPassword())) {
            return ResponseEntity.ok("Login Successful");
        } else {
            return ResponseEntity.ok(new ErrResponse("Username/Password Incorrect"));
        }
    }


    public ResponseEntity<?> getUserById(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            UserDTO userDTO = new UserDTO();
            userDTO.setUserID(user.get().getUserID());
            userDTO.setName(user.get().getName());
            userDTO.setEmail(user.get().getEmail());
            return ResponseEntity.ok(userDTO);
        } else {
            return ResponseEntity.ok(new ErrResponse("User does not exist"));
        }
    }


    public ResponseEntity<?> getAllUsers() {
        List<User> users = userRepository.findAll();
        List<UserDTO> userResponseDtosList = new ArrayList<>();
        for (User user : users) {
            UserDTO userDTO = new UserDTO();
            userDTO.setUserID(user.getUserID());
            userDTO.setName(user.getName());
            userDTO.setEmail(user.getEmail());

            userResponseDtosList.add(userDTO);
        }
        return new ResponseEntity<>(userResponseDtosList, HttpStatusCode.valueOf(200));
    }
}
