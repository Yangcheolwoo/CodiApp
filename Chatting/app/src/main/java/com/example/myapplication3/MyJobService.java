package com.example.myapplication3;

import android.util.Log;

import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;

public class MyJobService extends JobService {
    private static final String TAG = "MyJobService";

    @Override
    public boolean onStartJob(JobParameters job) {
        Log.d(TAG,"Start");
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters job) {
        Log.d(TAG,"STOP");
        return false;
    }

    //    @Override
//    public boolean onStartJob(JobParameters params) {
//        Log.d(TAG,"Start");
//        return false;
//    }
//
//    @Override
//    public boolean onStopJob(JobParameters params) {
//        Log.d(TAG,"STOP");
//        return false;
//    }
}
