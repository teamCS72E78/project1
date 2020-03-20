package com.example.justloginregistertest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;
import com.example.justloginregistertest.ClassSpace.*;
import com.example.justloginregistertest.threads.NetService;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
/**
 * Created by littlecurl 2018/6/24
 */
/**
 * 此类 implements View.OnClickListener 之后，
 * 就可以把onClick事件写到onCreate()方法之外
 * 这样，onCreate()方法中的代码就不会显得很冗余
 */
public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private String realCode;
    private DBOpenHelper mDBOpenHelper;
    private Button mBtRegisteractivityRegister;
    private RelativeLayout mRlRegisteractivityTop;
    private ImageView mIvRegisteractivityBack;
    private LinearLayout mLlRegisteractivityBody;
    private EditText mEtRegisteractivityUsername;
    private EditText mEtRegisteractivityPassword1;
    private EditText mEtRegisteractivityPassword2;
    private EditText mEtRegisteractivityPhonecodes;
    private ImageView mIvRegisteractivityShowcode;
    private RelativeLayout mRlRegisteractivityBottom;

    private LocalBroadcastManager mLocalBroadcastManager;
    private RegisterActivity.MyBroadcastReceiver mBroadcastReceiver;
    public static String COM_REGISTER_REJECTED = "com.register.rejected";
    public static String COM_REGISTER_ACCEPTED = "com.register.accepted";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mBroadcastReceiver = new RegisterActivity.MyBroadcastReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(COM_REGISTER_ACCEPTED);
        intentFilter.addAction(COM_REGISTER_REJECTED);

        mLocalBroadcastManager = LocalBroadcastManager.getInstance(this);
        mLocalBroadcastManager.registerReceiver(mBroadcastReceiver, intentFilter);

        initView();

        mDBOpenHelper = new DBOpenHelper(this);

        //将验证码用图片的形式显示出来
        mIvRegisteractivityShowcode.setImageBitmap(Code.getInstance().createBitmap());
        realCode = Code.getInstance().getCode().toLowerCase();
    }

    private void initView(){
        mBtRegisteractivityRegister = findViewById(R.id.bt_registeractivity_register);
        mRlRegisteractivityTop = findViewById(R.id.rl_registeractivity_top);
        mIvRegisteractivityBack = findViewById(R.id.iv_registeractivity_back);
        mLlRegisteractivityBody = findViewById(R.id.ll_registeractivity_body);
        mEtRegisteractivityUsername = findViewById(R.id.et_registeractivity_username);
        mEtRegisteractivityPassword1 = findViewById(R.id.et_registeractivity_password1);
        mEtRegisteractivityPassword2 = findViewById(R.id.et_registeractivity_password2);
        mEtRegisteractivityPhonecodes = findViewById(R.id.et_registeractivity_phoneCodes);
        mIvRegisteractivityShowcode = findViewById(R.id.iv_registeractivity_showCode);
        mRlRegisteractivityBottom = findViewById(R.id.rl_registeractivity_bottom);

        /**
         * 注册页面能点击的就三个地方
         * top处返回箭头、刷新验证码图片、注册按钮
         */
        mIvRegisteractivityBack.setOnClickListener(this);
        mIvRegisteractivityShowcode.setOnClickListener(this);
        mBtRegisteractivityRegister.setOnClickListener(this);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_registeractivity_back: //返回登录页面
                Intent intent1 = new Intent(this, loginActivity.class);
                startActivity(intent1);
                finish();
                break;
            case R.id.iv_registeractivity_showCode:    //改变随机验证码的生成
                mIvRegisteractivityShowcode.setImageBitmap(Code.getInstance().createBitmap());
                realCode = Code.getInstance().getCode().toLowerCase();
                break;
            case R.id.bt_registeractivity_register:    //注册按钮
                //获取用户输入的用户名、密码、验证码
                String username = mEtRegisteractivityUsername.getText().toString().trim();
                String password = mEtRegisteractivityPassword2.getText().toString().trim();
                String phoneCode = mEtRegisteractivityPhonecodes.getText().toString().toLowerCase();
                //注册验证

                if (!TextUtils.isEmpty(username) && !TextUtils.isEmpty(password) && !TextUtils.isEmpty(phoneCode) ) {
                    if (phoneCode.equals(realCode)) {
                        //将用户名和密码加入到数据库中
                        final Users usr = new Users(username, password);
                        final compack A = new compack("register", 1, 0, usr, true);
                        Intent send_register = new Intent(this, NetService.class);
                        send_register.putExtra("send", A);
                        send_register.putExtra("need_send", true);
                        startService(send_register);

                        /*
                        new Thread(new Runnable() {
                            @Override
                            public void run(){
                                try{
                                    Socket socket = new Socket("192.168.0.104",9999);

                                    OutputStream os = socket.getOutputStream();
                                    ObjectOutputStream oos = new ObjectOutputStream(os);
                                    oos.writeObject(A);
                                    socket.shutdownOutput();

                                    InputStream is = socket.getInputStream();
                                    //BufferedReader br=new BufferedReader(new InputStreamReader(is,"GBK"));
                                    ObjectInputStream ois = new ObjectInputStream(is);
                                    final compack B = (compack)ois.readObject();

                                    //String info = br.readLine();
                                    System.out.println(B.type);
                                    //System.out.println("this is ok!");

                                    socket.shutdownInput();
                                    oos.close();
                                    os.close();
                                    ois.close();
                                    is.close();
                                    //br.close();

                                    socket.close();

                                } catch (UnsupportedEncodingException e){
                                    e.printStackTrace();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                } catch (ClassNotFoundException e){
                                    e.printStackTrace();
                                }
                            }

                        }  ).start();
                        //add code here


                        Intent intent2 = new Intent(this, loginActivity.class);
                        startActivity(intent2);
                        finish();
                        Toast.makeText(this,  "验证通过，注册成功", Toast.LENGTH_SHORT).show();*/
                    } else {
                        Toast.makeText(this, "验证码错误,注册失败", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(this, "未完善信息，注册失败", Toast.LENGTH_SHORT).show();
                }

                break;
        }
    }
    public class MyBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if(action.equals(COM_REGISTER_ACCEPTED)){
                Intent intent2 = new Intent(context, loginActivity.class);
                startActivity(intent2);
                Toast.makeText(context,  "验证通过，注册成功", Toast.LENGTH_SHORT).show();
                finish();
            }
            else if(action.equals(COM_REGISTER_REJECTED)){
                Toast.makeText(context, "用户名重复，请重新注册", Toast.LENGTH_SHORT).show();
            }

        }
    }
}

