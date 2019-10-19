package com.example.myapplication.Fragment.Menu4;

import android.content.Intent;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.myapplication.Fragment.Menu4.Boards.Board;
import com.example.myapplication.R;

public class DailyStyleActivity extends AppCompatActivity {
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_style);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.frame_layout, Board.newInstance()).commit();

        btn = (Button) findViewById(R.id.add);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"addClick",Toast.LENGTH_SHORT).show();
                Intent it = new Intent(getApplicationContext(),AddContext.class);
                startActivity(it);
            }

        });
    }
}
