package com.demo.appserver.service;

import java.util.Collections;

import com.demo.appserver.model.AppUser;
import com.demo.appserver.repository.AppUserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    
    @Autowired
    private AppUserRepository appUserRepository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        AppUser appUser = appUserRepository.findByEmail(email);
        if (appUser == null){
            throw new UsernameNotFoundException("User " + email + " not found");
        }
        return new User(appUser.getEmail(), appUser.getPassword(), Collections.emptyList());
	}

    
}