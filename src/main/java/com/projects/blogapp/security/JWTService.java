package com.projects.blogapp.security;

import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.stereotype.Service;
import com.auth0.jwt.JWT;

import java.util.Date;

@Service
public class JWTService {
    private static final String JWT_KEY = "Sjjdkkjwo@oeooeksdjpoiu";
    private Algorithm algorithm = Algorithm.HMAC256(JWT_KEY);

    public String create_Jwt(Long userID){
        return JWT.create()
                .withSubject(userID.toString())
                .withIssuedAt(new Date())
             //   .withExpiresAt()  //TODO: setup and expiry parameter
                .sign(algorithm);
    }

    public Long retrieveUserId(String jwtString){

        var decodedJWT = JWT.decode(jwtString);
        var userID = Long.valueOf(decodedJWT.getSubject());
        return userID;
    }

}
