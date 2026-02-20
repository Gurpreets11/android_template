package com.preet.androidtemplate;

import android.app.Application;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import androidx.lifecycle.ViewModel;

import com.preet.androidtemplate.core.network.ApiClient;
import com.preet.androidtemplate.core.network.ApiService;
import com.preet.androidtemplate.utils.GlobalExceptionHandler;
import com.preet.androidtemplate.factory.AppViewModelFactory;
import com.preet.androidtemplate.repository.AuthRepository;
import com.preet.androidtemplate.viewmodel.AuthViewModel;

public class MainApplication extends Application {

    private AppViewModelFactory factory;

    @Override
    public void onCreate() {
        super.onCreate();
        ApiService apiService = ApiClient.getApiService(this);

        AuthRepository authRepository = new AuthRepository(apiService);
        Map<Class<? extends ViewModel>, Supplier<? extends ViewModel>> creators = new HashMap<>();
        creators.put(AuthViewModel.class, () -> new AuthViewModel(authRepository));

//        creators.put(ProfileViewModel.class, () -> new ProfileViewModel(profileRepository));
//        creators.put(HomeViewModel.class, () -> new HomeViewModel(homeRepository));

        factory = new AppViewModelFactory(creators);

        Thread.setDefaultUncaughtExceptionHandler(new GlobalExceptionHandler(this));
    }

    public AppViewModelFactory getViewModelFactory() {
        return factory;
    }

}
