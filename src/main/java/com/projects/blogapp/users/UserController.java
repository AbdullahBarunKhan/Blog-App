package com.projects.blogapp.users;

import com.projects.blogapp.commons.dto.ErrorResponse;
import com.projects.blogapp.security.JWTService;
import com.projects.blogapp.users.DTOs.CreateUserRequest;
import com.projects.blogapp.users.DTOs.UserResponse;
import com.projects.blogapp.users.DTOs.LoginUserRequest;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/users")
public class UserController {

    @GetMapping
    public String getuser(){
        return "user";
    }

    public UserController(UserService userService, ModelMapper modelMapper,JWTService jwtService) {
        this.userService = userService;
        this.modelMapper = modelMapper;
        this.jwtService = jwtService;
    }

    private final UserService userService;
    private final ModelMapper modelMapper;
    private final JWTService jwtService;

    @PostMapping("")
    ResponseEntity<UserResponse> signupUser(@RequestBody CreateUserRequest request){
        UsersEntity savedUser = userService.createuser(request);

        URI savedUserURI = URI.create("/users/" + savedUser.getId());
        var userResponse = modelMapper.map(savedUser, UserResponse.class);
        userResponse.setToken(jwtService.create_Jwt(savedUser.getId()));

        return ResponseEntity.created(savedUserURI).body(userResponse);
    }

    @PostMapping("/login")
    ResponseEntity<UserResponse> loginUser(@RequestBody LoginUserRequest request){
        UsersEntity saveduser = userService.loginUser(request.getUsername(),request.getPassword());
        var userResponse = modelMapper.map(saveduser, UserResponse.class);
        userResponse.setToken(jwtService.create_Jwt(saveduser.getId()));

        return ResponseEntity.ok(userResponse);
    }

    @ExceptionHandler({UserService.UserNotFoundException.class,
                        UserService.InvalidCredentialException.class})
    ResponseEntity<ErrorResponse> handleUserExceptions(Exception ex){
        String message;
        HttpStatus status;

        if(ex instanceof UserService.UserNotFoundException){
            message=ex.getMessage();
            status=HttpStatus.NOT_FOUND;
        } else if (ex instanceof UserService.InvalidCredentialException) {
            message=ex.getMessage();
            status=HttpStatus.UNAUTHORIZED;

        } else{
            message="Something Went Wrong";
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        ErrorResponse response = ErrorResponse.builder().message(message).build();

        return ResponseEntity.status(status).body(response);
    }

}
