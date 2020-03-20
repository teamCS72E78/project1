package com.example.justloginregistertest.threads;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.example.justloginregistertest.ClassSpace.*;
import com.example.justloginregistertest.User;

public class LocalUserService extends Service {
    public LocalUserService() {
    }
    public static Users home_user;
    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        compack x = (compack)intent.getSerializableExtra("this_user");
        if(x.type.equals("login_accepted")){
            home_user = (Users)x.data;
            System.out.println("Username is : " + home_user.username);
        }
        else if(x.type.equals("logout_accepted")){
            home_user = null;
            stopSelf();
        }

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
