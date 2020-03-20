package com.example.justloginregistertest.ui.newthing;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.justloginregistertest.R;

public class NewFragment extends Fragment {

    private NewViewModel newViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        newViewModel =
                ViewModelProviders.of(this).get(NewViewModel.class);
        //上面的方法已经过时，但是不妨碍我们使用它，可以以后再修改，现在主要是入个门
        View root = inflater.inflate(R.layout.fragment_new, container, false);
        final TextView textView = root.findViewById(R.id.text_new);
        //下面代码中observe()方法里的this，可能在IDE里是有红色波浪线的，但是却是可以编译的。
        //也可以直接把this改成getViewLifecycleOwner() 就不会报错了
        newViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}
