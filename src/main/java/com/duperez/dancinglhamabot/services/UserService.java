package com.duperez.dancinglhamabot.services;

import com.duperez.dancinglhamabot.entities.TwitchUser;
import com.duperez.dancinglhamabot.repositories.UserRepository;
import com.duperez.dancinglhamabot.threadExecutors.ExecutionSingleton;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public TwitchUser createUser(TwitchUser twitchUser) {
        ExecutionSingleton executionSingleton = ExecutionSingleton.getInstance();
        executionSingleton.execute(twitchUser, userRepository);
        return userRepository.save(twitchUser);
    }

    public void deleteUser(Integer id) {
        userRepository.deleteById(id);
    }

    public List<TwitchUser> getAllUsers() {
        return userRepository.findAll();
    }

    public TwitchUser findUserByName(String name) {
        return userRepository.findByName(name).get();
    }






}
