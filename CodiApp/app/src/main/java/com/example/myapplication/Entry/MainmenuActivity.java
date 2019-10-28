package com.example.myapplication.Entry;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;

import com.example.myapplication.Entry.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.SaveSharedPreference;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.myapplication.Fragment.Menu1.HomeFragment1;
import com.example.myapplication.Fragment.Menu3.MyCodiFragment3;
import com.example.myapplication.Fragment.Menu2.NeedCodiFragment2;
import com.example.myapplication.Fragment.Menu4.TemaCodiFragment4;
import com.example.myapplication.Fragment.UserInfoFragment5;

public class MainmenuActivity extends AppCompatActivity {


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {

                case R.id.navigation_menu1:{
                    replaceFragment(HomeFragment1.newInstance());
                    return true;
                }
                case R.id.navigation_menu2:{
                    replaceFragment(NeedCodiFragment2.newInstance());
                    return true;
                }
                case R.id.navigation_menu3: {
                    replaceFragment(MyCodiFragment3.newInstance());
                    return true;
                }
                case R.id.navigation_menu4: {
                    replaceFragment(TemaCodiFragment4.newInstance());
                    return true;
                }
                case R.id.navigation_menu5: {
                    replaceFragment(UserInfoFragment5.newInstance());
                    return true;
                }
            }
            return false;
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainmenu);
        String userid = SaveSharedPreference.getNowUserName(getApplicationContext());
        //Toast.makeText(getApplicationContext(),""+userid,Toast.LENGTH_LONG).show();
        //toolbar
        Toolbar tb = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(tb);


        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout,HomeFragment1.newInstance()).commit();
    }

    private  void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout,fragment).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.setting:
                SaveSharedPreference.clearUserName(getApplicationContext()); // 로그아웃
                Intent it = new Intent(getApplication(), MainActivity.class);
                startActivity(it);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    public void onBackPressed() {

    }
}
