package com.duperez.dancinglhamabot;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/user")
    public TwitchUser createUser(@RequestBody TwitchUser twitchUser) {
        return userService.createUser(twitchUser);
    }

    @DeleteMapping("/user")
    public void deleteUser(@RequestParam Integer id) {
        userService.deleteUser(id);
    }


}
