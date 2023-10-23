package com.duperez.dancinglhamabot.Twitch;

import com.duperez.dancinglhamabot.entities.TwitchUser;
import com.duperez.dancinglhamabot.repositories.TwitchChannelRepository;
import com.duperez.dancinglhamabot.services.UserService;
import com.duperez.dancinglhamabot.threadExecutors.GambleThread;
import com.github.twitch4j.TwitchClient;
import com.github.twitch4j.chat.events.channel.ChannelMessageEvent;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

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

        String message = event.getMessage();
        //it is a bot command
        if (message.startsWith("-")) {
            if (message.contains("info")) {
                event.getTwitchChat().sendMessage(event.getChannel().getName(), "dupe bot status: ON | number of channels: " + twitchChannelRepository.findAll().size() + " | number of users: " + userService.getAllUsers().size());
            }
            if(message.contains("removeUser")) {
                if (event.getUser().getName().equalsIgnoreCase("duperez_dev")) {
                    String user = event.getMessage().split(" ")[2];
                    userService.deleteUser(userService.findUserByName("@" + user).getId());
                    event.getTwitchChat().sendMessage(event.getChannel().getName(), "User " + user + " removed");
                } else {
                    event.getTwitchChat().sendMessage(event.getChannel().getName(), "You don't have permission to do that PoroSad");
                }
            }
            if(message.contains("showUsers")) {
                if (event.getUser().getName().equalsIgnoreCase("duperez_dev")) {
                    //get all usuers and transform in a list with all names
                    String users = userService.getAllUsers()
                            .stream()
                            .map(TwitchUser::getUser_name)
                            .map(user -> user.replace("@", ""))
                            .collect(Collectors.joining(" | "));
                    userService.getAllUsers().stream().map(twitchUser -> twitchUser.getUser_name() + " | ").forEach(users::concat);
                    event.getTwitchChat().sendMessage(event.getChannel().getName(), "Current users: " + users);

                } else {
                    event.getTwitchChat().sendMessage(event.getChannel().getName(), "You don't have permission to do that PoroSad");
                }
            }
            // it is related to the potato automation
            if (message.startsWith("-gamble")) {
                int min = Integer.parseInt(event.getMessage().split(" ")[1]);
                int max = Integer.parseInt(event.getMessage().split(" ")[2]);

                new GambleThread().run(min, max, userService.findUserByName("@" + event.getUser().getName()));
            }
            if (message.equals("-potatoes")) {
                event.getTwitchChat().sendMessage(event.getChannel().getName(), "You have " + userService.findUserByName("@" + event.getUser().getName()).getPotatoes() + " potatoes");
            }
        }
    }


}
