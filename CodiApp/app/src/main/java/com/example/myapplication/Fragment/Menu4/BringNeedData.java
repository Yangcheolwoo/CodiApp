package com.example.myapplication.Fragment.Menu4;

import android.content.Context;

import com.example.myapplication.Fragment.Menu4.HttpConnection.bringBoarddb;
import com.example.myapplication.Fragment.Menu4.HttpConnection.bringNeedBoarddb;
import com.example.myapplication.SaveSharedPreference;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class BringNeedData {

    String boardname;
    String add = "add";
    Context context;
    String userid;
    public BringNeedData(String boardname , Context context) {
        this.boardname = boardname;
        this.context = context;
        userid = SaveSharedPreference.getNowUserName(context);
    }

    ArrayList<ItemData> items = new ArrayList<>();

    public ArrayList<ItemData> getItems(){
        try {

            bringNeedBoarddb task = new bringNeedBoarddb();

            String result = task.execute(userid).get();
            JSONObject jsonObject = new JSONObject(result);
            JSONArray jsonArray = jsonObject.getJSONArray("NeedData");

            for(int i=0; i<jsonArray.length(); i++){
                jsonObject = jsonArray.getJSONObject(i);
                ItemData item = new ItemData();
                item.boardid = jsonObject.getString("id");
                item.id=jsonObject.getString("userid");
                item.title =jsonObject.getString("title");
                item.content = jsonObject.getString("content");
                item.time = jsonObject.getString("time");
                item.imgpath=jsonObject.getString("imagepath");
                item.boardname = jsonObject.getString("boardname");
                item.likecount = jsonObject.getString("likecount");
                item.userprofileimage = jsonObject.getString("profileimage");
                item.toggle = jsonObject.getString("flag");


                items.add(item);
            }


        }catch (Exception e){

        }finally {

            return items;
        }
    }
}
