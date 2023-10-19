package com.duperez.dancinglhamabot.controllers;

import com.duperez.dancinglhamabot.entities.TwitchChannel;
import com.duperez.dancinglhamabot.services.TwitchChannelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class twitchChannelController {

    @Autowired
    TwitchChannelService twitchChannelService;

    @PostMapping("/channels")
    public void addChannel(@RequestBody TwitchChannel twitchChannel) {
        twitchChannelService.addChannel(twitchChannel);
    }

    @DeleteMapping("/channels")
    public void deleteChannels(@RequestParam int id) {
        twitchChannelService.deleteChannels(id);
    }

}