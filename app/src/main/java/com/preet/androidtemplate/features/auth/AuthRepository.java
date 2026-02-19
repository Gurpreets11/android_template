package com.preet.androidtemplate.features.auth;



import com.preet.androidtemplate.core.model.BaseResponse;
import com.preet.androidtemplate.core.model.LoginData;
import com.preet.androidtemplate.core.model.SignupData;
import com.preet.androidtemplate.core.model.SignupRequest;
import com.preet.androidtemplate.core.network.ApiService;

import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthRepository {
    private final ApiService apiService;

    public AuthRepository(ApiService apiService) {
        this.apiService = apiService; // safe
    }

    public MutableLiveData<BaseResponse<LoginData>> login(String email, String password) {
        MutableLiveData<BaseResponse<LoginData>> result = new MutableLiveData<>();
        Map<String, String> body = new HashMap<>();
        body.put("email", email);
        body.put("password", password);
        apiService.login(body).enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<BaseResponse<LoginData>> call, @NonNull Response<BaseResponse<LoginData>> response) {
                result.setValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<BaseResponse<LoginData>> call, @NonNull Throwable t) {
                result.setValue(null);
            }
        });
        return result;
    }

    public MutableLiveData<BaseResponse<SignupData>> signup(SignupRequest request) {
        MutableLiveData<BaseResponse<SignupData>> result = new MutableLiveData<>();
        apiService.signup(request).enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<BaseResponse<SignupData>> call,
                                   @NonNull Response<BaseResponse<SignupData>> response) {
                result.setValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<BaseResponse<SignupData>> call, @NonNull Throwable t) {
                result.setValue(null);
            }
        });
        return result;
    }
}

