package com.preet.androidtemplate.repository;

import com.google.gson.Gson;
import com.preet.androidtemplate.core.model.BaseResponse;
import com.preet.androidtemplate.core.network.ApiResult;
import com.preet.androidtemplate.utils.AppLogger;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public abstract class BaseRepository {
    /*

    There are 2 types of errors:

    1️⃣ Business error (HTTP 200, statusCode = 401)

    Handled via body.isSuccess()

    2️⃣ HTTP error (HTTP 401, 500, 403)

    Handled via response.errorBody()

    Your backend uses HTTP-level errors.

    So your repository must support both.

    */
    protected <T> void executeCall(Call<BaseResponse<T>> call, MutableLiveData<ApiResult<BaseResponse<T>>> result) {
        result.setValue(ApiResult.loading());
        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<BaseResponse<T>> call, @NonNull Response<BaseResponse<T>> response) {
                AppLogger.api("response.isSuccessful() : " + response.isSuccessful());
                // HTTP error (HTTP 401, 500, 403)
                if (!response.isSuccessful()) {
                    String errorMessage = "Unknown error";
                    AppLogger.api("response.body() : " + response.body());
                    try {
                        if (response.errorBody() != null) {
                            String errorJson = response.errorBody().string();
                            AppLogger.api("Raw Error Body: " + errorJson);
                            Gson gson = new Gson();
                            BaseResponse<?> errorResponse = gson.fromJson(errorJson, BaseResponse.class);
                            if (errorResponse != null && errorResponse.getStatusMessage() != null) {
                                errorMessage = errorResponse.getStatusMessage();
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    result.setValue(ApiResult.error(errorMessage));
                    return;
                }
                // Business error (HTTP 200, statusCode = 401)
                BaseResponse<T> body = response.body();
                AppLogger.api("response body : " + response.body());
                if (!response.isSuccessful() || !body.isSuccess() || response.body() == null) {
                    result.setValue(ApiResult.error(body.getStatusMessage()));
                    return;
                }

                if (body.getData() == null) {
                    result.setValue(ApiResult.error("Empty data"));
                    return;
                }
                // SUCCESS GUARANTEES VALID DATA
                result.setValue(ApiResult.success(body));
            }

            @Override
            public void onFailure(@NonNull Call<BaseResponse<T>> call, @NonNull Throwable t) {
                result.setValue(ApiResult.error(t.getMessage()));
            }
        });
    }
}
