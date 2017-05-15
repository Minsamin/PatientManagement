package com.example.samin.paitientmanagement.other;


/**
 * Created by Samin on 23-02-2017.
 */

public class Show_chat_data_item {
    private String message;
    private String sender;



    public Show_chat_data_item() {
    }


    public Show_chat_data_item(String message, String sender) {
        this.message = message;
        this.sender = sender;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }
}
