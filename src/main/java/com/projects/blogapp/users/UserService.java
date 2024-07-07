package com.projects.blogapp.users;

import com.projects.blogapp.security.JWTService;
import com.projects.blogapp.users.DTOs.CreateUserRequest;
import lombok.NonNull;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired // initializes a constructor of user repository
    private UserRepository userRepository;
@Autowired
    ModelMapper modelMapper;
@Autowired
private PasswordEncoder passwordEncoder;
@Autowired
private JWTService jwtService;

    public UsersEntity createuser(CreateUserRequest u){
        UsersEntity newuser = modelMapper.map(u, UsersEntity.class);
      //  var newuser = UsersEntity.builder().username("Abdullah").email("abk@gmail.com").build();
        newuser.setPassword(passwordEncoder.encode(u.getPassword()));
        return userRepository.save(newuser);
    }

    public UsersEntity getUser(String username){

       return userRepository.findByUsername(username).orElseThrow(()->new UserNotFoundException(username));
    }

    public UsersEntity getUser(Long userId){

        return userRepository.findById(userId).orElseThrow(()->new UserNotFoundException(userId));
    }

    public UsersEntity loginUser(String username, @NonNull String password) {
        var user = userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException(username));
        var passMatch = passwordEncoder.matches(password, user.getPassword());
        if (!passMatch)
            throw new InvalidCredentialException();
        return user;
    }

    public static class UserNotFoundException extends IllegalArgumentException{
        public UserNotFoundException(String username){
            super("User " + username + " not found.");
        }
        public UserNotFoundException(Long id){
            super("User with id " + id + " not found.");
        }
    }

    public static class InvalidCredentialException extends IllegalArgumentException{
        public InvalidCredentialException(){
            super("Invalid username or Password.");
        }
    }
}
