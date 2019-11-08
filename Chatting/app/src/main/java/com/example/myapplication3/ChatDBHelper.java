package com.example.myapplication3;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ChatDBHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;

    public ChatDBHelper(Context context){
        super(context, "chatroomName", null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        String chat = "create table chatroomname_(" +
                "chatroomId integer not null primary key autoincrement, " +
                "chatroomname text not null)";
        db.execSQL(chat);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        if(newVersion == DATABASE_VERSION){
            db.execSQL("drop table chatroomname_table");
            onCreate(db);
        }
    }
}
