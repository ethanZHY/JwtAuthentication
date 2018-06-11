package com.demo.appserver.controller;

import javax.security.auth.message.callback.PrivateKeyCallback.Request;
import javax.transaction.Status;

import com.demo.appserver.model.AppUser;
import com.demo.appserver.repository.AppUserRepository;
import com.demo.appserver.model.SignUpResponse;

import org.apache.coyote.Response;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    private AppUserRepository appUserRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserController(AppUserRepository appUserRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.appUserRepository = appUserRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @PostMapping("/sign-up")
    public SignUpResponse signUp(@RequestBody AppUser user){
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        appUserRepository.save(user);
        return new SignUpResponse(true, "Sign-up succeed.");
    }

    @GetMapping("/hello")
    public String greeting(Request request){
        return "Hello World";
    }

}