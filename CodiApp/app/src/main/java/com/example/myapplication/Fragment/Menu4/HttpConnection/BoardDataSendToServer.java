package com.example.myapplication.Fragment.Menu4.HttpConnection;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ProgressBar;

import com.example.myapplication.CustomDialog;
import com.example.myapplication.Fragment.Menu4.AddContext;
import com.example.myapplication.ServerUri;

import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import dmax.dialog.SpotsDialog;

public class BoardDataSendToServer extends AsyncTask<String, Void, String> {
    String lineEnd = "\r\n";
    String twoHyphens = "--";
    String boundary = "*****";
    String receiveMsg;
    Context context;
    SpotsDialog dialog;
    Activity activity;
    CustomDialog customDialog;

    private String uri = new ServerUri().uri+"SaveBoardData.jsp";

    public BoardDataSendToServer(Context context,Activity activity){
        this.context = context;
        this.activity = activity;
        customDialog = new CustomDialog(context);
       // dialog = new SpotsDialog(context);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
//        dialog = new SpotsDialog(context);
//        dialog.show();
        customDialog.show();

    }

    @Override
    protected String doInBackground(String... Strings) {

        String fileName = Strings[0];
        String userName = Strings[1];
        String title = Strings[2];
        String content = Strings[3];
        String boardname = Strings[4];

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
            dos.writeBytes("Content-Disposition: form-data; name=\"username\"\r\n\r\n" + userName + lineEnd);
            dos.writeBytes("\r\n--" + boundary + "\r\n");
            dos.writeBytes("Content-Disposition: form-data; name=\"dbname\"\r\n\r\n" + boardname + lineEnd);
            dos.writeBytes("\r\n--" + boundary + "\r\n");
            dos.writeBytes("Content-Disposition: form-data; name=\"title\"\r\n\r\n");
            dos.writeUTF(title);
            dos.writeBytes(lineEnd);
            dos.writeBytes("\r\n--" + boundary + "\r\n");
            dos.writeBytes("Content-Disposition: form-data; name=\"content\"\r\n\r\n");
            dos.writeUTF(content);
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
            Log.e("title",""+title);
            Log.e("content",""+content);
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
        customDialog.dismiss();
        //dialog.dismiss();
        activity.finish();
    }
}
