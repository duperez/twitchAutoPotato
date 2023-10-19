package com.duperez.dancinglhamabot.services;

import com.duperez.dancinglhamabot.Twitch.TwitchBotClient;
import com.duperez.dancinglhamabot.entities.TwitchChannel;
import com.duperez.dancinglhamabot.repositories.TwitchChannelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TwitchChannelService {

    @Autowired
    TwitchChannelRepository twitchChannelRepository;

    @Autowired
    TwitchBotClient twitchBotClient;

    public void addChannel(TwitchChannel twitchChannel) {
        twitchChannelRepository.save(twitchChannel);
        twitchBotClient.addChannel(twitchChannel.getChannelName());
    }

    public void deleteChannels(int id) {
        twitchChannelRepository.deleteById(id);
    }
}
