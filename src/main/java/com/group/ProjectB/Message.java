package com.group.ProjectB;

public class Message {

    String content;
    Player from;
    Player to;
    boolean whisper, announce;

    Message(Player from, String content){
        this.from = from;
        this.content = content;
    }




}
