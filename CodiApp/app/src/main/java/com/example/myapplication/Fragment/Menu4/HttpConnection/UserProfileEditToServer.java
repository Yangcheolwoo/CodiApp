package com.example.myapplication.Fragment.Menu4.HttpConnection;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import dmax.dialog.SpotsDialog;

public class UserProfileEditToServer extends AsyncTask<String,Void,String> {
    String lineEnd = "\r\n";
    String twoHyphens = "--";
    String boundary = "*****";
    String receiveMsg;
    Context context;
    Activity activity;
    SpotsDialog dialog;

    //private final String serveruri = "http://54.180.49.246:8080/receiveimage.jsp"; //aws ec2 서버주소
    //private final String serveruri = "http://192.168.0.5:11000/Codi/imagetest.jsp"; //테스팅 서버주소
    //private final String uri = "http://coditest.iptime.org:80/Codi/imagetest.jsp";

    private String uri = "http://220.68.233.35:80/Codi/EditProfile.jsp";
    public UserProfileEditToServer(Context context,Activity activity){
        this.context = context;
        this.activity = activity;
        // dialog = new SpotsDialog(context);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        dialog = new SpotsDialog(context);
        dialog.show();

    }

    @Override
    protected String doInBackground(String... Strings) {

        String fileName = Strings[0];
        String userid = Strings[1];
        String intro = Strings[2];

//        String imagename = Strings[5];
//        String date = Strings[6];

        try {
            FileInputStream mFileInputStream = new FileInputStream(fileName);
            URL connectUrl = new URL(uri);
            Log.d("Test", "mFileInputStream  is " + mFileInputStream);

            // HttpURLConnection 통신
            HttpURLConnection conn = (HttpURLConnection) connectUrl.openConnection();
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setUseCaches(false);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("Accept-Charset", "UTF-8");
            conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);

            // write data
            DataOutputStream dos = new DataOutputStream(conn.getOutputStream());
            dos.writeBytes("\r\n--" + boundary + "\r\n");
            dos.writeBytes("Content-Disposition: form-data; name=\"userid\"\r\n\r\n" + userid + lineEnd);
            dos.writeBytes("\r\n--" + boundary + "\r\n");
            dos.writeBytes("Content-Disposition: form-data; name=\"intro\"\r\n\r\n");
            dos.writeUTF(intro);
            dos.writeBytes(lineEnd);
            dos.writeBytes("\r\n--" + boundary + "\r\n");
            dos.writeBytes("Content-Disposition: form-data; name=\"image\";filename=\"" + fileName + "\"" + lineEnd);
            dos.writeBytes(lineEnd);

            //Writer out = new OutputStreamWriter(dos,"UTF8");

            int bytesAvailable = mFileInputStream.available();
            int maxBufferSize = 1024;
            int bufferSize = Math.min(bytesAvailable, maxBufferSize);


            byte[] buffer = new byte[bufferSize];
            int bytesRead = mFileInputStream.read(buffer, 0, bufferSize);

            Log.d("Test", "image byte is " + bytesRead);

            // read image
            while (bytesRead > 0) {

                dos.write(buffer, 0, bufferSize);
                bytesAvailable = mFileInputStream.available();
                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                bytesRead = mFileInputStream.read(buffer, 0, bufferSize);
            }

            dos.writeBytes(lineEnd);
            dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

            // close streams
            Log.e("Test", "File is written");
            mFileInputStream.close();
            dos.flush();
            // finish upload...

            // get response
            InputStream is = conn.getInputStream();

            StringBuffer b = new StringBuffer();
            for (int ch = 0; (ch = is.read()) != -1; ) {
                b.append((char) ch);
            }
            receiveMsg = b.toString();

            is.close();
            Log.e("Test", b.toString());

        } catch (Exception e) {
            e.printStackTrace();
            Log.d("Test", "exception " + e.getMessage());
            // TODO: handle exception
        }



        return receiveMsg;
    }

    @Override
    protected void onPostExecute(String aVoid) {
        super.onPostExecute(aVoid);

        dialog.dismiss();
        activity.finish();


    }
}
