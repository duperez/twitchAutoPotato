package com.duperez.dancinglhamabot.threadExecutors;

import com.duperez.dancinglhamabot.Twitch.TwitchClientService;
import com.duperez.dancinglhamabot.entities.TwitchUser;
import com.github.twitch4j.TwitchClient;
import com.github.twitch4j.chat.events.channel.ChannelMessageActionEvent;

import static com.duperez.dancinglhamabot.utils.TwitchUtils.extractTotalPotato;

public class GambleThread extends Thread {

    int min, max;

    TwitchUser twitchUser;

    TwitchClient twitchClient;

    int multiplier = 100;

    public void run(int min, int max, TwitchUser twitchUser) {
        this.min = min;
        this.max = max;
        this.twitchUser = twitchUser;
        runGamble();
    }

    public void runGamble() {
        twitchClient = TwitchClientService.createClient(twitchUser.getClientId(), twitchUser.getClientSecret(), twitchUser.getOauth());
        twitchClient.getChat().joinChannel(twitchUser.getChannels());
        twitchClient.getChat().sendMessage(twitchUser.getChannels(), "Gamble started!");
        twitchClient.getChat().sendMessage(twitchUser.getChannels(), "#gamba " + multiplier + " even");
        twitchClient.getEventManager().onEvent(ChannelMessageActionEvent.class, event -> {
            if (event.getMessage().toLowerCase().contains(twitchUser.getUser_name().toLowerCase())) {

                if (event.getMessage().contains("✅") || event.getMessage().contains("❌")) {
                    int potatoes = extractTotalPotato(event.getMessage());
                    System.out.println(potatoes);
                    if (potatoes <= min || potatoes >= max || multiplier > 1000) {
                        twitchClient.getChat().sendMessage(twitchUser.getChannels(), "Gamble finished!");
                        try {
                            Thread.sleep(5000);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        twitchClient.close();
                    }
                }


                if (event.getMessage().contains("✅")) {
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    multiplier = 100;
                    twitchClient.getChat().sendMessage(twitchUser.getChannels(), "#gamba " + multiplier + " even");
                }
                if (event.getMessage().contains("❌")) {

                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }

                    multiplier *= 2;
                    twitchClient.getChat().sendMessage(twitchUser.getChannels(), "#gamba " + multiplier + " even");
                }
            }
        });
    }


}
