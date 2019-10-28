package com.example.myapplication.Fragment.Menu4.HttpConnection;

import android.os.AsyncTask;

import com.example.myapplication.ServerUri;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class SendCommentToDB extends AsyncTask<String,Void,Void> {
    String content;
    private String uri = new ServerUri().uri+"ComentSaveDB.jsp";


    @Override
    protected Void doInBackground(String... strings) {

        String sendMsg;

        try {
            String str;

            URL url = new URL(uri);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Accept-Charset", "utf-8");
            conn.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
            conn.setRequestMethod("POST");
            OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream());

            try{
                content = new String(strings[2].getBytes("UTF-8"));
            }catch (Exception e){
                e.printStackTrace();
            }
//            try{
//                strings[2] = URLEncoder.encode(strings[2],"UTF-8");
//            } catch(Exception e) {
//                e.printStackTrace();
//            }

            sendMsg = "boardid=" + strings[0] + "&userid=" + strings[1] + "&content=" + content + "&boardname=" + strings[3];

            osw.write(sendMsg);
            osw.flush();

            if(conn.getResponseCode() == conn.HTTP_OK){
                InputStreamReader tmp = new InputStreamReader(conn.getInputStream(),"UTF-8");
                BufferedReader reader = new BufferedReader(tmp);
                StringBuffer buffer = new StringBuffer();


                while((str = reader.readLine()) != null) {
                    buffer.append(str);
                }
                buffer.toString();
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
