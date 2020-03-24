package com.example.justloginregistertest.ui.notifications;

import android.os.Bundle;
import android.os.Trace;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.justloginregistertest.ClassSpace.Message;
import com.example.justloginregistertest.ClassSpace.Users;
import com.example.justloginregistertest.R;

public class NotificationsFragment extends Fragment {

    private NotificationsViewModel notificationsViewModel;
    //TextView user_name ;
    //private String temp = Users.getUsername();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_notifications, container, false);

        //user_name = root.findViewById(R.id.user_name);
        //user_name.setText(temp);

        return root;
    }
}