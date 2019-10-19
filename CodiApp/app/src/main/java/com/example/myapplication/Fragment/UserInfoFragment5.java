package com.example.myapplication.Fragment;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.R;


public class UserInfoFragment5 extends Fragment {

    public UserInfoFragment5(){

    }

    public static UserInfoFragment5 newInstance(){
        return new UserInfoFragment5();
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_info_fragment5, container, false);
    }

}
