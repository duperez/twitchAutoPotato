package com.duperez.dancinglhamabot.repositories;

import com.duperez.dancinglhamabot.entities.TwitchChannel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TwitchChannelRepository extends JpaRepository<TwitchChannel, Integer> {
}
