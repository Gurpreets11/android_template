package com.preet.androidtemplate.viewmodel;

public class SplashViewModel {

    public void startSplash(Runnable navigate) {
        new android.os.Handler().postDelayed(navigate, 2000);
    }
}

