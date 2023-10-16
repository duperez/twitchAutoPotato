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

    //@Value("${CHANNELS}")
    //private String channels;
//
    //@Value("${ID}")
    //private String clientId;
//
    //@Value("${SECRET}")
    //private String clientSecret;
//
    //@Value("${OAUTH}")
    //private String oauth;
//
    //@Value("${USER_NAME}")
    //private String user;

    static int totalPotato = 0;

    @Autowired
    private UserRepository twitchUserRepository;




    @PostConstruct
    public void startBot() {

        //Timer timer = new Timer();
//
        //TwitchClient twitchClient = TwitchClientBuilder.builder()
        //        .withClientId(clientId)
        //        .withClientSecret(clientSecret)
        //        .withChatAccount(new OAuth2Credential("twitch", oauth))
        //        .withEnableChat(true)
        //        .build();
//
        //twitchClient.getChat().joinChannel(channels);
//
        //twitchClient.getEventManager().onEvent(com.github.twitch4j.chat.events.channel.ChannelMessageActionEvent.class, event -> {
        //    System.out.println(event.getMessage());
        //    if (
        //                    event.getMessage().contains(user) &&
        //                    event.getMessage().contains("you have")
//
        //    ) {
        //        sendPotatoMessage(timer, twitchClient, event);
        //        if(isResult(event.getMessage())) {
        //            extractTotalPotato(event.getMessage());
        //            extractTotalNeededPotato(event.getMessage());
        //            printNumberOfPotatoes();
        //        }
//
        //    }
        //});

        ExecutionSingleton executionSingleton = ExecutionSingleton.getInstance();

        executionSingleton.execute(twitchUserRepository.findAll());

    }

    private void sendPotatoMessage(Timer timer, TwitchClient twitchClient, ChannelMessageActionEvent event) {
        if (event.getMessage().contains("potato")) {
            timer.schedule(task(twitchClient, event, "potato"), 5000);
        }
        if (event.getMessage().contains("steal")) {
            timer.schedule(task(twitchClient, event, "steal"), 10000);
        }
        if (event.getMessage().contains("trample")) {
            timer.schedule(task(twitchClient, event, "trample"), 15000);
        }
        if (event.getMessage().contains("cdr")) {
            timer.schedule(task(twitchClient, event, "potato"), 5000);
            timer.schedule(task(twitchClient, event, "trample"), 10000);
            timer.schedule(task(twitchClient, event, "steal"), 15000);
            timer.schedule(task(twitchClient, event, "trample"), 20000);
        }
    }

    public boolean isResult(String texto) {
        String padrao = "\\[(?:\\+|-)?\\d+ ⇒ (?:-\\d+|\\d+)\\]";

        Pattern pattern = Pattern.compile(padrao);

        Matcher matcher = pattern.matcher(texto);


        if(matcher.find()) {
            return matcher.matches();
        }
        return false;
    }

    public static Integer extractTotalPotato(String text) {
        String padrao = "\\[(?:\\+|-)?\\d+ ⇒ ([-\\d,]+)\\]";

        Pattern pattern = Pattern.compile(padrao);

        Matcher matcher = pattern.matcher(text);

        if(matcher.find()) {
            totalPotato = Integer.parseInt(matcher.group(1));
            return Integer.parseInt(matcher.group(1));
        }
        return 0;
    }

    public static Integer extractTotalNeededPotato(String text) {
        String padrao = "\\[(?:\\+|-)?\\d+ ⇒ ([-\\d,]+)\\]";

        Pattern pattern = Pattern.compile(padrao);

        Matcher matcher = pattern.matcher(text);

        if(matcher.find()) {
            totalPotato = Integer.parseInt(matcher.group(1));
            return Integer.parseInt(matcher.group(1));
        }
        return 0;
    }

    public static void printNumberOfPotatoes() {
        System.out.println(totalPotato);
    }


    @NotNull
    private TimerTask task(TwitchClient twitchClient, ChannelMessageActionEvent event, String command) {
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                twitchClient.getChat().sendMessage(event.getChannel().getName(), "#" + command);
                System.out.println(command + " executed!");
            }
        };
        return task;
    }

}