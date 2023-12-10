package com.group.ProjectB;

public class Message {
    Player server = new Player();

    String content;
    Player from;
    Player to;
    boolean whisper, announce;

    Message(Player from, String content){
        this.from = from;
        this.content = content;
        server.name = "SERVER";
    }
    Message(String content){
        this.from = server;
        this.content = content;
    }



}
