package com.preet.androidtemplate.utils;


import android.content.Context;

import androidx.appcompat.app.AlertDialog;

public class AppAlertDialog {

    public static void showMessage(
            Context context,
            String title,
            String message
    ) {

        new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", null)
                .setCancelable(false)
                .show();
    }
}

