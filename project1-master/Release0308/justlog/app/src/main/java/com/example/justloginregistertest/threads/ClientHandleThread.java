package com.example.justloginregistertest.threads;
import android.content.Intent;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.nio.file.AccessDeniedException;
import com.example.justloginregistertest.*;
import android.widget.Toast;
import androidx.activity.*;

import com.example.justloginregistertest.ClassSpace.*;


public class ClientHandleThread implements Runnable {
    public Socket socket = null;
    public NetService x = null;
    public ClientHandleThread(Socket socket,NetService netService){
        super();
        this.socket = socket;
        this.x = netService;
    }
    public void run() {
        // TODO Auto-generated method stub
        try {
            InputStream is = socket.getInputStream();
            ObjectInputStream ois=new ObjectInputStream(is);

            //最大允许传送100个包
            compack A[] = new compack[100];
            int compack_num = 0;
            while (true){
                A[compack_num] = (compack)ois.readObject();
                if(A[compack_num].is_last_pack){
                    compack_num++;
                    break;
                }
                compack_num++;
            }

            if(A[0].type.equals("login_accepted") || A[0].type.equals("login_rejected")){
                Intent login_in = new Intent(x,MainActivity.class);
                x.startActivity(login_in);
            }

            //= (compack)ois.readObject();
            InetAddress inetAddress = socket.getInetAddress();
            socket.shutdownInput();






        } catch (IOException | ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        finally{
            try{
                if(socket!=null){
                    socket.close();
                }
            }catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }
    }

}
