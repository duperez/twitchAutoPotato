package com.duperez.dancinglhamabot.Twitch;

import com.github.philippheuer.credentialmanager.domain.OAuth2Credential;
import com.github.twitch4j.TwitchClient;
import com.github.twitch4j.TwitchClientBuilder;

public class TwitchClientService {

    public static TwitchClient createClient(String clientId, String clientSecret, String oauth) {
        return TwitchClientBuilder.builder()
                .withClientId(clientId)
                .withClientSecret(clientSecret)
                .withChatAccount(new OAuth2Credential("twitch", oauth))
                .withEnableChat(true)
                .build();
    }

}
