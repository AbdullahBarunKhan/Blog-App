package com.projects.blogapp.security;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class JWTSecurityTest {

    JWTService jwtService = new JWTService();

    @Test
    void cancreateJWTForUserID(){
        var jwt = jwtService.create_Jwt(1001L);

        assertNotNull(jwt);


    }
}
