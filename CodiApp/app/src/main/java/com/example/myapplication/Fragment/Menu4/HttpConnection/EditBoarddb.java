package com.example.myapplication.Fragment.Menu4.HttpConnection;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class EditBoarddb extends AsyncTask<String, Void, Void> {
    //String uri="http://coditest.iptime.org:80/Codi/EditBoardDB.jsp";
    private String uri = "http://220.68.233.35:80/Codi/EditBoardDB.jsp";
    String sendMsg, receiveMsg;
    @Override
    protected Void doInBackground(String... strings) {


        try {
            String str;
            URL url = new URL(uri);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Accept-Charset", "utf-8");
            conn.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
            conn.setRequestMethod("POST");
            OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream());

            sendMsg = "boardname=" + strings[0] + "&userid=" + strings[1] + "&imgpath=" + strings[2] + "&boardid=" + strings[3]; // + strings[4] + "&content=" +strings[5] + "&time=" + strings[6];

            osw.write(sendMsg);
            osw.flush();

            if(conn.getResponseCode() == conn.HTTP_OK){
                InputStreamReader tmp = new InputStreamReader(conn.getInputStream(),"UTF-8");
                BufferedReader reader = new BufferedReader(tmp);
                StringBuffer buffer = new StringBuffer();


                while((str = reader.readLine()) != null) {
                    buffer.append(str);
                }
                receiveMsg = buffer.toString();
            }else {

            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }
}
