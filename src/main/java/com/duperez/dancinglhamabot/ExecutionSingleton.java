package com.duperez.dancinglhamabot;

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



    public void execute(List<TwitchUser> twitchUsers) {
        if (threadExecutors == null)
            threadExecutors = new ArrayList<>();
        for(TwitchUser twitchUser : twitchUsers) {
            ThreadExecutors threadExecutorsInstance = new ThreadExecutors();
            threadExecutorsInstance.run(new TwitchBotServiceExecutor(twitchUser.getChannels(), twitchUser.getClientId(), twitchUser.getClientSecret(), twitchUser.getOauth(), twitchUser.getUser_name()));
            threadExecutors.add(threadExecutorsInstance);
        }
    }

    public void execute(TwitchUser twitchUser) {
        if (threadExecutors == null)
            threadExecutors = new ArrayList<>();
        ThreadExecutors threadExecutorsInstance = new ThreadExecutors();
        threadExecutorsInstance.run(new TwitchBotServiceExecutor(twitchUser.getChannels(), twitchUser.getClientId(), twitchUser.getClientSecret(), twitchUser.getOauth(), twitchUser.getUser_name()));
        threadExecutors.add(threadExecutorsInstance);
    }


}

class ThreadExecutors extends Thread {
    public void run(TwitchBotServiceExecutor twitchBotServiceExecutor) {
        twitchBotServiceExecutor.startBot();
    }
}