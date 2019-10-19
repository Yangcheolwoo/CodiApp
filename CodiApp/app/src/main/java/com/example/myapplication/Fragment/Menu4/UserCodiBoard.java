package com.example.myapplication.Fragment.Menu4;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.Fragment.Menu4.Adapter.PageAdapter;
import com.example.myapplication.R;

public class UserCodiBoard extends Fragment {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    public UserCodiBoard() {
        // Required empty public constructor
    }

    public static UserCodiBoard newInstance() {

        return new UserCodiBoard();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_codi_board,container,false);

        tabLayout = (TabLayout) view.findViewById(R.id.tabLayout);
        tabLayout.addTab(tabLayout.newTab().setText("Daily"));
        tabLayout.addTab(tabLayout.newTab().setText("Hippop"));
        tabLayout.addTab(tabLayout.newTab().setText("Self"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);


        viewPager = (ViewPager) view.findViewById(R.id.viewPage);

        PageAdapter pa = new PageAdapter(getActivity().getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(pa);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        return view;
    }
}
