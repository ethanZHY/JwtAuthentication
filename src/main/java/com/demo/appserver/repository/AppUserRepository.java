package com.demo.appserver.repository;

import com.demo.appserver.model.AppUser;

import org.springframework.data.repository.CrudRepository;

public interface AppUserRepository extends CrudRepository<AppUser, Long>{
    AppUser findByUsername(String username);
    AppUser findByEmail(String email);
}