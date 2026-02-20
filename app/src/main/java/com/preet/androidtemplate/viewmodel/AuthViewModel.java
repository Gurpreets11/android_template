package com.preet.androidtemplate.viewmodel;



import com.preet.androidtemplate.core.model.BaseResponse;
import com.preet.androidtemplate.core.model.LoginData;
import com.preet.androidtemplate.core.model.SignupData;
import com.preet.androidtemplate.core.model.SignupRequest;
import com.preet.androidtemplate.core.network.ApiResult;
import com.preet.androidtemplate.utils.ValidationUtils;
import com.preet.androidtemplate.repository.AuthRepository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AuthViewModel extends ViewModel {

    private final AuthRepository repository;
    private final MutableLiveData<Boolean> loading = new MutableLiveData<>();

    public AuthViewModel(AuthRepository repository) {
        this.repository = repository;
    }

    public LiveData<Boolean> getLoading() {
        return loading;
    }

    /*public LiveData<BaseResponse<LoginData>> login(String email, String password) {
        loading.setValue(true);
        MutableLiveData<BaseResponse<LoginData>> responseLiveData = repository.login(email, password);
        responseLiveData.observeForever(response -> loading.setValue(false));
        return responseLiveData;

    }*/

    public LiveData<ApiResult<BaseResponse<LoginData>>> login(
            String email, String password) {
        return repository.login(email, password);
    }

    /*public LiveData<BaseResponse<SignupData>> signup(SignupRequest request) {
        loading.setValue(true);
        MutableLiveData<BaseResponse<SignupData>> liveData = repository.signup(request);
        liveData.observeForever(response -> loading.setValue(false));
        return liveData;
    }*/

    public LiveData<ApiResult<BaseResponse<SignupData>>> signup(
            SignupRequest request) {
        return repository.signup(request);
    }
    public boolean validateLogin(String email, String password) {
        return ValidationUtils.isEmailValid(email)
                && ValidationUtils.isPasswordValid(password);
    }
}



