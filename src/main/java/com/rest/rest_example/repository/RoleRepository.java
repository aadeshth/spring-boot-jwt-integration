package com.rest.rest_example.repository;

import com.rest.rest_example.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role,Integer> {
    boolean findByName(String name);

}
