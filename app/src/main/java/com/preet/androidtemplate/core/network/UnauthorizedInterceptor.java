package com.preet.androidtemplate.core.network;

import android.content.Context;
import android.content.Intent;


import com.preet.androidtemplate.core.storage.PrefManager;
import com.preet.androidtemplate.features.auth.LoginActivity;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

public class UnauthorizedInterceptor implements Interceptor {

    private final Context context;

    public UnauthorizedInterceptor(Context context) {
        this.context = context.getApplicationContext();
    }
    private static boolean isLogoutTriggered = false;

    @Override
    public Response intercept(Chain chain) throws IOException {

        Response response = chain.proceed(chain.request());

        if (response.code() == 401 && !isLogoutTriggered) {
            isLogoutTriggered = true;
            // Clear token
             PrefManager.getInstance(context).clear();

            // Redirect to Login
            Intent intent = new Intent(context, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            context.startActivity(intent);
        }

        return response;
    }
}

