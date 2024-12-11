package com.rest.rest_example.service;

import com.rest.rest_example.model.UserRequest;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    String registerUser(UserRequest userDetails);
}
