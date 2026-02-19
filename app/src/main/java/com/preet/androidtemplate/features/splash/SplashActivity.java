package com.preet.androidtemplate.features.splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;


import com.preet.androidtemplate.R;
import com.preet.androidtemplate.core.storage.PrefManager;
import com.preet.androidtemplate.features.auth.LoginActivity;
import com.preet.androidtemplate.viewmodel.SplashViewModel;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SplashActivity extends AppCompatActivity {

    private SplashViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_splash);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

       /* viewModel = new SplashViewModel();
        viewModel.startSplash(() -> {


            PrefManager pref = new PrefManager(this);

            if (pref.getToken() != null) {
                startActivity(new Intent(this, LoginActivity.class));
            } else {
                startActivity(new Intent(this, LoginActivity.class));
            }
            finish();

        });*/


        ImageView logo = findViewById(R.id.imgLogo);

        logo.animate()
                .alpha(1f)
                .scaleX(1f)
                .scaleY(1f)
                .setDuration(1800)
                .setInterpolator(new OvershootInterpolator())
                .withEndAction(() -> {
                    new Handler(Looper.getMainLooper()).postDelayed(() -> {
                        //startActivity(new Intent(this, LoginActivity.class));

                        PrefManager pref = PrefManager.getInstance(this);

                        if (pref.getToken() != null) {
                            startActivity(new Intent(this, LoginActivity.class));
                        } else {
                            startActivity(new Intent(this, LoginActivity.class));
                        }

                        overridePendingTransition(
                                R.anim.slide_in_right,
                                R.anim.fade_out
                        );
                        finish();
                    }, 600);
                })
                .start();
    }
}