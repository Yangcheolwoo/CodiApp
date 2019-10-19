package com.example.myapplication.Fragment.Menu1;

import androidx.annotation.NonNull;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;


public class HomeFragment1 extends Fragment{
    RecyclerView recyclerView;
    RankDataDecoration deco;
    TextView rankmenu;
    String type = "month";
    RankAdapter adapter = new RankAdapter(this);

    public HomeFragment1() {
        // Required empty public constructor
    }
    public static HomeFragment1 newInstance() {
        HomeFragment1 fragment = new HomeFragment1();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_fragment1,container,false);
        Toolbar tb = (Toolbar) view.findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(tb);

        rankmenu = (TextView) view.findViewById(R.id.rankdate);
        setHasOptionsMenu(true);
        rankmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"제목 클릭",Toast.LENGTH_SHORT).show();
                showPopup(v);
            }
        });

        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.rank_frame, MonthRank.newInstance()).commit();

        return view;
    }

    public void showPopup(View v){
        PopupMenu popup = new PopupMenu(getContext(),v);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.rank_menu_list,popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch(item.getItemId()){
                    case R.id.rank1:
                        rankmenu.setText(item.getTitle());
                        replaceFragment(MonthRank.newInstance());
                        break;

                    case R.id.rank2:
                        rankmenu.setText(item.getTitle());
                        replaceFragment(WeekRank.newInstance());
                        break;

                    case R.id.rank3:
                        rankmenu.setText(item.getTitle());
                        replaceFragment(DayRank.newInstance());
                        break;
                }
                return false;
            }
        });

        popup.show();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.rank_frame,fragment).commit();
    }
}
