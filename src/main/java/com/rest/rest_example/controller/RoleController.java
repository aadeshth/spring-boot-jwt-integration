package com.rest.rest_example.controller;

import com.rest.rest_example.entity.Role;
import com.rest.rest_example.model.AuthResponse;
import com.rest.rest_example.model.LoginDto;
import com.rest.rest_example.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
public class RoleController {

    @Autowired
    RoleRepository roleRepository;
    @PostMapping
    public ResponseEntity<Role> addRole(@RequestBody Role roleRequest)
    {
       if(roleRepository.findByName(roleRequest.getName()))
        return ResponseEntity.badRequest().build();
    return ResponseEntity.ok(roleRepository.save(roleRequest));
    }

    @GetMapping
    public ResponseEntity<List<Role>> getAllRoles()
    {
        return ResponseEntity.ok(roleRepository.findAll());
    }
}
