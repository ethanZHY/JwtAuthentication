package com.demo.appserver.security;


public class SecurityConstants {
    public static final String SECRET = "SecurityKeyToJwts";
    public static final String HEADER_STRING = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final long EXPIRATION_TIME = 600000; //in millisecond, 10 minute
    public static final String SIGN_UP_URL = "/users/sign-up";
}