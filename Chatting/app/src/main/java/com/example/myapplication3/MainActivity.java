package com.example.myapplication3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;

//
public class MainActivity extends AppCompatActivity /* implements View.OnClickListener */{
    FirebaseDatabase mFirebaseDatabase; // db 선언
    DatabaseReference mDatabaseReference;
    ChildEventListener mChildEventListener;
    ChatAdapter mAdapter;
    RecyclerView mRecyclerView;
    EditText mEditMsg, et_send, et_receive;
    Button message_send, log,btn4;
    String userName, token, sender, receiver, chatroom_key, key1;
    //static final String FCM_MESSAGE_URL = "https://fcm.googleapis.com/fcm/send";
    //static final String SERVER_KEY = "AAAADifFQ8c:APA91bE01SvhR8AcNpnU3-JbpKNyWxNf1nBuqtoEsALFO5cb4QoYUKIzcT_Urkp3SUI6oyKyCaghi0LPwDLG-p8wK2xeksCU_WD5BCzFws20Jnl1we154RA1dyEjMvSaarYgqkNwx5_c";

    ChatDBHelper chatDBHelper;
    SQLiteDatabase db;
    ArrayList<ChatData> chatlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn4 = (Button) findViewById(R.id.button4);

        mRecyclerView = (RecyclerView)findViewById(R.id.listview);
        mAdapter = new ChatAdapter();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        //mRecyclerView.setAdapter(mAdapter);
        mEditMsg = (EditText) findViewById(R.id.et);
        //findViewById(R.id.button).setOnClickListener(this);
        et_send = (EditText)findViewById(R.id.et_send);
        et_receive = (EditText)findViewById(R.id.et_receive);
        message_send = (Button)findViewById(R.id.button);
        message_send.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String message = mEditMsg.getText().toString();
                if (!TextUtils.isEmpty(message)) {
                    mEditMsg.setText("");
                    ChatData chatData = new ChatData();
                    chatData.senderName = sender;
                    chatData.receiverName = receiver;
                    chatData.message = message;
                    chatData.time = System.currentTimeMillis();
                    chatData.token = token;
                    mDatabaseReference.push().setValue(chatData);
                    // sendPostToFCM(chatData, "testString");

                    db = chatDBHelper.getWritableDatabase();
                    //db.execSQL("INSERT INTO '" + key1 + "' (sender_id, receiver_id, message, time) values(?,?,?,?)", new String[] {""+chatData.senderName, ""+chatData.receiverName, ""+chatData.message, ""+chatData.time});
                    db.close();


                }
            }
        });

//        Intent it = getIntent();
//        String title = it.getStringExtra("title");
//        String body = it.getStringExtra("body");
//
//        et_send.setText(title);
//        et_receive.setText(body);

        //-------------------토큰 생성
        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(MainActivity.this, new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult) {
                String newToken = instanceIdResult.getToken();
                Toast.makeText(getApplicationContext(), newToken, Toast.LENGTH_SHORT).show();
                Log.d("Token",newToken);
            }
        });

        FirebaseMessaging.getInstance();
