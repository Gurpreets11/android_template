package com.preet.androidtemplate.utils;


import android.content.Context;

public class GlobalExceptionHandler implements Thread.UncaughtExceptionHandler {

    private Context context;

    public GlobalExceptionHandler(Context context) {
        this.context = context;
    }

    @Override
    public void uncaughtException(Thread thread, Throwable throwable) {

        AppLogger.e("CRASH: " + throwable.getMessage());

        // You can later integrate Crashlytics here

        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(1);
    }
}

