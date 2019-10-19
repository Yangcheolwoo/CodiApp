package com.example.myapplication.Fragment.Menu4;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.myapplication.Fragment.Menu4.HttpConnection.BoardDataSendToServer;
import com.example.myapplication.Fragment.Menu4.HttpConnection.NeedBoardDataSendToServer;
import com.example.myapplication.R;
import com.example.myapplication.SaveSharedPreference;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import dmax.dialog.SpotsDialog;

public class AddContext extends AppCompatActivity  {
    int GALLERY_CODE = 1111; //갤러리 진입시 반환 코드
    Activity activity = new Activity();
    //private BoardAdapter adapter = new BoardAdapter();
    private String img_path = null; //원본이미지이름
    private String rimg_path = null; //이미지이름 변경
    String SaveUserId; //로그인한 아이디
    private static final int REQUEST_EXTERNAL_STORAGE =1;
    //허가받아야할 퍼미션들 저장 String배열
    private static String[] PERMISSIONS_STORAGE = {Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE};
    Button register,cancel;
    EditText content,title;
    ImageView iv;
    String boardname;
    Context context;
    SpotsDialog dialog;
    Handler timer = new Handler(Looper.getMainLooper());
    String reciveMSG;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_context);
        SaveUserId = SaveSharedPreference.getNowUserName(getApplicationContext());
        register = (Button) findViewById(R.id.register);
        cancel = (Button) findViewById(R.id.cancel);
        title = (EditText) findViewById(R.id.title);
        content = (EditText) findViewById(R.id.content);
        iv = (ImageView) findViewById(R.id.image);

        context = this;


        Intent it = getIntent();
        boardname = it.getExtras().getString("boardname");

        activity = AddContext.this;
        //현재 api가 23이상이면 checkVerify메소드 호출


        //Toast.makeText(getApplicationContext(), "" + items.get(0).getTitle() + " /  "+ items.get(1).getTitle(), Toast.LENGTH_LONG).show();
        /*
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            checkVerify();
        }
        */
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                selectGallery();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(),"등록완료",Toast.LENGTH_LONG).show();
                setResult(3000);
                String tl = title.getText().toString();
                String ct = content.getText().toString();


                String id = SaveSharedPreference.getNowUserName(getApplicationContext()); //userid


//                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.KOREA);
//                Date date = new Date();
//                String c_date = formatter.format(date);
//                String now = CurrentTime();
//                rimg_path = "Codi_" + SaveUserId +"_"+ now + img_path.substring(img_path.lastIndexOf("."),img_path.length());

                ExecutorService threadPool = Executors.newFixedThreadPool(3);
                if(boardname.equals("need")){
                    NeedBoardDataSendToServer task = new NeedBoardDataSendToServer(context,activity);
                    try {
                        //reciveMSG = task.executeOnExecutor(threadPool, img_path, id, tl, ct, boardname).get();//jsp서버주소,이미지 경로 서버로 전송
                        task.executeOnExecutor(threadPool, img_path, id, tl, ct, boardname);
                    }catch(Exception e){

                    }
                }else {
                    BoardDataSendToServer task = new BoardDataSendToServer(context,activity); //asynctask 스레드 객체생성
                    try {
                        //reciveMSG = task.executeOnExecutor(threadPool, img_path, id, tl, ct, boardname).get();//jsp서버주소,이미지 경로 서버로 전송
                        task.executeOnExecutor(threadPool, img_path, id, tl, ct, boardname);
                    }catch (Exception e){

                    }
                }
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
    /*
    //퍼미션이 허락되었는지 확인
    @TargetApi(Build.VERSION_CODES.M)
    public void checkVerify()
    {
        if (//저장소에 권한이 있는지 확인하는 버튼
                checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                        checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
        )
        {
            // 권한요청팝업 한번이라도 거부하면 다시묻지않음 체크버튼이 함께 나타나면서 다시 권한요청팝업이 뜨도록
            if (shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE))
            {
                // ...
            }
            //권한팝업 요청 메소드
            requestPermissions(PERMISSIONS_STORAGE, REQUEST_EXTERNAL_STORAGE);
        }
        else
        {
            //startApp();
        }
    }
    //저장소 접근 퍼미션을 거절할 경우 다시한번 알림메세지 띄워주기
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 1)
        {
            if (grantResults.length > 0)
            {
                for (int i=0; i<grantResults.length; ++i)
                {
                    if (grantResults[i] == PackageManager.PERMISSION_DENIED)
                    {
                        // 하나라도 거부한다면.
                        new AlertDialog.Builder(this)
                                .setTitle("알림").setMessage("권한을 허용해주셔야 앱을 이용할 수 있습니다.")
                                .setPositiveButton("종료", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        activity.finish();
                                    }
                                }).setNegativeButton("권한 설정", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                                        .setData(Uri.parse("package:" + getApplicationContext().getPackageName()));
                                getApplicationContext().startActivity(intent);
                            }
                        }).setCancelable(false).show();
                        return;
                    }
                }
                //startApp();
            }
        }
    }
    */
    //사진앨범 접속 메소드
    private void selectGallery(){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(intent, GALLERY_CODE);
        // Toast.makeText(getApplicationContext(),"샐렉트 갤러리",Toast.LENGTH_SHORT).show();
    }
    //사진앨범으로부터 이미지 경로 추출해서 이미지뷰에 사진 띄우기
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == GALLERY_CODE) {
            if (resultCode == RESULT_OK) {
                try {
                    img_path = getImageNameToUri(data.getData()); //이미지 절대 경로 추출
                    //Toast.makeText(this,"img_path : " + img_path, Toast.LENGTH_SHORT).show();
                    Glide.with(getApplicationContext()).load(img_path).centerCrop().error(R.mipmap.ic_launcher).into(iv);
                   // Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), data.getData()); //객체로부터 이미지를받아 비트맵화
                    //iv.setImageBitmap(bitmap); //이미지뷰에 비트맵화한 이미지 띄워주기
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);

    }
    //사진이 저장된 절대 경로가져오기
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
//    public String CurrentTime() {
//        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss", Locale.KOREA);
//        Date date = new Date();
//        String currentDate = formatter.format(date);
//        return currentDate;
//    }


}

