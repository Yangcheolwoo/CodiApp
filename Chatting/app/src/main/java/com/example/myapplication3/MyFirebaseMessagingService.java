package com.example.myapplication3;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.nio.channels.MembershipKey;

import androidx.core.app.NotificationCompat;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    String TAG = "mycodiappgood";
//
//    public MyFirebaseMessagingService() {
//    }

    // firebaseidservice에서 하던 토큰생성,갱신은 여기서

    private String title ="";
    private String body = "";

    // fcm으로 메시지를 받을 경우 수행된다
    // 클라이언트 앱은 onMessageReceived() 에서 데이터 메시지를 수신받고 키/값 쌍을 처리할 수 있다
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        //Log.d(TAG,"BACKGROUNDGetData"+remoteMessage.getData());
       //Log.d(TAG,"BACKGROUNDGetNotification"+remoteMessage.getNotification());

       // if (remoteMessage != null) {
            Log.d(TAG,"DATA"+remoteMessage.getData());
            title = remoteMessage.getData().get("title");
            body = remoteMessage.getData().get("body");
            Log.d(TAG,"title = " + remoteMessage.getData().get("title"));
            Log.d(TAG,"BODY = "+ remoteMessage.getData().get("body"));
            if(true){
                scheduleJob();
            }else {
                hanbdleNow();
            }
    //    }
//        if(remoteMessage.getNotification()!= null){
//            Log.d(TAG,"Notification"+remoteMessage.getNotification().getTitle());
//            //title = remoteMessage.getNotification().getTitle();
//            //body = remoteMessage.getNotification().getBody();
//        }
        sendNotification();
    }

    @Override
    public void onNewToken(String token) { // 토큰생성 or refresh
        super.onNewToken(token);
        Log.e("Firebase", "FirebaseInstanceIDService : " + token);

        //sendRegistrationToServer(token);

//        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(MainActivity.this, new OnSuccessListener<InstanceIdResult>() {
//            @Override
//            public void onSuccess(InstanceIdResult instanceIdResult) {
//                String newToken = instanceIdResult.getToken();
//                Log.e("New Token!", newToken);
//            }
//        });
    }


    private void scheduleJob(){
        FirebaseJobDispatcher dispatcher = new FirebaseJobDispatcher(new GooglePlayDriver(this));
        Job myJob = dispatcher.newJobBuilder()
                .setService(MyJobService.class)
                .setTag("my-job")
                .build();
        dispatcher.schedule(myJob);
    }


    private void hanbdleNow(){

    }
    // 알림작업

    private void sendNotification() {

        Intent intent = new Intent(this,MainActivity.class);
        intent.putExtra("title",title);
        intent.putExtra("body",body);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_ONE_SHOT);

        String channel_id = "채널id";
        String channel_nm = "채널명";

//        String title = remoteMessage.getData().get("title");
//        String message = remoteMessage.getData().get("message");

//        // NotificationCompat.Builder = 알림시 아이콘,메시지 제목,내용노출(RemoteMessage의 데이터), 알림소리, 알림 선택시 액비티비티 이동 등 설정
//        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, channel_id)
//                        .setSmallIcon(R.drawable.ic_launcher_background)
//                        .setContentTitle(title)
//                        .setContentText(message)
//                        .setChannelId(channel_id)
//                        .setAutoCancel(true)
//                        .setContentIntent(pendingIntent)
//                        .setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE);
//        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//        // NotificationManager에 등록
//        notificationManager.notify(9999, notificationBuilder.build());

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, channel_id)
                        .setSmallIcon(R.drawable.ic_launcher_background)
                        .setContentTitle(title)
                        .setContentText(body)
                        .setAutoCancel(true)
                        .setContentIntent(pendingIntent)
                        .setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);


        // 오레오(8.0) 이상일 경우 채널을 반드시 생성해야 한다.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            NotificationChannel channelMessage = new NotificationChannel(channel_id, channel_nm, android.app.NotificationManager.IMPORTANCE_DEFAULT);
            // 채널id     채널명        중요도
            channelMessage.setDescription("채널에 대한 설명.");
//            channelMessage.enableLights(true);
//            channelMessage.enableVibration(true);
//            channelMessage.setShowBadge(false);
//            channelMessage.setVibrationPattern(new long[]{100, 200, 100, 200});
            notificationManager.createNotificationChannel(channelMessage);
        }
        notificationManager.notify(0, notificationBuilder.build());

    }


}
