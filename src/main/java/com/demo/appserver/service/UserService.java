package com.demo.appserver.service;

import com.demo.appserver.model.AppUser;
import com.demo.appserver.repository.AppUserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class UserService {

    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
	private BCryptPasswordEncoder bcryptPasswordEncoder;

    public void updateUser(AppUser user) {
        appUserRepository.save(user);
    }

    public AppUser findByEmail(String email) {
        return appUserRepository.findByEmail(email);
    }

    public void changePassword(AppUser user, String password) {
		user.setPassword(this.bcryptPasswordEncoder.encode(password));
		this.updateUser(user);
    }
}
