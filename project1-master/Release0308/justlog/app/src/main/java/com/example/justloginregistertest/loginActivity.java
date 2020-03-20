package com.example.justloginregistertest;
/**
 * 纯粹实现登录注册功能，其它功能都被注释掉了
 * 起作用的代码（连带着packag、import算上） 共 73 行
 * 不多吧？
 */

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.example.justloginregistertest.ClassSpace.*;
import com.example.justloginregistertest.threads.LocalUserService;
import com.example.justloginregistertest.threads.NetReceiveService;
import com.example.justloginregistertest.threads.NetReceiveThread;
import com.example.justloginregistertest.threads.NetService;
import com.example.justloginregistertest.threads.NetThread;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import androidx.appcompat.app.AppCompatActivity;
import android.content.BroadcastReceiver;
import android.content.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.util.ArrayList;
/**
 * Created by littlecurl 2018/6/24
 */

/**
 * 此类 implements View.OnClickListener 之后，
 * 就可以把onClick事件写到onCreate()方法之外
 * 这样，onCreate()方法中的代码就不会显得很冗余
 */
public class loginActivity extends AppCompatActivity implements View.OnClickListener {
    /**
     * 声明自己写的 DBOpenHelper 对象
     * DBOpenHelper(extends SQLiteOpenHelper) 主要用来
     * 创建数据表
     * 然后再进行数据表的增、删、改、查操作
     */
    private DBOpenHelper mDBOpenHelper;
    private TextView mTvLoginactivityRegister;
    private RelativeLayout mRlLoginactivityTop;
    private EditText mEtLoginactivityUsername;
    private EditText mEtLoginactivityPassword;
    private LinearLayout mLlLoginactivityTwo;
    private Button mBtLoginactivityLogin;
    public boolean match;


    private LocalBroadcastManager mLocalBroadcastManager;
    private MyBroadcastReceiver mBroadcastReceiver;
    public static String COM_LOGIN_REJECTED = "com.login.rejected";
    public static String COM_LOGIN_ACCEPTED = "com.login.accepted";

    /**
     * 创建 Activity 时先来重写 onCreate() 方法
     * 保存实例状态
     * super.onCreate(savedInstanceState);
     * 设置视图内容的配置文件
     * setContentView(R.layout.activity_login);
     * 上面这行代码真正实现了把视图层 View 也就是 layout 的内容放到 Activity 中进行显示
     * 初始化视图中的控件对象 initView()
     * 实例化 DBOpenHelper，待会进行登录验证的时候要用来进行数据查询
     * mDBOpenHelper = new DBOpenHelper(this);
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //Intent intent = new Intent(this, NetService.class);
        //startService(intent);
        //Thread x = new Thread(new NetThread());
        //x.start();
        //注册广播

        mBroadcastReceiver = new MyBroadcastReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(COM_LOGIN_ACCEPTED);
        intentFilter.addAction(COM_LOGIN_REJECTED);

        mLocalBroadcastManager = LocalBroadcastManager.getInstance(this);
        mLocalBroadcastManager.registerReceiver(mBroadcastReceiver, intentFilter);

        initView();

        mDBOpenHelper = new DBOpenHelper(this);


    }

    /**
     * onCreate()中大的布局已经摆放好了，接下来就该把layout里的东西
     * 声明、实例化对象然后有行为的赋予其行为
     * 这样就可以把视图层View也就是layout 与 控制层 Java 结合起来了
     */
    private void initView() {
        // 初始化控件
        mBtLoginactivityLogin = findViewById(R.id.bt_loginactivity_login);
        mTvLoginactivityRegister = findViewById(R.id.tv_loginactivity_register);
        mRlLoginactivityTop = findViewById(R.id.rl_loginactivity_top);
        mEtLoginactivityUsername = findViewById(R.id.et_loginactivity_username);
        mEtLoginactivityPassword = findViewById(R.id.et_loginactivity_password);
        mLlLoginactivityTwo = findViewById(R.id.ll_loginactivity_two);

        // 设置点击事件监听器
        mBtLoginactivityLogin.setOnClickListener(this);
        mTvLoginactivityRegister.setOnClickListener(this);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            // 跳转到注册界面
            case R.id.tv_loginactivity_register:
                startActivity(new Intent(this, RegisterActivity.class));
                finish();
                break;
            /**
             * 登录验证：
             *
             * 从EditText的对象上获取文本编辑框输入的数据，并把左右两边的空格去掉
             *  String name = mEtLoginactivityUsername.getText().toString().trim();
             *  String password = mEtLoginactivityPassword.getText().toString().trim();
             *  进行匹配验证,先判断一下用户名密码是否为空，
             *  if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(password))
             *  再进而for循环判断是否与数据库中的数据相匹配
             *  if (name.equals(user.getName()) && password.equals(user.getPassword()))
             *  一旦匹配，立即将match = true；break；
             *  否则 一直匹配到结束 match = false；
             *
             *  登录成功之后，进行页面跳转：
             *
             *  Intent intent = new Intent(this, MainActivity.class);
             *  startActivity(intent);
             *  finish();//销毁此Activity
             */
            case R.id.bt_loginactivity_login:
                String name = mEtLoginactivityUsername.getText().toString().trim();
                String password = mEtLoginactivityPassword.getText().toString().trim();
                Users usr = new Users(name, password);
                final compack A = new compack("login request", 1, 0, usr, true);
                final loginActivity x = this;


                //处理登录的过程，分为是否为空两种情况
                if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(password)) {
                    /*new Thread(new Runnable() {
                        @Override
                        public void run(){
                            try{

                                Socket socket = new Socket("192.168.0.104",9999);

                                //向服务器发送登录请求，等待处理
                                OutputStream os = socket.getOutputStream();
                                ObjectOutputStream oos = new ObjectOutputStream(os);
                                oos.writeObject(A);
                                socket.shutdownOutput();

                                //接收服务器的回复，判断是登录成功还是登录被拒绝
                                InputStream is = socket.getInputStream();
                                ObjectInputStream ois = new ObjectInputStream(is);
                                compack B = (compack)ois.readObject();
                                socket.shutdownInput();

                                System.out.println(B.type);

                                //接收服务器发来的信息（user信息和type信息）
                                if(B.type.equals("login_accepted")) {
                                    match = true;
                                }
                                else {
                                    match = false;
                                }
                                //关闭网络通信的相关流
                                oos.close();
                                os.close();
                                //ois.close();
                                //is.close();
                                socket.close();
                            } catch (UnsupportedEncodingException e){
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }

                    }  ).start();*/

                    Intent send_login = new Intent(this,NetService.class);
                    send_login.putExtra("send", A);
                    send_login.putExtra("need_send", true);
                    startService(send_login);


                } else {
                    Toast.makeText(this, "请输入你的用户名或密码", Toast.LENGTH_SHORT).show();
                }

                break;
        }
    }

    public class MyBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if(action.equals(COM_LOGIN_ACCEPTED)){
                Toast.makeText(context, "登录成功", Toast.LENGTH_SHORT).show();
                Intent intent0 = new Intent(context, MainActivity.class);
                startActivity(intent0);
                finish();//销毁此Activity
            }
            else if(action.equals(COM_LOGIN_REJECTED)){
                Toast.makeText(context, "用户名或密码不正确，请重新输入", Toast.LENGTH_SHORT).show();
            }

        }
    }



}



