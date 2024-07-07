package com.projects.blogapp.Users;

import com.projects.blogapp.users.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public class UserServiceTests {

    @Autowired
    UserService userService;
//
//    @Test
//    void can_create_user(){
//        var user = userService.createuser("Abdullah", "pass123", "abk@gmail.com");
//
//        Assertions.assertNotNull(user);
//        Assertions.assertEquals("Abdullah",user.getUsername());
//    }
}
