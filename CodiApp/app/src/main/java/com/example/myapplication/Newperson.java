package com.example.myapplication;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class Newperson extends AppCompatActivity {

    Button registerbtn;
    EditText idet, pwet, repwet, nameet, nicknameet;
    String result;
    ConstraintLayout cl;
    InputMethodManager im; //키보드 내리기
    final Context context = this; // RegusterActivity에 alert사용할 수있게


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newperson);
        final AlertDialog.Builder alerBuilder = new AlertDialog.Builder(context);

        im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE); //키보드 내리기

        setTitle("회원가입");

        cl = (ConstraintLayout) findViewById(R.id.wlayout); //키보드내리기위해 전체 레이아웃
        registerbtn = (Button) findViewById(R.id.joinbtn);
        idet = (EditText) findViewById(R.id.idemail);
        pwet = (EditText) findViewById(R.id.joinpw);
        repwet = (EditText) findViewById(R.id.joinrepw);
        nameet = (EditText) findViewById(R.id.name);
        nicknameet = (EditText) findViewById(R.id.nickname);

        cl.setOnClickListener(new View.OnClickListener() { //키보드 숨기기
            @Override
            public void onClick(View v) {
                hidekeyboard();
            }
        });

        registerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String id = idet.getText().toString();
                    String pw = pwet.getText().toString();
                    String ckpw = repwet.getText().toString();
                    String name = nameet.getText().toString();
                    String nickname = nicknameet.getText().toString();

                    if(pw.equals(ckpw)) {
                        RegisterActivity task = new RegisterActivity();
                        result = task.execute(id, pw, name, nickname).get();
                        if(result.equals("existence")){
                            alerBuilder
                                    .setTitle("알림")
                                    .setMessage(("이미 존재하는 ID입니다."))
                                    .setCancelable(true)
                                    .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) { }
                                    });
                            AlertDialog dialog = alerBuilder.create();
                            dialog.show();
                        }
                        else if(result.equals("Successjoin")) {
                            alerBuilder
                                    .setTitle("알림")
                                    .setMessage(("회원가입이 완료되었습니다."))
                                    .setCancelable(true)
                                    .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            finish();
                                        }
                                    });
                            AlertDialog dialog = alerBuilder.create();
                            dialog.show();
                        }
                        else{
                            Toast.makeText(getApplicationContext(),"requst" + result,Toast.LENGTH_SHORT).show();
                        }
                        result = "";
                    }
                    else {
                        Toast.makeText(getApplicationContext(),"비밀번호 다시확인해 주세요",Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Log.i("DBtest", ".....ERROR.....!");
                }
            }

        });
    }
    public void hidekeyboard() {
        im.hideSoftInputFromWindow(idet.getWindowToken(),0);
        im.hideSoftInputFromWindow(pwet.getWindowToken(),0);
        im.hideSoftInputFromWindow(repwet.getWindowToken(),0);
        im.hideSoftInputFromWindow(nameet.getWindowToken(),0);
        im.hideSoftInputFromWindow(nicknameet.getWindowToken(),0);
    }
}


