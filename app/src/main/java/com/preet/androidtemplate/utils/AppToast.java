package com.preet.androidtemplate.utils;


import android.content.Context;
import android.widget.Toast;

public class AppToast {

    private static AppToast instance;
    private Toast toast;

    // Private constructor (Singleton)
    private AppToast() {
    }

    // Get single instance
    public static synchronized AppToast getInstance() {
        if (instance == null) {
            instance = new AppToast();
        }
        return instance;
    }

    // Show toast
    public void show(Context context, String message) {
        show(context, message, Toast.LENGTH_SHORT);
    }

    // Show toast with duration
    public void show(Context context, String message, int duration) {
        if (toast != null) {
            toast.cancel();
        }
        toast = Toast.makeText(context.getApplicationContext(), message, duration);
        toast.show();
    }

    // Convenience methods
    public void success(Context context, String message) {
        show(context, message);
    }

    public void error(Context context, String message) {
        show(context, message);
    }

    public void info(Context context, String message) {
        show(context, message);
    }
}
