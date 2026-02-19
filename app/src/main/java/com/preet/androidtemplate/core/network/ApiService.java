package com.preet.androidtemplate.core.network;



import com.preet.androidtemplate.core.model.BaseResponse;
import com.preet.androidtemplate.core.model.LoginData;
import com.preet.androidtemplate.core.model.SignupData;
import com.preet.androidtemplate.core.model.SignupRequest;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiService {

    @POST("api/auth/login")
    Call<BaseResponse<LoginData>> login(@Body Map<String, String> body);

    @POST("api/auth/register")
    Call<BaseResponse<SignupData>> signup(@Body SignupRequest request);

   /* @GET("api/garmin/request-token")
    Call<BaseResponse<GarminAuthResponse>> requestGarminToken();

    @POST("api/garmin/connection-status")
    Call<BaseResponse<GarminConnectionStatus>> getGarminConnectionStatus();*/
}
