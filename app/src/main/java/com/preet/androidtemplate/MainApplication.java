package com.preet.androidtemplate;

import android.app.Application;

import com.preet.androidtemplate.core.network.ApiClient;
import com.preet.androidtemplate.core.network.ApiService;
import com.preet.androidtemplate.features.auth.AuthRepository;


public class MainApplication extends Application {

    private AuthRepository authRepository;
    //private GarminRepository garminRepository;

    @Override
    public void onCreate() {
        super.onCreate();
        ApiService apiService = ApiClient.getApiService(this);
        authRepository = new AuthRepository(apiService);
        //garminRepository= new GarminRepository(apiService);
    }

    public AuthRepository getAuthRepository() {
        return authRepository;
    }

    /*public GarminRepository getGarminRepository() {
        return garminRepository;
    }*/
}
