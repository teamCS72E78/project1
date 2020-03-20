package com.example.justloginregistertest.ClassSpace;


import java.io.Serializable;

public class Users extends Object implements Serializable {
    private static final long serialVersionUID = 1L;
    public int id;
    public String username;
    public String ip = null;
    public boolean is_login = false;
    public String password;

    public Users(int id,String name, String password) {
        this.id = id;
        this.username = name;
        this.password = password;
    }
    public Users(String name, String password) {

        this.username = name;
        this.password = password;
    }
    public void get_ip(String ip){
        this.ip = ip;
    }

}