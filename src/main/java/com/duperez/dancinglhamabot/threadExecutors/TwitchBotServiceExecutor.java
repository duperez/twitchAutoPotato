package com.duperez.dancinglhamabot.threadExecutors;

import com.duperez.dancinglhamabot.entities.TwitchUser;
import com.duperez.dancinglhamabot.repositories.UserRepository;
import com.duperez.dancinglhamabot.services.UserService;
import com.duperez.dancinglhamabot.utils.TwitchUtils;
import com.github.philippheuer.credentialmanager.domain.OAuth2Credential;
import com.github.twitch4j.TwitchClient;
import com.github.twitch4j.TwitchClientBuilder;
import com.github.twitch4j.chat.events.channel.ChannelMessageActionEvent;
import com.github.twitch4j.chat.events.channel.ChannelMessageEvent;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@AllArgsConstructor
public class TwitchBotServiceExecutor {

    TwitchUser twitchUser;

    private UserRepository userRepository;


    @PostConstruct
    public void startBot() {
        System.out.println("Starting bot for user: " + twitchUser.getUser_name());

        Timer timer = new Timer();

        TwitchClient twitchClient = TwitchClientBuilder.builder()
                .withClientId(twitchUser.getClientId())
                .withClientSecret(twitchUser.getClientSecret())
                .withChatAccount(new OAuth2Credential("twitch", twitchUser.getOauth()))
                .withEnableChat(true)
                .build();

        twitchClient.getChat().joinChannel(twitchUser.getChannels());

        twitchClient.getEventManager().onEvent(ChannelMessageEvent.class, event -> {
            System.out.println(event.getMessage());
            if (event.getMessage().contains(twitchUser.getUser_name()) && event.getMessage().contains("you have")) {
                sendPotatoMessage(timer, event);
            }
            if (event.getMessage().contains(twitchUser.getUser_name()) && event.getMessage().contains("\uD83E\uDD54")) {
                int potatoes = TwitchUtils.extractTotalPotato(event.getMessage().replace(",", ""));
                twitchUser.setPotatoes(potatoes);
                userRepository.save(twitchUser);
            }

        });
    }

    private void sendPotatoMessage(Timer timer, ChannelMessageEvent event) {
        potatoTask(timer, 1000, event, "potato", false);
        potatoTask(timer, 3000, event, "steal", false);
        potatoTask(timer, 5000, event, "trample", false);
        boolean isCdr = potatoTask(timer, 1000, event, "cdr", false);
        if (isCdr) {
            potatoTask(timer, 3000, event, "potato", true);
            potatoTask(timer, 5000, event, "trample", true);
            potatoTask(timer, 7000, event, "steal", true);
        }
    }

    private boolean potatoTask(Timer timer, int time, ChannelMessageEvent event, String command, boolean skipValidattion) {

        if (event.getMessage().contains(command) || skipValidattion) {
            timer.schedule(
                    new TimerTask() {
                        @Override
                        public void run() {
                            event.getTwitchChat().sendMessage(event.getChannel().getName(), "#" + command);
                        }
                    }, time
            );
        }

        return event.getMessage().contains(command) || skipValidattion;
    }
}