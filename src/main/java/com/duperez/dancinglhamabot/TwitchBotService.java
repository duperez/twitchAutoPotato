package com.duperez.dancinglhamabot;

import com.github.philippheuer.credentialmanager.domain.OAuth2Credential;
import com.github.twitch4j.TwitchClient;
import com.github.twitch4j.TwitchClientBuilder;
import com.github.twitch4j.chat.events.channel.ChannelMessageActionEvent;
import jakarta.annotation.PostConstruct;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class TwitchBotService {

    @Autowired
    private UserRepository twitchUserRepository;

    @PostConstruct
    public void startBot() {

        ExecutionSingleton executionSingleton = ExecutionSingleton.getInstance();

        executionSingleton.execute(twitchUserRepository.findAll());

    }

}