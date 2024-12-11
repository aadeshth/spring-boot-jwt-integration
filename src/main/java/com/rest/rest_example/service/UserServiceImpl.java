package com.rest.rest_example.service;

import com.rest.rest_example.entity.Role;
import com.rest.rest_example.exception.UserNotFoundException;
import com.rest.rest_example.model.UserRequest;
import com.rest.rest_example.repository.RoleRepository;
import com.rest.rest_example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import com.rest.rest_example.entity.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserServiceImpl(RoleRepository roleRepository, UserRepository userRepository) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
    }

    @Override
    public String registerUser(UserRequest userRequest) {
        User user= new User();
        user.setUsername(userRequest.getUsername());
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        Set<Role> roleSet = new HashSet<>(roleRepository.findAllById(userRequest.getRoles()));
        user.setRoles(roleSet);
        userRepository.save(user);
        return "User Registered!";
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userOptional = userRepository.findByUsername(username);
        if(userOptional.isEmpty())
            throw new UserNotFoundException("User Not Found!");
        User user = userOptional.get();
        return org.springframework.security.core.userdetails.User.
                builder().username(user.getUsername())
                .password(user.getPassword())
                .authorities(user.getRoles().stream().map(role -> "ROLE_"+role.getName()).toArray(String[]::new))
                .build();
    }
}
