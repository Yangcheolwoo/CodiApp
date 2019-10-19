package com.example.myapplication.Fragment.Menu4;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.myapplication.Fragment.Menu4.Boards.NeedCodiBoard;
import com.example.myapplication.R;


public class TemaCodiFragment4 extends Fragment {
    Button nbtn,ubtn;
    private TabLayout tabLayout;
    public TemaCodiFragment4(){

    }

    public static TemaCodiFragment4 newInstance() {
        return new TemaCodiFragment4();
        // Required empty public constructor
    }



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tema_codi_fragment4, container, false);
//        nbtn = (Button) view.findViewById(R.id.one);
//        ubtn = (Button) view.findViewById(R.id.two);
//        nbtn.setOnClickListener(this);
//        ubtn.setOnClickListener(this);
        tabLayout = (TabLayout) view.findViewById(R.id.tabLayout);
        tabLayout.addTab(tabLayout.newTab().setText("NEED CODI"));
        tabLayout.addTab(tabLayout.newTab().setText("USER CODI"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int pos = tab.getPosition();

                if(pos == 0){
                    replaceFragment(NeedCodiBoard.newInstance());
                }
                else if(pos == 1){
                    replaceFragment(UserCodiBoard.newInstance());
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
        fragmentTransaction.replace(R.id.frame_layout, NeedCodiBoard.newInstance()).commit();


        return view;
    }

    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout,fragment).commit();
    }
}
