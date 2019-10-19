package com.example.myapplication.Fragment.Menu2;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.myapplication.Fragment.Menu4.ComentData;
import com.example.myapplication.Fragment.Menu4.HttpConnection.BringSearchUserItemFromServer;
import com.example.myapplication.SaveSharedPreference;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class BringSearchUserItem {

    Context context;
    String nowuserid;

    public BringSearchUserItem(Context context) {
        this.context = context;
        nowuserid = SaveSharedPreference.getNowUserName(context);
    }

    ArrayList<UserSearchItem> items = new ArrayList<>();

    public ArrayList<UserSearchItem> getItems() {
        try {
            BringSearchUserItemFromServer task = new BringSearchUserItemFromServer();

            String result = task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,"go").get();
            JSONObject jsonObject = new JSONObject(result);
            JSONArray jsonArray = jsonObject.getJSONArray("SearchUser");
            for (int i = 0; i < jsonArray.length(); i++) {
                jsonObject = jsonArray.getJSONObject(i);
                UserSearchItem item = new UserSearchItem();
                if(jsonObject.getString("id").equals(nowuserid)){

                }else {
                    item.userid = jsonObject.getString("id");
                    item.userimg = jsonObject.getString("profileimage");
                    items.add(item);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            return items;
        }
    }
}
