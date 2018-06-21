package com.demo.appserver.controller;

import java.util.UUID;

import javax.security.auth.message.callback.PrivateKeyCallback.Request;

import com.demo.appserver.model.AppUser;
import com.demo.appserver.model.ResetPasswordForm;
import com.demo.appserver.service.EmailService;
import com.demo.appserver.service.UserService;
import com.demo.appserver.model.SignUpResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private EmailService emailService;

    @PostMapping("/sign-up")
    public SignUpResponse signUp(@RequestBody AppUser user){
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userService.updateUser(user);
        return new SignUpResponse(true, "Sign-up succeed.");
    }

    @GetMapping("/hello")
    public String greeting(Request request){
        return "Hello World";
    }

    @GetMapping("/send-tmp-token")
    public void sendTokenEmail(@RequestParam(value="email") String email) {
        AppUser user = this.userService.findByEmail(email);
        if (user != null) {
            String token = UUID.randomUUID().toString();
            user.setPassword(token);
            userService.updateUser(user);
            emailService.sendComplexEmail(email, token);
        }
    }

    @PostMapping("/reset-password")
    public String resetPassword(@RequestBody ResetPasswordForm form) {
        AppUser user = this.userService.findByEmail(form.getEmail());
        if (user == null) {
            return "User not found with email " + form.getEmail();
        } else if (form.getPassword() != user.getPassword()) {
            return "Temperory password does not match!";
        } else {
            this.userService.changePassword(user, form.getNewPassword());
            return "Password successfully reset";
        }
    }

}