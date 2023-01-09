package com.backendproject.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backendproject.blog.entities.Role;

public interface RoleRepo extends JpaRepository<Role, Integer>{

}
