package com.example.myapplication.Fragment.Menu4;

import android.content.Context;
import android.widget.Toast;

import com.example.myapplication.Fragment.Menu4.HttpConnection.LoadCommentToDB;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class BringComent {
    String boardid;
    String boardname;
    Context context;

    public BringComent(String boardid, String boardname,Context context){
        this.boardid = boardid;
        this.boardname = boardname;
        this.context = context;
    }

    ArrayList<ComentData> items = new ArrayList<>();

    public ArrayList<ComentData> getItems(){
        try{
            LoadCommentToDB task = new LoadCommentToDB();

            String result = task.execute(boardid,boardname).get();
            JSONObject jsonObject = new JSONObject(result);
            JSONArray jsonArray = jsonObject.getJSONArray("ComentData");

            for(int i=0 ; i<jsonArray.length(); i++){
                jsonObject = jsonArray.getJSONObject(i);

                ComentData item = new ComentData();
                item.userid = jsonObject.getString("userid");
                item.time = jsonObject.getString("time");
                item.comm = jsonObject.getString("content");
                item.userprofileimage = jsonObject.getString("profileimage");
                items.add(item);
            }
        }
        catch(Exception e){

        }
        finally {
            //Toast.makeText(context,""+items.get(0).toString(),Toast.LENGTH_LONG).show();
            return items;
        }

    }
}
