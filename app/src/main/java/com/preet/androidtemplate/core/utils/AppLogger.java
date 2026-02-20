package com.preet.androidtemplate.core.utils;


import android.util.Log;


public class AppLogger {

    private static final String GLOBAL_TAG = "PREET_APP";

    public static void d(String message) {
        /*if (BuildConfig.DEBUG) {
            Log.d(GLOBAL_TAG, message);
        }*/
        Log.d(GLOBAL_TAG, message);
    }

    public static void e(String message) {
        /*if (BuildConfig.DEBUG) {
            Log.e(GLOBAL_TAG, message);
        }*/
        Log.e(GLOBAL_TAG, message);
    }

    public static void api(String message) {
        /*if (BuildConfig.DEBUG) {
            Log.i("API_LOG", message);
        }*/
        Log.i("API_LOG", message);
    }
}

