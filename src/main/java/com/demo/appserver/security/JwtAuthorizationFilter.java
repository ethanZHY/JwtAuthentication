package com.demo.appserver.security;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import io.jsonwebtoken.Jwts;

import static com.demo.appserver.security.SecurityConstants.HEADER_STRING;
import static com.demo.appserver.security.SecurityConstants.TOKEN_PREFIX;
import static com.demo.appserver.security.SecurityConstants.SECRET;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    public JwtAuthorizationFilter(AuthenticationManager authManager) {
        super(authManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
                
                String authHeader = request.getHeader(HEADER_STRING);
                if (authHeader == null || !authHeader.startsWith(TOKEN_PREFIX)){
                    chain.doFilter(request, response);
                    return;
                }

                UsernamePasswordAuthenticationToken token = getAuthToken(request);
                SecurityContextHolder.getContext().setAuthentication(token);
                chain.doFilter(request, response);
    }


    private UsernamePasswordAuthenticationToken getAuthToken(HttpServletRequest request) {
        String authHeader = request.getHeader(HEADER_STRING);
        if (authHeader != null){
            String user = Jwts.parser()
                    .setSigningKey(SECRET.getBytes())
                    .parseClaimsJws(authHeader.replace(TOKEN_PREFIX, ""))
                    .getBody()
                    .getSubject();

            if (user != null) {
                return new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>());
            }
        }
        return null;
    }
}