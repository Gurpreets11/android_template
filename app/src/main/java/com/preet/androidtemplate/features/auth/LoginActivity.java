package com.preet.androidtemplate.features.auth;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;


 import com.preet.androidtemplate.MainActivity;
import com.preet.androidtemplate.MainApplication;
import com.preet.androidtemplate.R;
import com.preet.androidtemplate.base.BaseActivity;
import com.preet.androidtemplate.core.storage.PrefManager;
import com.preet.androidtemplate.core.utils.AppToast;
import com.preet.androidtemplate.core.utils.ValidationUtils;
import com.preet.androidtemplate.core.widgets.SwipeButtonView;
import com.preet.androidtemplate.factory.ViewModelFactory;
import com.preet.androidtemplate.viewmodel.AuthViewModel;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
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

        /*findViewById(R.id.tvSignup).setOnClickListener(v ->
                startActivity(new Intent(this, SignupActivity.class)));
*/
        progressBar = findViewById(R.id.progressBar);

        // 1️⃣ Create repository
        AuthRepository repository = ((MainApplication) getApplication()).getAuthRepository();
        // 2️⃣ Create ViewModel with factory
        viewModel = new ViewModelProvider(this, new ViewModelFactory(repository)).get(AuthViewModel.class);

        viewModel.getLoading().observe(this, isLoading ->
                progressBar.setVisibility(isLoading ? View.VISIBLE : View.INVISIBLE)
        );

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
            resetSwipe();
            return;
        }

        viewModel.login(email, password).observe(this, response -> {
            //if (response != null && response.isSuccess()) {
            if (response != null && response.isSuccess()
                    && response.getData() != null
                    && !response.getData().isEmpty()) {
                swipeLogin.showSuccess();
                AppToast.getInstance().show(this, "Login Success");
                String token = response.getData().get(0).getToken();
                PrefManager.getInstance(this).saveToken(token);

                startActivity(new Intent(this, MainActivity.class));
                finish();

            } else {
                resetSwipe();
                AppToast.getInstance().show(this, "Login Failed");
            }
        });
    }

    public void resetSwipe() {
        swipeLogin.resetSwipe();
        swipeLogin.setSwipeButtonLabel("Swipe to Login");
    }
}