package com.example.myapplication.Fragment.Menu4.HttpConnection;

import android.os.AsyncTask;
import android.util.Log;

import com.example.myapplication.ServerUri;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class LoadCommentToDB extends AsyncTask<String, Void, String> {
    String sendMsg, receiveMsg;

    private String uri = new ServerUri().uri+"ComentLoadDB.jsp";

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

            sendMsg ="boardid=" + strings[0] + "&boardname=" + strings[1];

            osw.write(sendMsg);
            osw.flush();

            if(conn.getResponseCode() == conn.HTTP_OK){
                InputStreamReader tmp = new InputStreamReader(conn.getInputStream(),"UTF-8");
                BufferedReader reader = new BufferedReader(tmp);
                StringBuffer buffer = new StringBuffer();

                while((str = reader.readLine()) != null) {
                    buffer.append(str);
                }
                receiveMsg = buffer.toString();//buffer.toString();
            }else {

            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }
        return receiveMsg;
    }
    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        Log.e("RECVDATA",receiveMsg);
    }
}

