package com.example.justloginregistertest.ui.dashboard;

import android.content.Context;
import android.content.*;
import android.content.Intent;
import android.os.Bundle;
//import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.justloginregistertest.R;
import com.example.justloginregistertest.threads.NetService;
import com.example.justloginregistertest.ui.home.HomeFragment;
import com.example.justloginregistertest.ClassSpace.*;

public class DashboardFragment extends Fragment {

    private DashboardViewModel dashboardViewModel;
    private Button add_return;
    private Button add_confirm;
    private EditText title;
    private EditText detailed;


    public View onCreateView(@NonNull final LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);

        add_return = (Button)root.findViewById(R.id.add_return);
        add_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
                getFragmentManager()
                        .beginTransaction()
                        .addToBackStack(null)
                        .replace(R.id.nav_host_fragment,new HomeFragment()).commit();
            }
        });

        add_confirm = (Button)root.findViewById(R.id.add_confirm);
        title = (EditText) root.findViewById(R.id.title);
        detailed = (EditText) root.findViewById(R.id.detail);
        add_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title_contents = null;
                String detailed_contents = null;

                title_contents = title.getText().toString();
                detailed_contents = detailed.getText().toString();

                Message data = new Message("tan", "text", title_contents, detailed_contents, -1);
                compack send = new compack("add_message", 1, 0, data, true);

                Intent intent = new Intent(getActivity(), NetService.class);
                intent.putExtra("send", send);
                intent.putExtra("need_send", true);
                getActivity().startService(intent);


                getFragmentManager().popBackStack();
                getFragmentManager()
                        .beginTransaction()
                        .addToBackStack(null)
                        .replace(R.id.nav_host_fragment,new HomeFragment()).commit();
            }
        });
        return root;
    }
}