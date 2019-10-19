package com.example.myapplication.Fragment.Menu4;

import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.myapplication.Fragment.Menu4.HttpConnection.LikeBoolean;
import com.example.myapplication.Fragment.Menu4.HttpConnection.bringBoarddb;
//import com.example.myapplication.Fragment.Menu4.HttpConnection.bringLikeServer;
import com.example.myapplication.SaveSharedPreference;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class BringData{
    String boardname;
    String add = "add";
    Context context;
    String userid;
    public BringData(String boardname ,Context context) {
        this.boardname = boardname;
        this.context = context;
        userid = SaveSharedPreference.getNowUserName(context);
    }

    ArrayList<ItemData> items = new ArrayList<>();

    public ArrayList<ItemData> getItems(){
        try {

            bringBoarddb task = new bringBoarddb();

            String result = task.execute(boardname,userid).get();
            JSONObject jsonObject = new JSONObject(result);
            JSONArray jsonArray = jsonObject.getJSONArray("boardData");

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
