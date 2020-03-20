package com.example.justloginregistertest.threads;

import android.content.Intent;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import java.io.IOException;
import java.net.Socket;
import com.example.justloginregistertest.ClassSpace.*;
import com.example.justloginregistertest.*;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;

public class NetThread implements Runnable {
    private static String ip = "192.168.0.104";
    private static int port = 9999;

    private LocalBroadcastManager localBroadcastManager;

    public static compack senddata_pack[]= new compack[100];
    private static int send_num = 0;


    public static compack receivedata_pack = null;

    public Socket socket = null;

    public static void send(compack x){
        send_num++;
        senddata_pack[send_num] = x;
    }

    public NetThread(){

    }


    @Override
    public void run() {

        while(true){
            System.out.println("Ready to send number" + send_num);
            try{
                socket = new Socket(ip, port);
            }catch (IOException e){
                e.printStackTrace();
            }
            if(send_num > 0){
                //发送数据
                try{
                    OutputStream os = socket.getOutputStream();
                    ObjectOutputStream oos = new ObjectOutputStream(os);
                    oos.writeObject(senddata_pack[send_num]);
                    oos.close();
                    os.close();
                    socket.shutdownOutput();

                }catch (IOException e){
                    e.printStackTrace();
                }
                send_num--;
            }

            //接收数据
            try{
                InputStream is = socket.getInputStream();
                ObjectInputStream ois = new ObjectInputStream(is);
                receivedata_pack = (compack)ois.readObject();

                ois.close();
                is.close();
                socket.shutdownInput();

            }catch (IOException | ClassNotFoundException e){
                e.printStackTrace();
                continue;
            }


            //处理数据
            if(receivedata_pack.type.equals("logout_accepted")){
                break;
            }else if(receivedata_pack.type.equals("login_rejected")){
                Intent intent = new Intent(loginActivity.COM_LOGIN_REJECTED);
                localBroadcastManager.sendBroadcast(intent);
            }

            try{
                socket.close();
            }catch (IOException e){
                e.printStackTrace();
            }

        }
    }
}
