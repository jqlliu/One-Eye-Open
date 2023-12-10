package com.group.ProjectB;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Game {
    MessageService msg;
    Game(MessageService msg){
        this.msg = msg;
        beginTimer();
    }
    void beginTimer(){
        ScheduledExecutorService exe = Executors.newSingleThreadScheduledExecutor();
        exe.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                msg.sendMessage(new Message("TEST"));
            }
        }, 0,100, TimeUnit.MILLISECONDS);
    }





}
