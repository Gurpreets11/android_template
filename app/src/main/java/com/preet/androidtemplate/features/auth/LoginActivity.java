package com.preet.androidtemplate.features.auth;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.preet.androidtemplate.MainActivity;
import com.preet.androidtemplate.MainApplication;
import com.preet.androidtemplate.R;
import com.preet.androidtemplate.base.BaseActivity;
import com.preet.androidtemplate.core.model.LoginData;
import com.preet.androidtemplate.core.storage.PrefManager;
import com.preet.androidtemplate.core.utils.AppToast;
import com.preet.androidtemplate.core.utils.ValidationUtils;
import com.preet.androidtemplate.core.widgets.SwipeButtonView;
import com.preet.androidtemplate.factory.AppViewModelFactory;
import com.preet.androidtemplate.viewmodel.AuthViewModel;

import java.util.List;

import androidx.activity.EdgeToEdge;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;


public class LoginActivity extends BaseActivity {
    private EditText etEmail, etPassword;
    private AuthViewModel viewModel;
    private ProgressBar progressBar;
    private SwipeButtonView swipeLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);

        etEmail.setText("gurpreet33@gmail.com");
        etPassword.setText("123456");

        findViewById(R.id.tvSignup).setOnClickListener(v ->
                startActivity(new Intent(this, SignupActivity.class)));

        progressBar = findViewById(R.id.progressBar);

        AppViewModelFactory factory = ((MainApplication) getApplication()).getViewModelFactory();
        viewModel = new ViewModelProvider(this, factory).get(AuthViewModel.class);

        //findViewById(R.id.btnLogin).setOnClickListener(v -> onClickedLogin());
        swipeLogin = findViewById(R.id.swipeLogin);
        swipeLogin.setSwipeButtonLabel("Swipe to Login");
        swipeLogin.setOnSwipeCompleteListener(this::onClickedLogin);
    }

    private void onClickedLogin() {
        String email = etEmail.getText().toString();
        String password = etPassword.getText().toString();

        if (!ValidationUtils.isEmailValid(email) ||
                !ValidationUtils.isPasswordValid(password)) {
            AppToast.getInstance().show(this, "Invalid credentials");
//            AppAlertDialog.showMessage(this, "Error", "Invalid credentials");
            resetSwipe();
            return;
        }

        viewModel.login(email, password).observe(this, result -> {
            if (result == null) {
                resetSwipe();
                return;
            }
            switch (result.status) {
                case LOADING:
                    // If using swipe loader animation
                    swipeLogin.showSuccess();
                    break;

                case SUCCESS:
                    List<LoginData> users = result.data.getData();
                        swipeLogin.showSuccess();
                        AppToast.getInstance().show(this, "Login Success");
                        String token = users.get(0).getToken();
                        PrefManager.getInstance(this).saveToken(token);
                        startActivity(new Intent(this, MainActivity.class));
                        finish();
                    break;
                case ERROR:
                    resetSwipe();
                    AppToast.getInstance().show(this, result.message);
                    break;
            }
        });
    }

    public void resetSwipe() {
        swipeLogin.resetSwipe();
        swipeLogin.setSwipeButtonLabel("Swipe to Login");
    }
}