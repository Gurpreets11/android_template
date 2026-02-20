package com.preet.androidtemplate.features.auth;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioGroup;

import com.preet.androidtemplate.MainApplication;
import com.preet.androidtemplate.R;
import com.preet.androidtemplate.core.model.SignupData;
import com.preet.androidtemplate.core.model.SignupRequest;
import com.preet.androidtemplate.core.storage.PrefKeys;
import com.preet.androidtemplate.core.storage.PrefManager;
import com.preet.androidtemplate.utils.AppLogger;
import com.preet.androidtemplate.utils.AppToast;
import com.preet.androidtemplate.utils.ValidationUtils;
import com.preet.androidtemplate.factory.AppViewModelFactory;
import com.preet.androidtemplate.viewmodel.AuthViewModel;

import java.util.Calendar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

public class SignupActivity extends AppCompatActivity {
    private EditText etFullName, etMobile, etEmail, etDob, etHeight, etWeight, etPassword;
    private RadioGroup rgGender;
    private ProgressBar progressBar;
    private AuthViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_signup);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initViews();
        setupViewModel();
        setupDobPicker();

        findViewById(R.id.btnSignup).setOnClickListener(v -> validateAndSignup());
    }

    private void initViews() {
        etFullName = findViewById(R.id.etFullName);
        etMobile = findViewById(R.id.etMobile);
        etEmail = findViewById(R.id.etEmail);
        etDob = findViewById(R.id.etDob);
        etHeight = findViewById(R.id.etHeight);
        etWeight = findViewById(R.id.etWeight);
        etPassword = findViewById(R.id.etPassword);
        rgGender = findViewById(R.id.rgGender);
        progressBar = findViewById(R.id.progressBar);
    }

    private void setupViewModel() {
        AppViewModelFactory factory = ((MainApplication) getApplication()).getViewModelFactory();
        viewModel = new ViewModelProvider(this, factory).get(AuthViewModel.class);
    }

    @SuppressLint("DefaultLocale")
    private void setupDobPicker() {
        etDob.setOnClickListener(v -> {
            Calendar c = Calendar.getInstance();
            new DatePickerDialog(this,
                    (view, year, month, day) ->
                            etDob.setText(String.format("%04d-%02d-%02d", year, month + 1, day)),
                    c.get(Calendar.YEAR),
                    c.get(Calendar.MONTH),
                    c.get(Calendar.DAY_OF_MONTH)
            ).show();
        });
    }

    private void validateAndSignup() {

        String fullName = etFullName.getText().toString().trim();
        String mobile = etMobile.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String dob = etDob.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        String heightStr = etHeight.getText().toString().trim();
        String weightStr = etWeight.getText().toString().trim();

        int selectedGender = rgGender.getCheckedRadioButtonId();

        if (fullName.isEmpty()) {
            showError("Enter full name");
            return;
        }

        if (!mobile.matches("\\d{10}")) {
            showError("Enter valid 10 digit mobile number");
            return;
        }

        if (!ValidationUtils.isEmailValid(email)) {
            showError("Enter valid email");
            return;
        }

        if (dob.isEmpty()) {
            showError("Select date of birth");
            return;
        }

        if (selectedGender == -1) {
            showError("Select gender");
            return;
        }
        if (heightStr.isEmpty()) {
            heightStr = "100";
        }
        if (weightStr.isEmpty()) {
            weightStr = "80";
        }
        int height = Integer.parseInt(heightStr);
        int weight = Integer.parseInt(weightStr);

        if (height < 50 || height > 300) {
            showError("Enter valid height");
            return;
        }

        if (weight < 20 || weight > 300) {
            showError("Enter valid weight");
            return;
        }

        if (!ValidationUtils.isPasswordValid(password)) {
            showError("Password must be at least 6 characters");
            return;
        }

        String gender = selectedGender == R.id.rbMale ? "M" : "F";
        SignupRequest request = new SignupRequest(fullName, mobile, email, dob, gender, height, weight, password);

        viewModel.signup(request).observe(this, result -> {
            if (result == null) {
                return;
            }
            switch (result.status) {
                case LOADING:
                    // If using swipe loader animation
                    //swipeLogin.showSuccess();
                    break;

                case SUCCESS:
                    //swipeLogin.showSuccess();
                    AppLogger.api("result.message : " + result.message);
                    AppLogger.api("result.data.getStatusMessage() : " + result.data.getStatusMessage());
                    AppToast.getInstance().show(this, result.data.getStatusMessage());

                    SignupData data = result.data.getData().get(0);
                    if (data != null) {
                        PrefManager.getInstance(this).saveString(PrefKeys.USER_ID, data.getUser_id());
                    }
                    finish();
                    break;
                case ERROR:
                    AppToast.getInstance().show(this, result.message);
                    break;
            }
        });
    }

    private void showError(String msg) {
        AppToast.getInstance().show(this, msg);
    }
}