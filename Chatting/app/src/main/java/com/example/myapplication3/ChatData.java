package com.example.myapplication3;

public class ChatData{
    public String firebaseKey;
    public String senderName;
    public String receiverName;
    public String message;
    public Long time;
    public String token;


    public String getSenderName(){
        return this.senderName;
    }
    public String getReceiverName(){
        return this.receiverName;
    }
    public String getMessage(){
        return this.message;
    }
    public Long getTime(){
        return this.time;
    }
}
