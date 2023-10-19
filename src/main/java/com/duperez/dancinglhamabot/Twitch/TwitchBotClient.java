package com.duperez.dancinglhamabot.Twitch;

import com.duperez.dancinglhamabot.repositories.TwitchChannelRepository;
import com.duperez.dancinglhamabot.services.UserService;
import com.duperez.dancinglhamabot.threadExecutors.GambleThread;
import com.github.twitch4j.TwitchClient;
import com.github.twitch4j.chat.events.channel.ChannelMessageEvent;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class TwitchBotClient {

    @Autowired
    TwitchChannelRepository twitchChannelRepository;

    @Autowired
    UserService userService;

    TwitchClient twitchClient;

    @Value("${ID}")
    private String clientId;

    @Value("${SECRET}")
    private String clientSecret;

    @Value("${OAUTH}")
    private String oauth;


    @PostConstruct
    public void startBot() {
        System.out.println("Starting bot");
        twitchClient = TwitchClientService.createClient(clientId, clientSecret, oauth);
        twitchChannelRepository.findAll().forEach(twitchChannel -> {
            twitchClient.getChat().joinChannel(twitchChannel.getChannelName());
            twitchClient.getEventManager().onEvent(ChannelMessageEvent.class, this::userCommand);
        });
    }

    public void addChannel(String channel) {
        twitchClient.getChat().joinChannel(channel);
        twitchClient.close();
        startBot();
    }

    public void userCommand(ChannelMessageEvent event) {
        if (event.getMessage().startsWith("-dupebot")) {
            if (event.getMessage().contains("potato info")) {
                event.getTwitchChat().sendMessage(event.getChannel().getName(), "dupe bot status: ON | number of channels: " + twitchChannelRepository.findAll().size() + " | number of users: " + userService.getAllUsers().size());
            }
            if (event.getMessage().contains("potato gamble")) {
                int min = Integer.parseInt(event.getMessage().split(" ")[3]);
                int max = Integer.parseInt(event.getMessage().split(" ")[4]);

                new GambleThread().run(min, max, userService.findUserByName("@" + event.getUser().getName()));
            }
        }
    }


}
