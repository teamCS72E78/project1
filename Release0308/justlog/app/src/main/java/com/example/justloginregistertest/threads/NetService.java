package com.example.justloginregistertest.threads;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.example.justloginregistertest.ClassSpace.compack;
import com.example.justloginregistertest.MainActivity;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import com.example.justloginregistertest.ClassSpace.*;
import com.example.justloginregistertest.RegisterActivity;
import com.example.justloginregistertest.loginActivity;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class NetService extends IntentService {
    public static int port = 9999;
    public static InetAddress ip;
    public boolean need_send;
    public static String WAN = "175.24.57.212";
    public static String LAN = "192.168.0.104";

    private LocalBroadcastManager localBroadcastManager;

    public Socket socket = null;

    @Override
    public void onCreate() {
        super.onCreate();
        /*
        try{
            ip = InetAddress.getByName(LAN);
        }catch (IOException e){
            e.printStackTrace();
        }*/

    }

    public NetService() {
        super("NetService");


    }

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */


    @Override

    protected void onHandleIntent(Intent intent) {
        System.out.println("SendService activated!");
        compack send = (compack)intent.getSerializableExtra("send");
        need_send = intent.getBooleanExtra("need_send", false);
        System.out.println("类型为" + send.type);

        try{
            socket = new Socket(LAN, port);
            if(need_send) {
                OutputStream os = socket.getOutputStream();
                ObjectOutputStream oos = new ObjectOutputStream(os);
                oos.writeObject(send);
            }
            InputStream is = socket.getInputStream();
            ObjectInputStream ois = new ObjectInputStream(is);

            compack receive = (compack)ois.readObject();

            if (receive.type.equals("login_rejected")){
                Intent intent0 = new Intent(loginActivity.COM_LOGIN_REJECTED);
                //localBroadcastManager.sendBroadcast(intent0);
                LocalBroadcastManager.getInstance(this).sendBroadcast(intent0);
            }
            else if(receive.type.equals("login_accepted")){
                Intent intent0 = new Intent(loginActivity.COM_LOGIN_ACCEPTED);
                //localBroadcastManager.sendBroadcast(intent0);
                LocalBroadcastManager.getInstance(this).sendBroadcast(intent0);
            }else if(receive.type.equals("register accepted")){
                Intent intent0 = new Intent(RegisterActivity.COM_REGISTER_ACCEPTED);
                //localBroadcastManager.sendBroadcast(intent0);
                LocalBroadcastManager.getInstance(this).sendBroadcast(intent0);
            }else if(receive.type.equals("register rejected")){
                Intent intent0 = new Intent(RegisterActivity.COM_REGISTER_REJECTED);
                //localBroadcastManager.sendBroadcast(intent0);
                LocalBroadcastManager.getInstance(this).sendBroadcast(intent0);
            }


        }catch (IOException | ClassNotFoundException e){
            e.printStackTrace();
        }finally{
            try{
                if(socket!=null){
                    socket.close();
                }
            }catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }

        /*try {
            DatagramSocket ds = new DatagramSocket();
            DatagramPacket dp = new DatagramPacket(convert.o2b(send), convert.o2b(send).length, ip,port);
            ds.send(dp);
        }catch (IOException e){
            e.printStackTrace();
        }*/
    }
}
