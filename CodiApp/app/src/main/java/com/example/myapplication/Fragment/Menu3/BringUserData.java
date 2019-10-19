package com.example.myapplication.Fragment.Menu3;

import android.content.Context;
import android.os.AsyncTask;

import com.example.myapplication.Fragment.Menu4.HttpConnection.BringUserDataFromServer;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class BringUserData {

    Context context;
    String userid;
    public BringUserData(String userid ,Context context) {
        this.userid = userid;
        this.context = context;
    }

    ArrayList<UserData> items = new ArrayList<>();

    public ArrayList<UserData> getItems(){
        try {

            BringUserDataFromServer task = new BringUserDataFromServer();

            String result = task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,userid).get();
            JSONObject jsonObject = new JSONObject(result);
            JSONArray jsonArray = jsonObject.getJSONArray("boardData");

            for(int i=0; i<jsonArray.length(); i++){
                jsonObject = jsonArray.getJSONObject(i);
                UserData item = new UserData();
                item.boardid = jsonObject.getString("id");
                item.id=jsonObject.getString("userid");
                item.title =jsonObject.getString("title");
                item.content = jsonObject.getString("content");
                item.time = jsonObject.getString("time");
                item.imgpath=jsonObject.getString("imagepath");
                item.boardname = jsonObject.getString("boardname");
                item.userprofileimage = jsonObject.getString("profileimage");
                item.likecount = jsonObject.getString("likecount");
                //item.toggle = jsonObject.getString("flag");


                items.add(item);
            }


        }catch (Exception e){

        }finally {

            return items;
        }
    }
}
