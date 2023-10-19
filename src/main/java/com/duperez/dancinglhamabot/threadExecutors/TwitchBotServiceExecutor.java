package com.duperez.dancinglhamabot.threadExecutors;

import com.github.philippheuer.credentialmanager.domain.OAuth2Credential;
import com.github.twitch4j.TwitchClient;
import com.github.twitch4j.TwitchClientBuilder;
import com.github.twitch4j.chat.events.channel.ChannelMessageActionEvent;
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

    private String channels;

    private String clientId;

    private String clientSecret;

    private String oauth;

    private String user;


    @PostConstruct
    public void startBot() {
        System.out.println("Starting bot for user: " + user);

        Timer timer = new Timer();

        TwitchClient twitchClient = TwitchClientBuilder.builder()
                .withClientId(clientId)
                .withClientSecret(clientSecret)
                .withChatAccount(new OAuth2Credential("twitch", oauth))
                .withEnableChat(true)
                .build();

        twitchClient.getChat().joinChannel(channels);

        twitchClient.getEventManager().onEvent(ChannelMessageActionEvent.class, event -> {
            System.out.println(event.getMessage());
            if (event.getMessage().contains(user) && event.getMessage().contains("you have")) {
                //sendPotatoMessage(timer, event);
            }
        });
    }

    private void sendPotatoMessage(Timer timer, ChannelMessageActionEvent event) {
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

    public boolean isResult(String texto) {
        String padrao = "\\[(?:\\+|-)?\\d+ ⇒ (?:-\\d+|\\d+)\\]";

        Pattern pattern = Pattern.compile(padrao);

        Matcher matcher = pattern.matcher(texto);

        return matcher.find() && matcher.matches();
    }

    //public static Integer extractTotalPotato(String text) {
    //    String padrao = "\\[(?:\\+|-)?\\d+ ⇒ ([-\\d,]+)\\]";
//
    //    Pattern pattern = Pattern.compile(padrao);
//
    //    Matcher matcher = pattern.matcher(text);
//
    //    if(matcher.find()) {
    //        totalPotato = Integer.parseInt(matcher.group(1));
    //        return Integer.parseInt(matcher.group(1));
    //    }
    //    return 0;
    //}
//
    //public static Integer extractTotalNeededPotato(String text) {
    //    String padrao = "\\[(?:\\+|-)?\\d+ ⇒ ([-\\d,]+)\\]";
//
    //    Pattern pattern = Pattern.compile(padrao);
//
    //    Matcher matcher = pattern.matcher(text);
//
    //    if(matcher.find()) {
    //        totalPotato = Integer.parseInt(matcher.group(1));
    //        return Integer.parseInt(matcher.group(1));
    //    }
    //    return 0;
    //}
//
    //public static void printNumberOfPotatoes() {
    //    System.out.println(totalPotato);
    //}

    private boolean potatoTask(Timer timer, int time, ChannelMessageActionEvent event, String command, boolean skipValidattion) {

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