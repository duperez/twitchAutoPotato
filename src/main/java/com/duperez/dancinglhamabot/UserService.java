package com.duperez.dancinglhamabot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public TwitchUser createUser(TwitchUser twitchUser) {
        ExecutionSingleton executionSingleton = ExecutionSingleton.getInstance();
        executionSingleton.execute(twitchUser);
        return userRepository.save(twitchUser);
    }






}
