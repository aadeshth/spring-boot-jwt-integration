package com.rest.rest_example.controller;

import com.rest.rest_example.entity.Role;
import com.rest.rest_example.entity.User;
import com.rest.rest_example.model.UserRequest;
import com.rest.rest_example.repository.RoleRepository;
import com.rest.rest_example.repository.UserRepository;
import com.rest.rest_example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/api/users")
public class UserRegistrationController {

    private final UserService userService;

    public UserRegistrationController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<Object> register(@RequestBody UserRequest user){
       return ResponseEntity.ok().body(userService.registerUser(user));
    }
}
