package com.example.myapplication.Fragment.Menu3;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.myapplication.Fragment.Menu4.HttpConnection.UserProfileEditToServer;
import com.example.myapplication.R;
import com.example.myapplication.SaveSharedPreference;
import com.example.myapplication.ServerUri;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditMyProfile extends AppCompatActivity {
    int GALLERY_CODE = 1111; //갤러리 진입시 반환 코드
    String img_path = null; //원본이미지이름
    private static String[] PERMISSIONS_STORAGE = {Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE};
    String iuri = new ServerUri().uri+"image/";
    Button registerbtn;
    CircleImageView userimg;
    EditText intro;
    String userid,nowuserimg,userintro;
    Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_my_profile);
        context = this;
        nowuserimg = SaveSharedPreference.getUserProFileImage(getApplicationContext());
        userintro = SaveSharedPreference.getUserIntro(getApplicationContext());
        userid = SaveSharedPreference.getNowUserName(getApplicationContext());
        registerbtn = (Button) findViewById(R.id.register);
        userimg = (CircleImageView)findViewById(R.id.img);
        intro = (EditText) findViewById(R.id.intro);

        Glide.with(this).load(iuri+"ProFileImage/" + nowuserimg).centerCrop().error(R.mipmap.ic_launcher).into(userimg);
        intro.setText(userintro);

        userimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectGallery();
            }
        });
        registerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(3500);
                String content = intro.getText().toString();
                try{
                    UserProfileEditToServer task = new UserProfileEditToServer(context,EditMyProfile.this);
                    task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,img_path,userid,content);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

    }
    private void selectGallery(){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(intent, GALLERY_CODE);
        // Toast.makeText(getApplicationContext(),"샐렉트 갤러리",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == GALLERY_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                try {
                    img_path = getImageNameToUri(data.getData()); //이미지 절대 경로 추출
                    //Toast.makeText(this,"img_path : " + img_path, Toast.LENGTH_SHORT).show();
                    //Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData()); //객체로부터 이미지를받아 비트맵화
                    //Toast.makeText(getContext(),""+img_path,Toast.LENGTH_LONG).show();
                    Glide.with(getApplicationContext()).load(img_path).centerCrop().error(R.mipmap.ic_launcher).into(userimg);
                    //userimg.setImageBitmap(bitmap); //이미지뷰에 비트맵화한 이미지 띄워주기
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);

    }


    private String getImageNameToUri(Uri data) {
        String[] proj = {MediaStore.Images.Media.DATA,}; //저장소의 이미지데이터 저장

        Cursor cursor = getContentResolver().query(data, proj, null, null, null);
        int column_data = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String imgPath = cursor.getString(column_data);
        Log.d("test",imgPath);
        // String imgName = imgPath.substring(imgPath.lastIndexOf("/")+1);
        //Toast.makeText(MainActivity.this,"이미지 이름:" +imgPath,Toast.LENGTH_SHORT).show();

        return imgPath;
    }



}
