package com.duperez.dancinglhamabot.threadExecutors;

import com.duperez.dancinglhamabot.entities.TwitchUser;
import com.duperez.dancinglhamabot.repositories.UserRepository;
import com.duperez.dancinglhamabot.services.UserService;

import java.util.ArrayList;
import java.util.List;

public class ExecutionSingleton {

    private static ExecutionSingleton executionSingleton;
    private static List<ThreadExecutors> threadExecutors;

    private ExecutionSingleton() {

    }

    public static ExecutionSingleton getInstance() {
        if (executionSingleton == null) {
            executionSingleton = new ExecutionSingleton();
        }

        return executionSingleton;
    }

    public void execute(List<TwitchUser> twitchUsers, UserRepository twitchUserRepository) {
        for(TwitchUser twitchUser : twitchUsers) {
            execute(twitchUser, twitchUserRepository);
        }
    }

    public void execute(TwitchUser twitchUser, UserRepository twitchUserRepository) {
        if (threadExecutors == null)
            threadExecutors = new ArrayList<>();
        ThreadExecutors threadExecutorsInstance = new ThreadExecutors();
        threadExecutorsInstance.run(new TwitchBotServiceExecutor(twitchUser, twitchUserRepository));
        threadExecutors.add(threadExecutorsInstance);
    }
}

class ThreadExecutors extends Thread {
    public void run(TwitchBotServiceExecutor twitchBotServiceExecutor) {
        twitchBotServiceExecutor.startBot();
    }
}