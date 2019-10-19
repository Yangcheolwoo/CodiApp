package com.example.myapplication.Fragment.Menu4;

import android.content.Intent;
import android.os.AsyncTask;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.Fragment.Menu4.HttpConnection.SendCommentToDB;
import com.example.myapplication.R;
import com.example.myapplication.SaveSharedPreference;

public class ShowContext extends AppCompatActivity {
    EditText coment;
    Button comentbtn;
    String usercoment;
    String nowuserid;
    String boardid;
    String boardname;
    Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_context);
        nowuserid = SaveSharedPreference.getNowUserName(getApplicationContext());
        Intent it = getIntent();

        boardid = it.getExtras().getString("boardid");
        String userid = it.getExtras().getString("userid");
        String context = it.getExtras().getString("content");
        String time = it.getExtras().getString("usertime");
        boardname = it.getExtras().getString("boardname");
        String imagepath = it.getExtras().getString("img");
        String userimg = it.getExtras().getString("userimg");

        Bundle bundle = new Bundle();
        bundle.putString("boardid",boardid);
        bundle.putString("userid",userid);
        bundle.putString("content",context);
        bundle.putString("time",time);
        bundle.putString("boardname",boardname);
        bundle.putString("userimg",userimg);
        bundle.putString("imagepath",imagepath);

        fragment = new ShowContextIntoData();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragment.setArguments(bundle);
        fragmentTransaction.replace(R.id.frame_frag,fragment).commit();

        coment = (EditText) findViewById(R.id.edit_comment);
        comentbtn = (Button) findViewById(R.id.comment_button);

        comentbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(coment.getText().toString().length() == 0){
                    Toast.makeText(getApplicationContext(),"댓글을 입력하세요.",Toast.LENGTH_SHORT).show();
                }else{
                    usercoment = coment.getText().toString();
                    SendCommentToDB task = new SendCommentToDB();
                    task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,boardid,nowuserid,usercoment,boardname);

                    coment.setText("");
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.detach(fragment).attach(fragment).commit();

                }
            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();

    }
}
