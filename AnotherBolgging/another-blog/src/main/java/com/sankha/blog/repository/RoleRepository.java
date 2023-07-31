package com.sankha.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sankha.blog.entity.Role;


public interface RoleRepository  extends JpaRepository<Role, Integer>{

}
