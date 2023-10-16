package com.duperez.dancinglhamabot;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<TwitchUser, Integer> {
}
