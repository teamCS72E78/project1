package com.example.justloginregistertest.threads;

import android.content.Intent;

import java.net.DatagramSocket;
import java.net.DatagramPacket;
import com.example.justloginregistertest.ClassSpace.*;
import com.example.justloginregistertest.*;
import androidx.localbroadcastmanager.*;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

public class NetReceiveThread implements Runnable {
    public compack data_pack = null;
    private LocalBroadcastManager localBroadcastManager;


    NetReceiveThread(compack data_pack){
        super();
        this.data_pack = data_pack;
    }
    @Override
    public void run() {
        if (data_pack.type.equals("login_rejected")){
            Intent intent = new Intent(loginActivity.COM_LOGIN_REJECTED);
            localBroadcastManager.sendBroadcast(intent);
        }
        else if(data_pack.type.equals("login_accepted")){
            Intent intent = new Intent(loginActivity.COM_LOGIN_ACCEPTED);
            intent.putExtra("data_pack", data_pack);
            localBroadcastManager.sendBroadcast(intent);
        }


    }
}
