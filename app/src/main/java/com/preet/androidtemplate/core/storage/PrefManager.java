package com.preet.androidtemplate.core.storage;

import android.content.Context;
import android.content.SharedPreferences;

public class PrefManager {
    private static PrefManager instance;

    private PrefManager() {
    }

    private static final String PREF_NAME = "health_tracker_pref";

    private SharedPreferences prefs;

    public static synchronized PrefManager getInstance(Context context) {
        if (instance == null) {
            instance = new PrefManager(context);
        }
        return instance;
    }

    private PrefManager(Context context) {
        prefs = context.getApplicationContext().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public void saveString(String key, String value) {
        prefs.edit().putString(key, value).apply();
    }

    public String getString(String key) {
        return prefs.getString(key, null);
    }

    public void saveToken(String token) {
        saveString(PrefKeys.TOKEN, token);
    }

    public String getToken() {
        return prefs.getString(PrefKeys.TOKEN, null);
    }

    /*public void saveToken(String token) {
        prefs.edit().putString(KEY_TOKEN, token).apply();
    }

    public String getToken() {
        return prefs.getString(KEY_TOKEN, null);
    }

    public void saveUserId(String token) {
        prefs.edit().putString(KEY_USERID, token).apply();
    }

    public String getUserId() {
        return prefs.getString(KEY_USERID, null);
    }*/

    public void clear() {
        prefs.edit().clear().apply();
    }
}

