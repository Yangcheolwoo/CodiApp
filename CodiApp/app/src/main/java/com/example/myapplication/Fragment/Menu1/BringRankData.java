package com.example.myapplication.Fragment.Menu1;

import android.content.Context;
import android.os.AsyncTask;

import com.example.myapplication.Fragment.Menu4.HttpConnection.BringRankDatadb;
import com.example.myapplication.SaveSharedPreference;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class BringRankData {
    String type;
    String userid;
    Context context;
    public BringRankData(String type,Context context) {
        this.type = type;
        this.context = context;
        userid = SaveSharedPreference.getNowUserName(context);
    }

    ArrayList<RankData> items = new ArrayList<>();

    public ArrayList<RankData> getItems(){
        try {

            BringRankDatadb task = new BringRankDatadb();
            String result = task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,type,userid).get();
            JSONObject jsonObject = new JSONObject(result);
            JSONArray jsonArray = jsonObject.getJSONArray("rankData");

            for(int i=0; i<jsonArray.length(); i++){
                jsonObject = jsonArray.getJSONObject(i);
                RankData item = new RankData();
                item.boardid = jsonObject.getString("id");
                item.id=jsonObject.getString("userid");
                item.title =jsonObject.getString("title");
                item.content = jsonObject.getString("content");
                item.time = jsonObject.getString("time");
                item.imgpath=jsonObject.getString("imagepath");
                item.boardname = jsonObject.getString("boardname");
                item.likecount = jsonObject.getString("likecount");
                item.toggle = jsonObject.getString("flag");
                items.add(item);
            }

        }catch (Exception e){

        }finally {
            return items;
        }
    }
}