//            @Override
//            public void onComplete(@NonNull Task<InstanceIdResult> task) {
//                if(!task.isSuccessful()){
//                    Log.w("Firebase", "getinstanceid failed", task.getException());
//                    return;
//                }
//
//                token = task.getResult().getToken();
//                SharedPreferences tk = getSharedPreferences("tk_file", MODE_PRIVATE);
//                SharedPreferences.Editor editor = tk.edit();
//                editor.putString("token", token);
//                editor.commit();
//
//                Log.e("token", token);
//            }
//        });
        //--------------------------------------------------

        //채팅버튼 클릭시-------------------------------------------------------
        log = (Button) findViewById(R.id.button2);
        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //------------채팅룸 키 생성 -> 두 id를 사전순으로 정렬해 키로 만든다 -> 송수신자가 달라도 키는 정렬된거 하나로
                sender = et_send.getText().toString();
                receiver = et_receive.getText().toString();

                if(sender.compareTo(receiver) < 0){
                    chatroom_key = "chatroom" + "*" + sender + "*" + receiver;
                }
                else if(sender.compareTo(receiver) > 0){
                    chatroom_key = "chatroom" + "*" + receiver + "*" + sender;
                }

                chatDBHelper = new ChatDBHelper(getApplicationContext());
                db = chatDBHelper.getWritableDatabase();
                key1 = chatroom_key;
                String sql1 = " CREATE TABLE IF NOT EXISTS '" + chatroom_key + "'(" +
                        "sender_id text not null," +
                        "receiver_id text not null," +
                        "message text not null," +
                        "time text not null) ";
                db.execSQL(sql1);
                db.close();

                initFirebaseDatabase();

                db = chatDBHelper.getWritableDatabase();
                //★
                Cursor cursor = db.rawQuery("select * from '" + chatroom_key +"' ", null);
                chatlist = new ArrayList<>();
                ChatData cd = new ChatData();
                while(cursor.moveToNext())
                {
                    cd.senderName = cursor.getString(0);
                    cd.receiverName = cursor.getString(1);
                    cd.message = cursor.getString(2);
                    cd.time = cursor.getLong(3);
                    chatlist.add(cd);
                }
                db.close();
                mAdapter.SetItems(chatlist);
                mRecyclerView.setAdapter(mAdapter);


                //-------------------------------------------------------------------------
//                Cursor cursor = db.rawQuery("select * from '" + key1 +"' order by time", null);
//                chatlist = new ArrayList<>();
//                ChatData cd = new ChatData();
//                while(cursor.moveToNext())
//                {
//                    cd.senderName = cursor.getString(0);
//                    cd.receiverName = cursor.getString(1);
//                    cd.message = cursor.getString(2);
//                    cd.time = cursor.getLong(3);
//                    chatlist.add(cd);
//                }
//                mAdapter.SetItems(chatlist);
//                mRecyclerView.setAdapter(mAdapter);

            }
        });
        //-----------------------------------------------


    }

    public void initFirebaseDatabase(){
//        chatDBHelper = new ChatDBHelper(this);
//        db = chatDBHelper.getWritableDatabase();

        SaveSharedPreference.SetChatroomkey(getApplicationContext(), chatroom_key);

        mFirebaseDatabase = FirebaseDatabase.getInstance(); // 할당
        mDatabaseReference = mFirebaseDatabase.getReference(chatroom_key); // 참조해서 가져온다(chatroom_key는 채팅방을 구분해주기 위해)
        // 채팅방 구분시 ()안을 변경 -> ex)A와 B의 채팅방의 키값이 AB다 -> 괄호안을 AB로

//        key1 = chatroom_key;
//            String sql1 = " CREATE TABLE IF NOT EXISTS '" + chatroom_key + "'(" +
//                    "sender_id text not null," +
//                    "receiver_id text not null," +
//                    "message text not null," +
//                    "time text not null) ";
//            db.execSQL(sql1);

        // ChildEventListener()는 db의 child의 변화에 대해서 반응
        mChildEventListener = new ChildEventListener() {
            // 리스트의 아이템을 검색/추가가 있을시 수신
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) { // 데이터스냅샷(firebase db에서 들어간 데이터)
                ChatData chatData = dataSnapshot.getValue(ChatData.class);
                chatData.firebaseKey = dataSnapshot.getKey();
                // Cursor cursor = db.rawQuery(" select * from '" + chatroom_key + "' ", null);

                // rawQuery(sql문, sql에 대입할 매개변수)
                //sql문에 데이터 부분(values)을 ?로 작성했다면, 두 번째 매개변수에서 각각의 ?에 대응하는 데이터를 지정

//                void execSQL(String sql)
//                SELECT 명령을 제외한 모든 SQL 문장을 실행한다.

//                Cursor rawQuery(String sql, String[] selectionArgs)
//                SELECT 명령어를 사용하여 쿼리를 실행하려면 rawQuery()를 사용하면 된다.
//                        쿼리의 결과는 Cursor 객체로 반환된다.
//                Cursor 객체는 쿼리에 의하여 생성된 행들을 가리킨다.

                Log.e("chatData.message : ", chatData.message);
                Log.e("chatData.senderName : ", chatData.senderName);
                Log.e("chatData.receiverNa : ", chatData.receiverName);
                Log.e("chatData.time : ", ""+chatData.time);
                Log.e("chatData.firebaseKey : ", chatData.firebaseKey);
               // mListView.smoothScrollToPosition(mAdapter.getCount());
                //sendPostToFCM(chatData, "testString");

                // 내장db에 데이터 넣기------------------------------------------
                db = chatDBHelper.getWritableDatabase();
                db.execSQL("INSERT INTO '" + key1 + "' (sender_id, receiver_id, message, time) values(?,?,?,?)", new String[] {""+chatData.senderName, ""+chatData.receiverName, ""+chatData.message, ""+chatData.time});
                db.close();

                //---------------전송시마다 채팅말풍선 보이기
                ChatData chatdata2 = new ChatData();
                chatdata2.message = chatData.message;
                chatdata2.senderName = chatData.senderName;
                chatdata2.receiverName = chatData.receiverName;
                chatdata2.time = chatData.time;
                mAdapter.addChatData(chatdata2);
                //------------------------------

//                Cursor cursor = db.rawQuery("select * from '" + key1 +"' order by time", null);
//                chatlist = new ArrayList<>();
//                ChatData cd = new ChatData();
//                while(cursor.moveToNext())
//                {
//                    cd.senderName = cursor.getString(0);
//                    cd.receiverName = cursor.getString(1);
//                    cd.message = cursor.getString(2);
//                    cd.time = cursor.getLong(3);
//                    chatlist.add(cd);
//                }

                //mAdapter.addChatData(cd);
                //mAdapter.notifyDataSetChanged();

            }

            // 리스트의 아이템의 변화가 있을때 수신
            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) { }
            // 리스트의 아이템이 삭제되었을때 수신
            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
