package com.example.justloginregistertest.ui.home;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.example.justloginregistertest.ListMessage.ListViewAdapter;
import com.example.justloginregistertest.MainActivity;
import com.example.justloginregistertest.R;
import com.example.justloginregistertest.loginActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.example.justloginregistertest.ClassSpace.*;

public class HomeFragment extends Fragment {

    private ListView listView;
    private LocalBroadcastManager mLocalBroadcastManager;
    private HomeFragment.MyBroadcastReceiver mBroadcastReceiver;
    private List<Map<String, Object>> list;
    public static String COM_GET_MESSAGE = "com.get.message";


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBroadcastReceiver = new HomeFragment.MyBroadcastReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(COM_GET_MESSAGE);

        mLocalBroadcastManager = LocalBroadcastManager.getInstance(getActivity());
        mLocalBroadcastManager.registerReceiver(mBroadcastReceiver, intentFilter);
    }

    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_home , container, false);
        listView = (ListView)view.findViewById(R.id.listview);
        list=getData();
        listView.setAdapter(new ListViewAdapter(getActivity(), list));
        return view;
    }

    public List<Map<String, Object>> getData(){
        List<Map<String, Object>> list=new ArrayList<Map<String,Object>>();
        for (int i = 0; i < 10; i++) {
            Map<String, Object> map=new HashMap<String, Object>();
            map.put("image", R.drawable.ic_launcher_foreground);
            map.put("title", "11");
            map.put("info", "11");
            list.add(map);
        }
        return list;
    }
    public List<Map<String, Object>> getData(Message x[], int maxnum){
        List<Map<String, Object>> list=new ArrayList<Map<String,Object>>();
        for (int i = 0; i < maxnum; i++) {
            Map<String, Object> map=new HashMap<String, Object>();
            map.put("image", R.drawable.ic_launcher_foreground);
            map.put("title", x[i].title);
            map.put("info", x[i].detailed);
            list.add(map);
        }
        return list;
    }


    public class MyBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if(action.equals(COM_GET_MESSAGE)){
                compack local = (compack)intent.getSerializableExtra("data_list");
                //Message x[] = new Message[100];
                compack x[] = new compack[100];
                Message data[];
                int maxnum = 0;
                for(maxnum = 0;;maxnum++){
                    x[maxnum] = (compack)local.data[maxnum];
                    if(x[maxnum].is_last_pack){
                        maxnum++;
                        data = new Message[maxnum];
                        break;
                    }
                }
                for(int i = 0;i<maxnum;i++){
                    data[i] = (Message)x[i].data[0];
                }
                list = getData(data,maxnum);





            }
        }
    }

}