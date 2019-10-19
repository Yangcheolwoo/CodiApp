package com.example.myapplication.Fragment.Menu3;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.example.myapplication.SaveSharedPreference;

import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import de.hdodenhof.circleimageview.CircleImageView;


public class MyCodiFragment3 extends Fragment {
    int GALLERY_CODE = 1111; //갤러리 진입시 반환 코드
    private String img_path = null; //원본이미지이름
    private static String[] PERMISSIONS_STORAGE = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    CodiImageAdapter adapter = new CodiImageAdapter();
    String userid, userprofileimage, userintro;
    TextView Textid;
    CircleImageView userimg;
    TextView intro;
    TextView markctn;
    TextView writingctn;
    Button editbtn;


    public MyCodiFragment3() {

    }

    public static MyCodiFragment3 newInstance() {
        return new MyCodiFragment3();
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_my_codi_fragment3, container, false);
        userid = SaveSharedPreference.getNowUserName(getContext());
        Textid = (TextView) view.findViewById(R.id.userid);
        userimg = (CircleImageView) view.findViewById(R.id.img);
        intro = (TextView) view.findViewById(R.id.intro);
        editbtn = (Button) view.findViewById(R.id.edit_profile);
        markctn = (TextView) view.findViewById(R.id.bookmarkcnt);
        writingctn = (TextView) view.findViewById(R.id.writingcnt);
        /*
        userimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectGallery();
            }
        });
        */
        editbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(getContext(), EditMyProfile.class);
                startActivityForResult(it, 1500);
            }
        });


        recyclerView = (RecyclerView) view.findViewById(R.id.image_recyclerview);

        recyclerView.setHasFixedSize(true);

        layoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);


        adapter.setItems(new BringUserData(userid, getContext()).getItems());
        recyclerView.setAdapter(adapter);

        userprofileimage = SaveSharedPreference.getUserProFileImage(getContext());

        userintro = SaveSharedPreference.getUserIntro(getContext());
        LoadUserInfo task1 = new LoadUserInfo(getContext());
        task1.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,userid);

        Textid.setText(userid);
        if(intro.equals("0")){
            intro.setText("");
        }else {
            intro.setText(userintro);
        }
        Glide.with(this).load("http://220.68.233.35:80/Codi/image/ProFileImage/" + userprofileimage).centerCrop().error(R.mipmap.ic_launcher).into(userimg);

        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == 3500) {
            if (requestCode == 1500) {
               // Toast.makeText(getContext(),"정보 수정중...", Toast.LENGTH_LONG).show();
                LoadUserInfo task = new LoadUserInfo(getContext());
                task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,userid);
            }
        }
    }

    class LoadUserInfo extends AsyncTask<String, Void, String>{
        String sendMsg,reciveMsg;
        String uri = "http://220.68.233.35:80/Codi/LoadUserInfo.jsp";
        Context context;

        public LoadUserInfo(Context context){
            this.context = context;
        }
        @Override
        protected String doInBackground(String... strings) {

            try {
                String str;

                URL url = new URL(uri);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestProperty("Accept-Charset", "utf-8");
                conn.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
                conn.setRequestMethod("POST");
                OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream());

                sendMsg = "id=" + strings[0];

                osw.write(sendMsg);
                osw.flush();

                if(conn.getResponseCode() == conn.HTTP_OK){
                    InputStreamReader tmp = new InputStreamReader(conn.getInputStream(),"UTF-8");
                    BufferedReader reader = new BufferedReader(tmp);
                    StringBuffer buffer = new StringBuffer();


                    while((str = reader.readLine()) != null) {
                        buffer.append(str);
                    }
                    reciveMsg = buffer.toString();
                }else {

                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e){
                e.printStackTrace();
            }
            return reciveMsg;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                JSONObject jsonObject = new JSONObject(s);
                String userid = jsonObject.getString("id");
                String userprofileimage = jsonObject.getString("profileimage");
                String userintro = jsonObject.getString("intro");
                String bookmarkctn = jsonObject.getString("bookmarkctn");
                String boardctn = jsonObject.getString("boardctn");
                //Toast.makeText(getContext(),"이미지수정 intro =" + userintro,Toast.LENGTH_SHORT).show();
                if(userintro.equals("0")){
                    userintro = "";
                }
                SaveSharedPreference.setUserProFileImage(getContext(),userprofileimage);
                SaveSharedPreference.setUserIntro(getContext(),userintro);
                intro.setText(userintro);
                markctn.setText(bookmarkctn);
                writingctn.setText(boardctn);
                Glide.with(getContext()).load("http://220.68.233.35:80/Codi/image/ProFileImage/" + userprofileimage).centerCrop().error(R.mipmap.ic_launcher).into(userimg);

            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
    //    private void selectGallery(){
//        Intent intent = new Intent(Intent.ACTION_PICK);
//        intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//        intent.setType("image/*");
//        startActivityForResult(intent, GALLERY_CODE);
//        // Toast.makeText(getApplicationContext(),"샐렉트 갤러리",Toast.LENGTH_SHORT).show();
//    }
//
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        if(requestCode == GALLERY_CODE) {
//            if (resultCode == Activity.RESULT_OK) {
//                try {
//                    img_path = getImageNameToUri(data.getData()); //이미지 절대 경로 추출
//                    //Toast.makeText(this,"img_path : " + img_path, Toast.LENGTH_SHORT).show();
//                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), data.getData()); //객체로부터 이미지를받아 비트맵화
//                    Toast.makeText(getContext(),""+img_path,Toast.LENGTH_LONG).show();
//                    UserProfileEditToServer task = new UserProfileEditToServer();
//                    task.execute(img_path,userid,"");
//
//                    userimg.setImageBitmap(bitmap); //이미지뷰에 비트맵화한 이미지 띄워주기
//                }catch(Exception e){
//                    e.printStackTrace();
//                }
//            }
//        }
//        super.onActivityResult(requestCode, resultCode, data);
//
//    }
//
//
//    private String getImageNameToUri(Uri data) {
//        String[] proj = {MediaStore.Images.Media.DATA,}; //저장소의 이미지데이터 저장
//
//        Cursor cursor = getActivity().getContentResolver().query(data, proj, null, null, null);
//        int column_data = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
//        cursor.moveToFirst();
//        String imgPath = cursor.getString(column_data);
//        Log.d("test",imgPath);
//        // String imgName = imgPath.substring(imgPath.lastIndexOf("/")+1);
//        //Toast.makeText(MainActivity.this,"이미지 이름:" +imgPath,Toast.LENGTH_SHORT).show();
//
//        return imgPath;
//    }

