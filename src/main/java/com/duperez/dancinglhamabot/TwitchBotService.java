package com.duperez.dancinglhamabot;

import com.github.philippheuer.credentialmanager.domain.OAuth2Credential;
import com.github.twitch4j.TwitchClient;
import com.github.twitch4j.TwitchClientBuilder;
import jakarta.annotation.PostConstruct;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Timer;
import java.util.TimerTask;

@Component
public class TwitchBotService {

    @Value("${CHANNELS}")
    private String channels;

    @Value("${ID}")
    private String clientId;

    @Value("${SECRET}")
    private String clientSecret;

    @Value("${OAUTH}")
    private String oauth;

    @Value("${USER_NAME}")
    private String user;




    @PostConstruct
    public void startBot() {

        Timer timer = new Timer();

        TwitchClient twitchClient = TwitchClientBuilder.builder()
                .withClientId(clientId)
                .withClientSecret(clientSecret)
                .withChatAccount(new OAuth2Credential("twitch", oauth))
                .withEnableChat(true)
                .build();

        twitchClient.getChat().joinChannel(channels);

        // Ouça as mensagens do chat
        twitchClient.getEventManager().onEvent(com.github.twitch4j.chat.events.channel.ChannelMessageActionEvent.class, event -> {
            System.out.println(event.getMessage());
            // Verifique se a mensagem vem do bot "NomeDoBot"
            //
            if (
                    //event.getUser().getName().equalsIgnoreCase("PotatBotat") &&
                            event.getMessage().contains(user) &&
                            event.getMessage().contains("you have")

            ) {
                if (event.getMessage().contains("potato")) {
                    timer.schedule(getTimerTask(), 10000);
                    twitchClient.getChat().sendMessage(event.getChannel().getName(), "#potato");
                }
                if (event.getMessage().contains("steal")) {
                    timer.schedule(getTimerTask(), 10000);
                    twitchClient.getChat().sendMessage(event.getChannel().getName(), "#steal");
                }
                if (event.getMessage().contains("trample")) {
                    timer.schedule(getTimerTask(), 10000);
                    twitchClient.getChat().sendMessage(event.getChannel().getName(), "#trample");
                }
            }
        });
    }

    @NotNull
    private TimerTask getTimerTask() {
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                System.out.println("Timer finished!");
            }
        };
        return task;
    }
}