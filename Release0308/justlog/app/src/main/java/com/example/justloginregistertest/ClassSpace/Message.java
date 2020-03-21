package com.example.justloginregistertest.ClassSpace;
import java.io.Serializable;

public class Message extends Object implements Serializable{

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    public String username;
    public String label; //
    public String title;                 //
    public String detailed;              //
    public int message_id;               //
    //public int message_order;

    public Message(String username, String label, String title, String detailed, int message_id){
        this.username = username;
        this.label = label;
        this.title = title;
        this.detailed = detailed;
        this.message_id = message_id;
    }
    public Message(){
        this.message_id = -1;
    }
}