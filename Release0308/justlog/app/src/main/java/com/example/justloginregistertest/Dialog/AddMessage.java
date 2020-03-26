package com.example.justloginregistertest.Dialog;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.os.Bundle;

import com.example.justloginregistertest.MainActivity;
import com.example.justloginregistertest.R;
import com.example.justloginregistertest.RegisterActivity;
import com.example.justloginregistertest.ui.home.HomeFragment;

public class AddMessage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //让布局向上移来显示软键盘
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.fragment_dashboard);

        Button add_confirm = (Button)findViewById(R.id.add_confirm);
        Button add_return = (Button)findViewById(R.id.add_return);

        add_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AddMessage.this, MainActivity.class);
                startActivity(i);
            }
        });

        add_return.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AddMessage.this,MainActivity.class);
                startActivity(i);
            }
        });

    }

}
