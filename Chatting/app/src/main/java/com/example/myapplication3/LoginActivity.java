package com.example.myapplication3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    Button registerbtn, loginbtn;
    CheckBox cb;
    EditText logidet, logpwet;
    String result;
    ConstraintLayout cl;
    Activity activity = this;
    final Context context = this;
    InputMethodManager im; //키보드 내리기
    AlertDialog.Builder alerBuilder = null;

    private static final int REQUEST_EXTERNAL_STORAGE =1;
    //허가받아야할 퍼미션들 저장 String배열
    private static String[] PERMISSIONS_STORAGE = {Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE); //키보드 내리기
        alerBuilder = new AlertDialog.Builder(context);

        setTitle("코디가 필요해");

//        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
//        {
//            checkVerify();
//        }


        if (SaveSharedPreference.getAutoUserName(LoginActivity.this).equals("")) {
            //자동로그인체크를 안할시 현재 이 로그인 엑티비티 실행
        }else{
            Intent it = new Intent(this, MainActivity.class); //자동로그인시 메인홈엑티비티 실행
            startActivity(it);
        }

        cl = (ConstraintLayout) findViewById(R.id.wlayout); //키보드내리기위해 전체 레이아웃
        registerbtn = (Button) findViewById(R.id.registerButton);
        loginbtn = (Button) findViewById(R.id.loginbutton);
        cb = (CheckBox) findViewById(R.id.auto);
        logidet = (EditText) findViewById(R.id.loginid);
        logpwet = (EditText) findViewById(R.id.loginpw);

        //로그인,회원가입버튼 리스너에 연결
        loginbtn.setOnClickListener(myClickListener);
        registerbtn.setOnClickListener(myClickListener);

        cl.setOnClickListener(new View.OnClickListener() { //키보드 숨기기
            @Override
            public void onClick(View v) {
                hidekeyboard();
            }
        });
    }
    View.OnClickListener myClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
//                case R.id.registerButton:
//                {
//                    Intent it = new Intent(getApplicationContext(), Newperson.class);
//                    startActivity(it);
//                }
//                break;
                case R.id.loginbutton:
                {
                    try {
                        String id = logidet.getText().toString();
                        String pw = logpwet.getText().toString();

                        LogincheckActivity task = new LogincheckActivity();
                        result = task.execute(id, pw).get();
                        Log.e("RECVDATA",result);
                        if(result.equals("Wrongpw")){
                            alerBuilder
                                    .setTitle("알림")
                                    .setMessage(("비밀번호가 틀렸습니다."))
                                    .setCancelable(true)
                                    .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) { }
                                    });
                            AlertDialog dialog = alerBuilder.create();
                            dialog.show();
                        }else if(result.equals("NoId")){
                            alerBuilder
                                    .setTitle("알림")
                                    .setMessage(("아이디가 등록되어있지 않습니다. 회원가입해주세요."))
                                    .setCancelable(true)
                                    .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) { }
                                    });
                            AlertDialog dialog = alerBuilder.create();
                            dialog.show();
                        }else{
                            JSONObject jsonObject = new JSONObject(result);
                            String userid = jsonObject.getString("id");
                            String userprofileimage = jsonObject.getString("profileimage");
                            String intro = jsonObject.getString("intro");
                            if(intro.equals("0")){
                                intro = "";
                            }
                            //Toast.makeText(getApplicationContext(),"userid="+userid + "\nprofile=" + userprofileimage+"\nintro=" + intro, Toast.LENGTH_LONG).show();
                            SaveSharedPreference.setNowUserName(getApplicationContext(), userid);
                            if(cb.isChecked()) { //자동로그인체크 했을경우
                                SaveSharedPreference.setAutoUserName(getApplicationContext(), userid);//입력한 아이디 저장
                            }
                            SaveSharedPreference.setUserProFileImage(getApplicationContext(),userprofileimage);
                            SaveSharedPreference.setUserIntro(getApplicationContext(),intro);
                            alerBuilder
                                    .setTitle("알림")
                                    .setMessage(("로그인이 완료되었습니다."))
                                    .setCancelable(true)
                                    .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Intent it = new Intent(LoginActivity.this, MainActivity.class);
                                            startActivity(it);
                                        }
                                    });
                            AlertDialog dialog = alerBuilder.create();
                            dialog.show();

                        }

                    } catch (Exception e) {
                        Log.i("DBtest", ".....ERROR.....!");
                    }
                }
                break;
            }
        }
    };

    public void hidekeyboard() {
        im.hideSoftInputFromWindow(logidet.getWindowToken(),0);
        im.hideSoftInputFromWindow(logpwet.getWindowToken(),0);
    }
//    @TargetApi(Build.VERSION_CODES.M)
//    public void checkVerify()
//    {
//        if (//저장소에 권한이 있는지 확인하는 버튼
//                checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
//                        checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
//        )
//        {
//            // 권한요청팝업 한번이라도 거부하면 다시묻지않음 체크버튼이 함께 나타나면서 다시 권한요청팝업이 뜨도록
//            if (shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE))
//            {
//                // ...
//            }
//            //권한팝업 요청 메소드
//            requestPermissions(PERMISSIONS_STORAGE, REQUEST_EXTERNAL_STORAGE);
//        }
//        else
//        {
//            //startApp();
//        }
//    }
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
//    {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//
//        if (requestCode == 1)
//        {
//            if (grantResults.length > 0)
//            {
//                for (int i=0; i<grantResults.length; ++i)
//                {
//                    if (grantResults[i] == PackageManager.PERMISSION_DENIED)
//                    {
//                        // 하나라도 거부한다면.
//                        new AlertDialog.Builder(this)
//                                .setTitle("알림").setMessage("권한을 허용해주셔야 앱을 이용할 수 있습니다.")
//                                .setPositiveButton("종료", new DialogInterface.OnClickListener() {
//                                    public void onClick(DialogInterface dialog, int which) {
//                                        dialog.dismiss();
//                                        activity.finish();
//                                    }
//                                }).setNegativeButton("권한 설정", new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int which) {
//                                dialog.dismiss();
//                                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
//                                        .setData(Uri.parse("package:" + getApplicationContext().getPackageName()));
//                                getApplicationContext().startActivity(intent);
//                            }
//                        }).setCancelable(false).show();
//                        return;
//                    }
//                }
//                //startApp();
//            }
//        }
//    }
}
