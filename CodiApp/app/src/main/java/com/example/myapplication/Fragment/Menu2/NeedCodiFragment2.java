package com.example.myapplication.Fragment.Menu2;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.myapplication.Fragment.Menu4.Boards.NeedCodiBoard;
import com.example.myapplication.Fragment.Menu4.UserCodiBoard;
import com.example.myapplication.R;
import com.google.android.material.tabs.TabLayout;


public class NeedCodiFragment2 extends Fragment {

    EditText search;
    private TabLayout tabLayout;

    public NeedCodiFragment2(){

    }

    public static NeedCodiFragment2 newInstance() {
        return new NeedCodiFragment2();
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_need_codi_fragment2, container, false);

        search = (EditText) view.findViewById(R.id.search);
        search.setFocusable(false);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(getContext(),SearchActivity.class);
                getContext().startActivity(it);
                getActivity().overridePendingTransition(R.anim.anim_slide_in_left,R.anim.anim_slide_out_right);
            }
        });

        tabLayout = (TabLayout) view.findViewById(R.id.tabLayout);
        tabLayout.addTab(tabLayout.newTab().setText("BookMark"));
        tabLayout.addTab(tabLayout.newTab().setText("Scrap"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int pos = tab.getPosition();

                if(pos == 0){
                    replaceFragment(BookMark.newInstance());
                }
                else if(pos == 1){
                    replaceFragment(Scrap.newInstance());
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, BookMark.newInstance()).commit();


        return view;
    }

    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout,fragment).commit();
    }

}
