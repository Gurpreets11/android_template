package com.preet.androidtemplate.repository;

import com.preet.androidtemplate.core.model.BaseResponse;
import com.preet.androidtemplate.core.model.LoginData;
import com.preet.androidtemplate.core.model.SignupData;
import com.preet.androidtemplate.core.model.SignupRequest;
import com.preet.androidtemplate.core.network.ApiResult;
import com.preet.androidtemplate.core.network.ApiService;

import java.util.HashMap;
import java.util.Map;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class AuthRepository extends BaseRepository {

    private final ApiService apiService;

    public AuthRepository(ApiService apiService) {
        this.apiService = apiService;
    }

    public LiveData<ApiResult<BaseResponse<LoginData>>> login(String email, String password) {
        MutableLiveData<ApiResult<BaseResponse<LoginData>>> result = new MutableLiveData<>();
        Map<String, String> body = new HashMap<>();
        body.put("email", email);
        body.put("password", password);
        executeCall(apiService.login(body), result);
        return result;
    }

    public LiveData<ApiResult<BaseResponse<SignupData>>> signup(SignupRequest request) {
        MutableLiveData<ApiResult<BaseResponse<SignupData>>> result = new MutableLiveData<>();
        executeCall(apiService.signup(request), result);
        return result;
    }

    /*public MutableLiveData<BaseResponse<LoginData>> login(String email, String password) {
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
    }*/
}


