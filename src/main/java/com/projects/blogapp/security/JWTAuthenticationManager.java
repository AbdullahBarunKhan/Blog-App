package com.projects.blogapp.security;

import com.projects.blogapp.users.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.parameters.P;

public class JWTAuthenticationManager implements AuthenticationManager {

    private JWTService jwtService;
    private UserService userService;

    public JWTAuthenticationManager(JWTService jwtService, UserService userService) {
        this.jwtService = jwtService;
        this.userService = userService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        if(authentication instanceof JWTAuthentication){
            var jwtAuthentication = (JWTAuthentication) authentication;
            var jwt = jwtAuthentication.getCredentials();
            var userId = jwtService.retrieveUserId((String) jwt);
            var userEntity = userService.getUser(userId);

            jwtAuthentication.usersEntity = userEntity;

            jwtAuthentication.setAuthenticated(true);


            return jwtAuthentication;
        }

       throw new IllegalAccessError("cannot authenticate with non jwt authenticator");
    }
}