//                String firebaseKey = dataSnapshot.getKey();
//                int count = mAdapter.getItemCount();
//                for (int i = 0; i < count; i++) {
//                    if (mAdapter.getItem(i).firebaseKey.equals(firebaseKey)) {
//                        mAdapter.remove(mAdapter.getItem(i));
//                        break;
//                    }
//                }
            }
            // 리스트의 순서가 변경되었을때 수신
            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };
        mDatabaseReference.addChildEventListener(mChildEventListener);
        //mRecyclerView.setAdapter(mAdapter);

    }

    //---send 버튼 눌릴시 -> 채팅데이터 타입에 데이터 주기
   // @Override
   // public void onClick(View v) {
//        //initFirebaseDatabase();
//        String message = mEditMsg.getText().toString();
//        if (!TextUtils.isEmpty(message)) {
//            mEditMsg.setText("");
//            ChatData chatData = new ChatData();
//            chatData.senderName = sender;
//            chatData.receiverName = receiver;
//            chatData.message = message;
//            chatData.time = System.currentTimeMillis();
//            chatData.token = token;
//            mDatabaseReference.push().setValue(chatData);
//           // sendPostToFCM(chatData, "testString");
   //     }

}

    //--------------------------

//    public void sendPostToFCM(final ChatData chatData, final String message) {
//        mFirebaseDatabase.getReference(chatroom_key)
//                //.child("-LjFVgZ2qQyBR-Y-1Nv-")
//                .addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(DataSnapshot dataSnapshot) {
//                        final ChatData chatData = dataSnapshot.getValue(ChatData.class);
//                        new Thread(new Runnable() {
//                            @Override
//                            public void run() {
//                                try {
//                                    // FMC 메시지 생성 start
//                                    JSONObject root = new JSONObject();
//                                    JSONObject notification = new JSONObject();
//                                    notification.put("body", "test1");
//                                    notification.put("title", "getString(R.string.app_name)");
//                                    root.put("notification", notification);
//                                    root.put("to", chatData.token);
//                                    // FMC 메시지 생성 end
//
//                                    URL Url = new URL(FCM_MESSAGE_URL); // URL클래스의 객체 생성 -> 주소를 절대경로로
//                                    HttpURLConnection conn = (HttpURLConnection) Url.openConnection(); // url내용을 읽거나 데이터 전달시 사용
//                                    // URL 클래스의 openConnection()을 호출하게 되면, URL 클래스가 사용하는 프로토콜에 따라 URLConnection을 상속한 알맞은 하위 클래스의 인스턴스를 얻게 된다
//                                    conn.setRequestMethod("POST"); // POST방식
//
//                                    conn.setDoOutput(true); //POST 데이터를 OutputStream으로 넘겨 주겠다는 설정
//                                    conn.setDoInput(true);
//                                    conn.addRequestProperty("Authorization", "key=" + SERVER_KEY);
//                                    conn.setRequestProperty("Accept", "application/json");
//                                    conn.setRequestProperty("Content-type", "application/json");
//                                    OutputStream os = conn.getOutputStream();
//                                    os.write(root.toString().getBytes("utf-8")); //json 형식의 message 전달
//                                    os.flush(); // 버퍼가 아직 가득 차지 않은 상황에서 강제로 버퍼의 내용을 전송
//                                    conn.getResponseCode();
//                                } catch (Exception e) {
//                                    e.printStackTrace();
//                                }
//                            }
//                        }).start(); // 쓰레드 내의 run()을 수행
//                    }
//
//                    @Override
//                    public void onCancelled(DatabaseError databaseError) {
//
//                    }
//                });
//    }


