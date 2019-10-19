package com.example.myapplication.Fragment.Menu3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;

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

public class OtherUserHomePage extends AppCompatActivity {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_user_home_page);
        Intent it = getIntent();
        userid = it.getExtras().getString("userid");

        Textid = (TextView) findViewById(R.id.userid);
        userimg = (CircleImageView) findViewById(R.id.img);
        intro = (TextView) findViewById(R.id.intro);
        editbtn = (Button) findViewById(R.id.edit_profile);
        markctn = (TextView) findViewById(R.id.bookmarkcnt);
        writingctn = (TextView) findViewById(R.id.writingcnt);
        /*
        userimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectGallery();
            }
        });
        */
        LoadUserInfo task = new LoadUserInfo(getApplicationContext());
        task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,userid);
        Textid.setText(userid);
        recyclerView = (RecyclerView) findViewById(R.id.image_recyclerview);

        recyclerView.setHasFixedSize(true);

        layoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);


        adapter.setItems(new BringUserData(userid, getApplicationContext()).getItems());
        recyclerView.setAdapter(adapter);

        //adapter.notifyDataSetChanged();
//        userprofileimage = SaveSharedPreference.getUserProFileImage(getContext());
//        userintro = SaveSharedPreference.getUserIntro(getContext());
//        Textid.setText(userid);
//        intro.setText(userintro);
//        Glide.with(this).load("http://220.68.233.35:80/Codi/image/ProFileImage/" + userprofileimage).centerCrop().error(R.mipmap.ic_launcher).into(userimg);

        // Inflate the layout for this fragment

    }

    class LoadUserInfo extends AsyncTask<String, Void, String> {
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
                //Toast.makeText(getApplicationContext(),"intro =" + userintro,Toast.LENGTH_SHORT).show();
                if(userintro.equals("0")){
                    userintro = "";
                }
                //Toast.makeText(getApplicationContext(),"intro =" + userintro,Toast.LENGTH_SHORT).show();
                intro.setText(userintro);
                markctn.setText(bookmarkctn);
                writingctn.setText(boardctn);
                Glide.with(getApplicationContext()).load("http://220.68.233.35:80/Codi/image/ProFileImage/" + userprofileimage).centerCrop().error(R.mipmap.ic_launcher).into(userimg);

            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
