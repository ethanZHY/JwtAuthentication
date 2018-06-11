package com.demo.appserver.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.demo.appserver.model.AppUser;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import static com.demo.appserver.security.SecurityConstants.EXPIRATION_TIME;
import static com.demo.appserver.security.SecurityConstants.HEADER_STRING;
import static com.demo.appserver.security.SecurityConstants.SECRET;
import static com.demo.appserver.security.SecurityConstants.TOKEN_PREFIX;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    
    private AuthenticationManager authenticationManager;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager){
        this.authenticationManager = authenticationManager;
    }


    /**
     * Parse the user's credentials and issue them to the AuthenticationManager
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {

                try {
                    AppUser appUser = new ObjectMapper().readValue(request.getInputStream(), AppUser.class);
                    UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(appUser.getEmail(), appUser.getPassword(), new ArrayList<>());
                    return authenticationManager.authenticate(token);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
    }

    /**
     * Method used to generate JWT when a user successfully logs in.
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
            Authentication authResult) throws IOException, ServletException {
                
                String token = Jwts.builder()
                        .setSubject(((User) authResult.getPrincipal()).getUsername())
                        .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                        .signWith(SignatureAlgorithm.HS512, SECRET.getBytes())
                        .compact();

                response.addHeader(HEADER_STRING, TOKEN_PREFIX + token);

    }
}