//    public class MyFirebaseMessagingService extends FirebaseMessagingService {
//
////    public MyFirebaseMessagingService() {
////        Log.e("Firebase","create");
////    }
//
//        // firebaseidservice에서 하던 토큰생성,갱신은 여기서
//        @Override
//        public void onNewToken(String token) { // 토큰생성 or refresh
//            super.onNewToken(token);
//            Log.e("Firebase", "FirebaseInstanceIDService : " + token);
//
//            //sendRegistrationToServer(token);
//
////            FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(MainActivity.this, new OnSuccessListener<InstanceIdResult>() {
////                @Override
////                public void onSuccess(InstanceIdResult instanceIdResult) {
////                    String newToken = instanceIdResult.getToken();
////                    Log.e("New Token!", newToken);
////                }
////            });
//        }
//
//
//        // fcm으로 메시지를 받을 경우 수행된다
//        // 클라이언트 앱은 onMessageReceived() 에서 데이터 메시지를 수신받고 키/값 쌍을 처리할 수 있다
//        @Override
//        public void onMessageReceived(RemoteMessage remoteMessage) {
//
//            if (remoteMessage != null && remoteMessage.getData().size() > 0) {
//                sendNotification(remoteMessage);
//            }
//        }
//
//        // 알림작업
//        private void sendNotification(RemoteMessage remoteMessage) {
//
//            String title = remoteMessage.getData().get("title");
//            String message = remoteMessage.getData().get("message");
//
//            // 오레오(8.0) 이상일 경우 채널을 반드시 생성해야 한다.
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
//            {
//                String channel_id = "채널id";
//                String channel_nm = "채널명";
//
//                NotificationManager notichannel = (android.app.NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//
//                NotificationChannel channelMessage = new NotificationChannel(channel_id, channel_nm, android.app.NotificationManager.IMPORTANCE_DEFAULT);
//                // 채널id     채널명        중요도
//                channelMessage.setDescription("채널에 대한 설명.");
//                channelMessage.enableLights(true);
//                channelMessage.enableVibration(true);
//                channelMessage.setShowBadge(false);
//                channelMessage.setVibrationPattern(new long[]{100, 200, 100, 200});
//                notichannel.createNotificationChannel(channelMessage);
//
//                // NotificationCompat.Builder = 알림시 아이콘,메시지 제목,내용노출(RemoteMessage의 데이터), 알림소리, 알림 선택시 액비티비티 이동 등 설정
//                NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, channel_id)
//                        .setSmallIcon(R.drawable.ic_launcher_background)
//                        .setContentTitle(title)
//                        .setContentText(message)
//                        .setChannelId(channel_id)
//                        .setAutoCancel(true)
//                        .setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE);
//                NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//                // NotificationManager에 등록
//                notificationManager.notify(9999, notificationBuilder.build());
//            }
//            else
//            {
//                NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, "")
//                        .setSmallIcon(R.drawable.ic_launcher_background)
//                        .setContentTitle(title)
//                        .setContentText(message)
//                        .setAutoCancel(true)
//                        .setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE);
//
//                NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//                notificationManager.notify(9999, notificationBuilder.build());
//            }
//        }
//
//    }
//

//}