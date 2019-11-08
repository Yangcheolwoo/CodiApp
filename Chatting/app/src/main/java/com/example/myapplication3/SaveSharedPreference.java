package com.example.myapplication3;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SaveSharedPreference {
    static final String AUTO_USER_NAME= "username";
    static final String NOW_USER_NAME= "nowusername";
    static final String USER_PROFILE_IMAGE = "userprofileimage";
    static final String USER_INTRO = "userintro";
    static final String CHATROOM_KEY = "chatroom_key";

    static SharedPreferences getSharedPreferences(Context ctx) {
        return PreferenceManager.getDefaultSharedPreferences(ctx);
    }

    public static void setAutoUserName(Context ctx, String userName) { //자동로그인
        SharedPreferences.Editor AUTOeditor = getSharedPreferences(ctx).edit();
        AUTOeditor.putString(AUTO_USER_NAME, userName);
        AUTOeditor.commit();
    }

    public static void setNowUserName(Context ctx, String nowuserName){ //일반로그인
        SharedPreferences.Editor NOWeditor = getSharedPreferences(ctx).edit();
        NOWeditor.putString(NOW_USER_NAME, nowuserName);
        NOWeditor.commit();
    }

    public static void setUserProFileImage(Context ctx, String userprofileimage){ //일반로그인
        SharedPreferences.Editor NOWeditor = getSharedPreferences(ctx).edit();
        NOWeditor.putString(USER_PROFILE_IMAGE, userprofileimage);
        NOWeditor.commit();
    }

    public static void setUserIntro(Context ctx, String userintro){ //일반로그인
        SharedPreferences.Editor NOWeditor = getSharedPreferences(ctx).edit();
        NOWeditor.putString(USER_INTRO, userintro);
        NOWeditor.commit();
    }

    public static void SetChatroomkey(Context ctx, String chatroom_key){
        SharedPreferences.Editor NOWeditor = getSharedPreferences(ctx).edit();
        NOWeditor.putString(CHATROOM_KEY, chatroom_key);
        NOWeditor.commit();
    }

    public static String getAutoUserName(Context ctx) {
        return getSharedPreferences(ctx).getString(AUTO_USER_NAME, "");
    }
    public static String getNowUserName(Context ctx){
        return getSharedPreferences(ctx).getString(NOW_USER_NAME,"");
    }

    public static String getUserProFileImage(Context ctx){
        return getSharedPreferences(ctx).getString(USER_PROFILE_IMAGE,"");
    }

    public static String getUserIntro(Context ctx){
        return getSharedPreferences(ctx).getString(USER_INTRO,"");
    }

    public static String getChatroomKey(Context ctx){
        return getSharedPreferences(ctx).getString(CHATROOM_KEY, "");
    }

    public static void clearUserName(Context ctx){
//        SharedPreferences Auto = PreferenceManager.getDefaultSharedPreferences(ctx);
//        SharedPreferences Now = PreferenceManager.getDefaultSharedPreferences(ctx);
        SharedPreferences.Editor AUTOeditor = getSharedPreferences(ctx).edit();
        SharedPreferences.Editor NOWeditor = getSharedPreferences(ctx).edit();
        AUTOeditor.clear();
        NOWeditor.clear();
        AUTOeditor.commit();
        NOWeditor.commit();
    }
}