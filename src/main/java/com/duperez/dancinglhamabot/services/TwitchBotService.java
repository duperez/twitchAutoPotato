package com.duperez.dancinglhamabot.services;

import com.duperez.dancinglhamabot.threadExecutors.ExecutionSingleton;
import com.duperez.dancinglhamabot.repositories.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TwitchBotService {

    @Autowired
    private UserRepository twitchUserRepository;

    @PostConstruct
    public void startBot() {

        ExecutionSingleton executionSingleton = ExecutionSingleton.getInstance();

        executionSingleton.execute(twitchUserRepository.findAll(), twitchUserRepository);

    }

}