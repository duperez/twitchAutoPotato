package com.duperez.dancinglhamabot.repositories;

import com.duperez.dancinglhamabot.entities.TwitchUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<TwitchUser, Integer> {
}
