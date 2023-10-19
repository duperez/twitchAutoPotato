package com.duperez.dancinglhamabot.repositories;

import com.duperez.dancinglhamabot.entities.TwitchUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<TwitchUser, Integer> {

    @Query("SELECT t FROM TwitchUser t WHERE t.user_name = ?1")
    Optional<TwitchUser> findByName(String name);
}
